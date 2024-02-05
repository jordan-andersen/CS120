public class Main {
	public static void main(String[] args){
		// ENSURES USER HAS CORRECTLY INPUTTED THE REQUIRED ARGUMENTS. IF NOT, PROMPTS THE USER WITH THE USAGE INSTRUCTIONS AND ENDS THE PROGRAM.
		if (args.length != 11) { 
        	System.out.println("Usage: java GradeCalculator <\"student\"> <\"course\"> <\"year\"> <\"semester\"> <lab 1 grade> <lab 2 grade> <lab 3 grade> <lab 4 grade> <lab 5 grade> <mid-term grade> <final grade>"); 
        	return;
        }

        // DECLARES AND INITIALIZES PROGRAM ARRAYS FOR COURSE DETAILS, LAB GRADES, EXAM GRADES, TRANSCRIPTS.
        // TRANSCRIPTS ARRAY IS CURRENTLY UNNECCESARY.
        String[] course_details = new String[4];
		double[] lab_grades = new double[5];
		double[] exam_grades = new double[2];
        Transcript[] semester_schedule = new Transcript[5];

        // POPULATES PROGRAM ARRAYS WITH USER PROVIDED DATA. 
        for ( int i = 0; i < args.length; i++ ) { if ( i < 4 ) { course_details[i] = args[i]; } }
		for ( int i = 4; i < args.length; i++ ) { if ( i < 9 ) { lab_grades[i - 4] = Double.parseDouble(args[i]); } }
		for ( int i = 9; i < args.length; i++ ) { if ( i < 11 ) { exam_grades[i - 9] = Double.parseDouble(args[i]); } }
		
		// CREATES TRANSCRIPT OBJECT WITH USER PROVIDED COURSE DETAILS, THEN CREATES ASSIGNMENT CHILD OBJECTS FOR NEWLY CREATED TRANSCRIPT PARENT OBJECT.
		semester_schedule[0] = new Transcript(course_details);
		for ( Double lab : lab_grades ) {
			if ( lab !=  null ){ semester_schedule[0].recordAssignment(lab, true); }
		}
		for ( Double exam : exam_grades ) {
			if ( exam != null ){ semester_schedule[0].recordAssignment(exam, false); }
		}

		// PRINTS STORED TRANSCRIPTS
		for ( Transcript transcript : semester_schedule ) {
			if ( transcript != null ) { System.out.println(transcript.toString()); }
		}
	}
}