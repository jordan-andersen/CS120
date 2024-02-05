public class Transcript {
	
	// ATTRIBUTES

	private String course_student;
	private String course_name;
	private String course_year;
	private String course_semester;
	private Assignment[] assignments;
	private int assignment_count;
	private int lab_count;
	private int exam_count;

	// METHODS

	public Transcript (String[] course_details) {
		// INSTANTIATES A NEW TRANSCRIPT OBJECT AND INITIALIZES ATTRIBUTES WITH DEFAULT AND USER PROVIDED VALUES. 
		// Instantiates a 
		this.course_student = course_details[0];
		this.course_name = course_details[1];
		this.course_year = course_details[2];
		this.course_semester = course_details[3];
		this.assignments = new Assignment[20];
		this.assignment_count = 0;
		this.lab_count = 0;
		this.exam_count = 0;
	}
	
	public void recordAssignment (double grade, boolean is_lab) {
		// Passes default values to create new Assignment object based on if the passed input is a lab assignment. 
		if ( is_lab ) {
			this.lab_count = this.lab_count + 1;
			this.assignments[assignment_count] = new Assignment(("Lab" + this.lab_count), "lab", grade, 50, 12);
		} else {
			this.exam_count = this.exam_count + 1;
			this.assignments[assignment_count] = new Assignment(("Exam" + this.exam_count), "exam", grade, 100, 20);
		}
		this.assignment_count = this.assignment_count + 1;
	}

	private static String convertGrade(double grade) {
		// CONVERTS A (DOUBLE) PERCENTAGE SCORE AND RETURNS A CORRESPONDING (STRING) LETTER GRADE.
		if ( grade < 63 ) {
			return "F";
		} else if ( grade < 67 ) {
			return "D";
		} else if ( grade < 70 ) {
			return "D+";
		} else if ( grade < 73 ) {
			return "C-";
		} else if ( grade < 77 ) {
			return "C";
		} else if ( grade < 80 ) {
			return "c+";
		} else if ( grade < 83 ) {
			return "B-";
		} else if ( grade < 87 ) {
			return "B";
		} else if ( grade < 90 ) {
			return "B+";
		} else if ( grade < 94 ) {
			return "A-";
		} else if ( grade <= 100 ) {
			return "A";
		} else {
			return "Invalid input.";
		}
	}

	private double calculateCourseGrade () {
		// SUMS AND CALCULATES AN AVERAGE OF EACH GRADE CATEGORY AND SUMS THOSE WEIGHTED SUMS TOGETHER, RETURNS THE FINAL COURSE GRADE.
		double grade_sum = 0;
		for ( Assignment assignment : this.assignments ) { if ( assignment != null ) {grade_sum = grade_sum + assignment.getWeightedScore();} }
		return ((double) (Math.round(grade_sum * 10)) / 10);
	}
	
	public String toString () {
		// REPLACES DEFAULT toSTRING METHOD, CREATES STRING WITH EACH ASSIGNMENT GRADE AND INCLUDES RELEVANT COURSE INFO AND FINAL LETTER GRADE.
		double course_grade = calculateCourseGrade();
		String assignments_text = "";
		for ( Assignment assignment : assignments ) {
			if ( assignment != null ) { assignments_text = assignments_text + assignment.toString(); }
		}
		return (
			"Showing " + this.course_semester + " " + this.course_year + " transcript for " + this.course_name + " student " + this.course_student + "\n"
			+ "----------------------------------" + "\n"
			+ assignments_text
			+ "----------------------------------" + "\n" 
			+ "Total Course Grade" + "\t" + course_grade + "% (" + convertGrade(course_grade) + ")"
		);
	}
}