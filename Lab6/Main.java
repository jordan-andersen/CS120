import java.io.File;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    private static Scanner user_input = new Scanner(System.in);
    private static ArrayList<Employee> employee_list;
    private static File data_file;

    public static void main(String[] args) {
        boolean valid_input = false;

        System.out.print(	
                        "\n============================ USAGE ============================\n" +
                        "Ensure the data files are in the ./resources directory.\n" +
                        "Input \'[insert_file_name].csv\' to load desired data file.\n" +
                        "After the data file is loaded, enter desired employee name.\n" +
                        "Name format: 'FIRSTname LASTname'\n" +
                        "Input \'quit\' to end program.\n" +
                        "===============================================================\n\n");
        
        while ( !valid_input ) {
            System.out.print("Input filepath to data file: resources/");
            String file_name = user_input.nextLine().trim();
            String file_path = "resources/" + file_name;
            data_file = new File(file_path);

            if (data_file.exists() && file_path.endsWith(".csv")) {
                System.out.println("Loading...\n");
                ErrorWriter.initializeErrorFile();
                employee_list = DataReader.readFile(data_file);
                boolean searching = true;
                
                System.out.println(ErrorWriter.getErrorReport());
                System.out.println("Total EMPLOYEES loaded: " + Employee.getTotalEmployees());
                System.out.println("Total POSITIONS loaded: " + Position.getTotalPositions());

                while ( searching ) {
                    searching = searchMenu();
                }

                valid_input = true;
                System.out.println("Quitting...");
            } else if ( file_name.equalsIgnoreCase("quit")) {
                valid_input = true;
                System.out.println("Quitting...");
            } else {
                System.out.println("Error: Invalid file provided!");
            }
        }
       
        user_input.close();
    }
    
    private static boolean searchMenu() {
        System.out.print("\nInput name [First Last] of employee to lookup: ");
        String name_input = user_input.nextLine().trim();
        boolean employee_found = false;
        
        if (name_input.equalsIgnoreCase("quit")) {
            return false;
        } else {
            String search_name = formatName(name_input);
            
            for (Employee employee : employee_list) {
                String employee_name = formatName(employee.getFullName());
               
                if ( employee_name.equals(search_name) ) {
                    System.out.println(employee.toString());
                    employee_found = true;
                }
            }
            
            if ( !employee_found ) { System.out.println("\nNo employee found"); }
            
            return true;
        }
    }
    
    private static String formatName(String input_string) {               // Example return: matthewobetz
        return input_string.toLowerCase()                                 // <-- Unifies case
                           .replaceAll("[^a-z]", "")    // <-- Removes all non-alphabet chars.
                           .replaceAll("jr","")         // <-- Removes JR suffix
                           .replaceAll("iii","")        // <-- Removes III suffix
                           .replaceAll("iv","");        // <-- Removes IV suffix
    
    }
}
