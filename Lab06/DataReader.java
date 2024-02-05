import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class DataReader {
    
    // ATTRIBUTES
    private static ArrayList<Employee> employee_list = new ArrayList<Employee>();
    private static HashMap<Long, Integer> employee_indices = new HashMap<Long, Integer>();
    private static Integer list_index = 0;  // <-- Allows for tracking list index of unique employees.
    private static Integer line_index = 1;  // <-- Starts at 1, header line is skipped.

    // METHODS
    public static ArrayList<Employee> readFile(File data_file) {
        try {
            Scanner data_input = new Scanner(data_file);
            data_input.nextLine(); // <-- skips header line.
    
            while (data_input.hasNext()) {
                parseString(data_input.nextLine());
            }
    
            data_input.close();
            return employee_list;
        } catch (FileNotFoundException error) {
            error.printStackTrace();
            return null;
        }
    } 

    public static void parseString(String given_string) {
        //System.out.println(given_string);
        String[] data_line = given_string.split(",");
        Long ID_number = Long.parseLong(data_line[1]);
        String error_message = "";
        line_index = line_index + 1;

        // System.out.println("Processing line: " + line_index);
        if (employee_indices.containsKey(ID_number)) {  // <-- If employee already exists, only add position.

            Integer employee_index = employee_indices.get(ID_number);
            Position created_position = Position.parse(data_line, line_index);
            if ( created_position != null) {
                employee_list.get(employee_index).addPosition(created_position);
            } else {
                error_message += ("Line: " + line_index + "\nDataReader: Error! NULL Position returned!\n");
                ErrorWriter.writeError(error_message);
            }

        } else {
            Employee created_employee = Employee.parse(data_line, line_index);
            if ( created_employee != null ) {
                employee_indices.put(ID_number, list_index);
                employee_list.add(created_employee);

                Position created_position = Position.parse(data_line, line_index);
                if ( created_position != null) {
                    employee_list.get(list_index).addPosition(created_position);
                } else {
                    error_message += ("Line: " + line_index + "\nDataReader: Error! NULL Position returned!\n");
                    ErrorWriter.writeError(error_message);
                }

                list_index = list_index + 1;
            } else {
                error_message += ("Line: " + line_index + "DataReader: Error! NULL Employee returned!\n");
                ErrorWriter.writeError(error_message);
            }
        }
    }
}
