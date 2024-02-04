public class Main {
	public static void main(String[] args){
		if (args.length != 11) { 
        	System.out.println("Usage: java GradeCalculator <\"student\"> <\"course\"> <\"year\"> <\"semester\"> <lab 1 grade> <lab 2 grade> <lab 3 grade> <lab 4 grade> <lab 5 grade> <mid-term grade> <final grade>"); 
        	return;
        }

        String course_student = args[0];
    	String course_name = args[1];
		int course_year = Integer.parseInt(args[2]);
		String course_semester = args[3];
		double[] lab_grades = {0, 0, 0, 0, 0};
		for ( int i = 4; i < 9; i = i + 1 ) { lab_grades[i - 4] = Double.parseDouble(args[i]); }
		double[] exam_grades = {Double.parseDouble(args[9]), Double.parseDouble(args[10])};
		Transcript course_one = new Transcript(course_student, course_name, course_year, course_semester);

		course_one.setLabGrades(lab_grades);
		course_one.setExamGrades(exam_grades);
		System.out.println(course_one.toString());
	}
}