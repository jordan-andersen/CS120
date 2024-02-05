import java.util.List;
import java.util.ArrayList;

public class Employee {

    // ATTRIBUTES
    private static int total_employees = 0;
    private String first_name;          // <-- (3) "name_first"
    private String last_name;           // <-- (2) "name_last"
    private long ID_number;             // <-- (1) "trans_no"
    private List<Position> positions;

    // METHODS
    public Employee(String given_first_name, String given_last_name, long given_ID_number) {
        this.first_name = given_first_name;
        this.last_name = given_last_name;
        this.ID_number = given_ID_number;
        this.positions = new ArrayList<Position>();
        Employee.total_employees = Employee.total_employees + 1;
    }

    public static Employee parse(String[] data_line, int line_index) {
        Employee created_employee = null;
        String error_message = "";

        boolean input_is_correct_length = data_line.length >= 28;
        boolean no_null_entries = data_line[3] != null && !data_line[3].trim().isEmpty() &&
                                  data_line[2] != null && !data_line[2].trim().isEmpty() &&
                                  data_line[21] != null && !data_line[1].trim().isEmpty();
        
        
        if ( input_is_correct_length && no_null_entries ) {
            String parsed_first_name = data_line[3];
            String parsed_last_name = data_line[2];
            long parsed_ID_number = Long.parseLong(data_line[1]); 

            created_employee = new Employee(parsed_first_name, parsed_last_name, parsed_ID_number);
        } else if ( !input_is_correct_length ) {
            error_message += ("Line: " + line_index + "\nEmployee: Error! Incorrect string array length was passed! Unable to parse line to Employee.\n" +
                              "String array length = " + data_line.length + "\n");
            ErrorWriter.writeEmployeeError(error_message, true);
        } else if ( !no_null_entries ) {
            error_message += ("Line: " + line_index + "\nEmployee: Error! A NULL value was passed! Unable to parse line to Employee.\n" +
                              "3: " + data_line[3].isEmpty() + "\n" + 
                              "2: " + data_line[2].isEmpty() + "\n" + 
                              "1: " + data_line[1].isEmpty() + "\n");
            ErrorWriter.writeEmployeeError(error_message, true);
        } else {
            error_message += ("Line: " + line_index + "\nEmployee: Unknown error! Unable to parse line to Employee.\n");
            ErrorWriter.writeEmployeeError(error_message, true);
        }

        return created_employee;
    } 

    public static int getTotalEmployees() {
        return Employee.total_employees;
    }

    public void addPosition(Position given_position) {
        this.positions.add(given_position);
    }

    public int getNumberOfPositions() {
        return positions.size();
    } 

    public double getTotalPay() {
        double total_pay = 0;
        for (Position position : this.positions) {
            total_pay = total_pay + position.getPay();
        }
        return total_pay;
    }

    public String getHighestPayingPositions() {
        double highest_paid_income = 0.0;
        String highest_paid_position = "";
        
        for (Position position : positions) {
            if (position.getPay() > highest_paid_income) {
                highest_paid_income = position.getPay();
                highest_paid_position = position.getTitle();
            } 
        }

        return highest_paid_position + " $" + String.format("%.2f", highest_paid_income) + "\n";
    }

    public String getFullName() {
        return this.first_name + " " + this.last_name;
    }

    public long getIDString() {
        return ID_number;
    }
    
    @Override
    public String toString() {
        String employee_string = "\nListing position details for employee " + this.first_name.toUpperCase() + " " + this.last_name.toUpperCase() + " (" + this.ID_number + "):\n";
        for (Position position : this.positions) {
            employee_string = employee_string + position.toString();
        }
        employee_string = employee_string + "Total pay is: $" + String.format("%.2f", this.getTotalPay());
        return employee_string;
    }
}
