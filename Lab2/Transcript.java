public class Transcript {
	
	// ATTRIBUTES

	private String course_student;
	private String course_name;
	private int course_year;
	private String course_semester;
	private double[] lab_grades;
	private double[] exam_grades;

	// METHODS

	public Transcript (String student, String name, int year, String semester) {
		this.course_student = student;
		this.course_name = name;
		this.course_year = year;
		this.course_semester = semester;
		this.lab_grades = new double[]{0, 0, 0, 0, 0};
		this.exam_grades = new double[]{0, 0};
	}
	
	public void setLabGrades (double[] labs) {
		this.lab_grades = labs;
	}

	public void setExamGrades (double[] exams) {
		this.exam_grades = exams;
	}
	
	private double roundDouble(double input_value) {
		return ((double) (Math.round(input_value * 100)) / 100);
	}
	
	private double calculateLabAverage () {
		return (this.lab_grades[0] + this.lab_grades[1] + this.lab_grades[2] + this.lab_grades[3] + this.lab_grades[4]) / 250;
	}

	private double calculateCourseGrade () {	// calculates lab average then calculates overall course grades according to 
		return this.roundDouble((this.calculateLabAverage() * 60) + (this.exam_grades[0] * .2) + (this.exam_grades[1] * .2));
	}
	
	public String toString () {
		String representation = (
			"Showing scores for " + this.course_name + " student " + this.course_student + "\n"
			+ "-----------------------------------" + "\n"
			+ "Lab 1 ........................." + roundDouble(this.lab_grades[0]) + "\n"
			+ "Lab 2 ........................." + roundDouble(this.lab_grades[1]) + "\n"
			+ "Lab 3 ........................." + roundDouble(this.lab_grades[2]) + "\n"
			+ "Lab 4 ........................." + roundDouble(this.lab_grades[3]) + "\n"
			+ "Lab 5 ........................." + roundDouble(this.lab_grades[4]) + "\n"
			+ "Midterm ......................." + roundDouble(this.exam_grades[0]) + "\n"
			+ "Final ........................." + roundDouble(this.exam_grades[1]) + "\n"
			+ "-----------------------------------" + "\n" 
			+ "Total Course Grade ............" + this.calculateCourseGrade()
		);
		return representation;
	}
}
