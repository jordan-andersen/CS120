import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		// DISPLAYS USAGE INFO ON PROGRAM START
		System.out.print(	"\n============================= USAGE =============================\n" +
							"\'First Last\' name of student to load and print their transcript.\n" +
							"\'quit\' to end program.\n" + 
							"=================================================================\n\n");
		
		Scanner user_input = new Scanner(System.in);
		GradeReader grade_book;
		Boolean program_running = true;

		// MAIN PROGRAM INPUT LOOP 
		while ( program_running ) {
			// RESETS STRING VARIABLES.
			String student_name = "";
			String user_command = "";

			// PROMPTS USER FOR STRING INPUT AND PARSES THE INPUT STRING.
			System.out.print("Input: ");
			user_command = user_input.nextLine().trim(); // USER INPUT, TRIMS THE INPUT
			String[] name_array = user_command.split(" "); // SPLITS USER INPUT INTO NAME ARRAY
			for ( String name : name_array ) { if ( !name.isEmpty() ) { student_name = student_name + name + " ";}} // CONCATENATES NAMES TOGETHER WITHOUT EXCESS WHITE SPACE.
			student_name = student_name.trim().toUpperCase(); // TRIMS AND FORMATS FINAL STUDENT NAME 

			// MAIN PROGRAM LOGIC, CHECKS FOR KEYWORD COMMANDS, OPENS TRANSCRIPT FILE, 
			// CREATES NEW GRADEREADER OBJECT FROM THE OPENED TRANSCRIPT FILE, PRINTS OUT GRADEBOOK.
			if ( user_command.equalsIgnoreCase("quit") ) {
					program_running = false;
					System.out.println("Exiting...\n");
			} else if ( student_name.split(" ").length == 2 ) {
				String file_path = "resources/" + student_name.replace(" ", "_").toLowerCase() + ".trp";
				File transcript_file = new File(file_path);
				if ( transcript_file.exists() )  {
					System.out.println("Reading file: ./" + file_path);
					grade_book = new GradeReader(transcript_file);
					grade_book.loadFile();
					System.out.println("Loaded transcript for " + student_name + ".\n" + grade_book.toString());
				} else {
					System.out.println("No file for " + student_name + " found.\n");
				}
			} else {
				System.out.println("Invalid input! Use \'quit\' to end program.\n");
			}
		}

		user_input.close(); // CLOSES OUT SCANNER OBJECT
		return;
	}
}