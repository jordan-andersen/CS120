public class GradeCalculatorOld {
	
	public static void main(String[] args){
		double lab_1 = Double.parseDouble(args[0]);
		double lab_2 = Double.parseDouble(args[1]);
		double lab_3 = Double.parseDouble(args[2]);
		double lab_4 = Double.parseDouble(args[3]);
		double lab_5 = Double.parseDouble(args[4]);
		double mid_term = Double.parseDouble(args[5]);
		double end_term = Double.parseDouble(args[6]);
		double lab_average = calculateLabAverage(lab_1, lab_2, lab_3, lab_4, lab_5);
		double course_grade = calculateCourseGrade(lab_average, mid_term, end_term);
		System.out.println("Your final course grade for CS120 is: " + course_grade + "%");
	}
	
	public static double calculateLabAverage(double grade_1, double grade_2, double grade_3, double grade_4, double grade_5) {
		return (grade_1 + grade_2 + grade_3 + grade_4 + grade_5) / 5;
	}

	public static double calculateCourseGrade(double lab_grade, double mid_grade, double end_grade) {
		return ((double) (Math.round( ((lab_grade * 1.2) + ( mid_grade * 0.2 ) + ( end_grade * 0.2 )) * 100 )) / 100 );
	}

}
