public class GradeCalculator {
	
	public static void main(String[] args) {
        if (args.length != 7) { 
        	System.out.println("Usage: java GradeCalculator <lab 1 grade> <lab 2 grade> <lab 3 grade> <lab 4 grade> <lab 5 grade> <mid-term grade> <final grade>"); 
        	return;
        }
		double[] courseGrades = {0, 0, 0, 0, 0, 0, 0};
		for ( int i = 0; i < 7; i = i + 1 ) {courseGrades[i] = Double.parseDouble(args[i]);}
		System.out.println("Your final course grade for CS120 is: " + calculateCourseGrade(courseGrades) + "%");
	}

	public static double calculateCourseGrade(double[] grades) {
		double weightedSum = 0;
		for ( int i = 0; i < 7; i = i + 1 ) {if ( i < 5 ) {weightedSum = weightedSum + grades[i] * 0.24;} else {weightedSum = weightedSum + grades[i] * 0.2;}}
		return ((double) (Math.round(weightedSum * 100)) / 100);
	}
}

/* 
5: parses the args array into doubles and sequentially stores the resulting values in grade_array,
6: passes grade_array to the calculateCourseGrade function, which calculates the final course grade, then prints the program results.
11: sequentially multiplies the array grade values by a grade weight constant and adds the result to the value in index 7,
12: rounds course_grade percentage to 1 decimal place and returns value.
Array indexes 0-4:
Lab grade = 60 * ( (lab 1 / 50) (lab 2 / 50) (lab 3 / 50) (lab 4 / 50) (lab 5 / 50) / 5 )
Lab grade = 60 / 250 (lab 1 + lab 2 + lab 3 + lab 4 + lab 5)
Lab grade = 0.24 (lab 1 + lab 2 + lab 3 + lab 4 + lab 5)
Lab grade = 0.24 lab 1 + 0.24 lab 2 + 0.24 lab 3 + 0.24 lab 4 + 0.24 lab 5
Array indexes 5-6:
Exam grade = mid exam * 20 / 100 + end exam * 20 / 100
Exam grade = 0.2 mid exam + 0.2 end exam
*/