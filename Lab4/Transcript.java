/**
 * A Transcript represents a college course and stores all associated data. Transcripts are created from stored file data read by a GradeReader object.
 * Transcripts contain all assignments as Assignment objects.
 * @author Jordan Andersen
 * @version 1.4
 * @Date 2023-10-20
 * */	

public class Transcript {
	
	// ATTRIBUTES
	/** course_name (String): the proper course name. Programming 1 as an example, not CS120. */
	private String course_name;
	/** course_student (String): the First and Last name of the student. Format: First Last. */
	private String course_student;
	/** course_year (int): the Calender year of course. Format: 0000. */
	private int course_year;
	/** course_semester (String): the semester term of the class. Options: Fall, Winter, Spring, Summer */
	private String course_semester;
	/** category_weights (double[]): 8 element array that stores various grade weights as dictated by the course syllabus:  
	 * 0 - Final Exam  1 - Midterm Exam  2. Exams  3. Quizzes  4. Labs  5. Homework	 6. Attendance  7. Participation 
	*/
	private double[] category_weights = {20, 20, 0, 0, 60, 0, 0, 0}; // HARD CODED WEIGHTS FOR TO FIT LAB ASSIGNMENT REQUIREMENTS
	/** course_assignments (Assignment[]): 100 element array that stores the assignments associated with the transcript. */
	private Assignment[] course_assignments;
	/** assignment_count (int): track total number of recorded assignment used to index the course_assignment array. */
	private int assignment_count;

	// METHODS
	/** Instantiates a new Transcript object and initializes attributes with default and user provided values. 
	 * @param course_details[0]			course_name: This is the proper course name. Programming 1, not CS120.
	 * @param course_details[1]			course_student: This is the course student name. Format: First Last. 
	 * @param course_details[2]			course_year: This is the course year. Format: 2023.
	 * @param course_details[3] 		course_semester: This is the course semester. Fall, Winter, Spring, Summer */
	public Transcript (String[] course_details) {
		this.course_name = course_details[0];
		this.course_student = course_details[1];
		this.course_year = Integer.parseInt(course_details[2]);
		this.course_semester = course_details[3];
		this.course_assignments = new Assignment[100];
		this.assignment_count = 0;
	}
	
	public void recordAssignment (String[] assignment_details) {
		/** Passes assignment details array to construct a new assignment object and increments assignment count. */
		this.course_assignments[assignment_count] = new Assignment(assignment_details);
		this.assignment_count = this.assignment_count + 1;
	}

	private static String convertGrade(double grade) {
		/** Converts a (double) percentage score and returns a corresponding (String) letter grade. */
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
			return "C+";
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

	private double calculateCategoryGrade (double score, double total, double weight) {
		return weight * (score / total);
	}
	
	private double calculateCourseGrade () {
		/** Sums each assignment type totals for actual and total possible points to calculate category average. 
		 * Then multiplies by the category weight then sums these weighted sums together and returns the final course grade. */
		double grade_sum = 0;
		double[] category_score = new double[8];
		double[] category_total = new double[8];
		for (Assignment assignment : this.course_assignments) {
			if (assignment != null) {
				switch (assignment.getType()) {
					case "final":
						category_score[0] += assignment.getScore();
						category_total[0] += assignment.getTotal();
						break;
					case "midterm":
						category_score[1] += assignment.getScore();
						category_total[1] += assignment.getTotal();
						break;
					case "exam":
						category_score[2] += assignment.getScore();
						category_total[2] += assignment.getTotal();
						break;
					case "quiz":
						category_score[3] += assignment.getScore();
						category_total[3] += assignment.getTotal();
						break;
					case "lab":
						category_score[4] += assignment.getScore();
						category_total[4] += assignment.getTotal();
						break;
					case "homework":
						category_score[5] += assignment.getScore();
						category_total[5] += assignment.getTotal();
						break;
					case "attendance":
						category_score[6] += assignment.getScore();
						category_total[6] += assignment.getTotal();
						break;
					case "participation":
						category_score[7] += assignment.getScore();
						category_total[7] += assignment.getTotal();
						break;
				}
			}
		}
		for ( int i = 0; i < 8; i++ ) { 
			if ( this.category_weights[i] != 0 && category_total[i] != 0 ) { // CHECK TO ENSURE CATEGORY IS NOT USED OR EMPTY.
				grade_sum += calculateCategoryGrade(category_score[i], category_total[i], this.category_weights[i]); // ADDS WEIGHTED CATEGORY SUMS TOGETHER.
			} 
		}
		return ((double) (Math.round(grade_sum * 10)) / 10);
	}
	
	public String toString () {
		/** Replaces default toString method, creates string with each assignment grade and includes relevant course info and final letter grade. */
		double course_grade = calculateCourseGrade();
		String assignments_text = "";
		for ( Assignment assignment : this.course_assignments ) {
			if ( assignment != null ) { assignments_text = assignments_text + assignment.toString(); }
		}
		return (
			"\nShowing " + this.course_semester + " " + this.course_year + " transcript for " + this.course_name + " student " + this.course_student + ":\n"
			+ "--------------------------------------------------" + "\n"
			+ assignments_text
			+ "--------------------------------------------------" + "\n" 
			+ "Total Course Grade:" + "\t\t\t" + course_grade + "% (" + convertGrade(course_grade) + ")\n\n"
		);
	}
}