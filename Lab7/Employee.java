import java.util.*;

public class Employee {

    // ATTRIBUTES
    private static int total_employees = 0;
    private List<Department> departments;
    private String first_name;          // <-- (3) "name_first"
    private String last_name;           // <-- (2) "name_last"
    private long employee_id;             // <-- (1) "trans_no"
    private List<Position> positions;

    // METHODS
    public Employee( String given_first_name, String given_last_name, long given_id ) {
        this.departments = new ArrayList<Department>();
        this.first_name = given_first_name;
        this.last_name = given_last_name;
        this.employee_id = given_id;
        this.positions = new ArrayList<Position>();
        Employee.total_employees = Employee.total_employees + 1;
    }

    public static Employee parse( String[] data_line, int line_index ) {
        Employee created_employee = null;
        String error_message = "";

        boolean input_is_correct_length = data_line.length >= 28;
        boolean no_null_entries = data_line[3] != null && !data_line[3].trim().isEmpty() &&
                                  data_line[2] != null && !data_line[2].trim().isEmpty() &&
                                  data_line[1] != null && !data_line[1].trim().isEmpty();
        
        
        if ( input_is_correct_length && no_null_entries ) {
            String parsed_first_name = data_line[3];
            String parsed_last_name = data_line[2];
            long parsed_id = Long.parseLong(data_line[1]); 

            created_employee = new Employee(parsed_first_name, parsed_last_name, parsed_id);
        } else if ( !input_is_correct_length ) {
            error_message += ("Line: " + line_index + "\nEmployee: Error! Incorrect string array length was passed! Unable to parse line to Employee.\n" +
                              "String array length = " + data_line.length + "\n");
            ErrorWriter.writeEmployeeError(error_message);
        } else if ( !no_null_entries ) {
            error_message += ("Line: " + line_index + "\nEmployee: Error! A NULL value was passed! Unable to parse line to Employee.\n" +
                              "3: " + data_line[3].isEmpty() + "\n" + 
                              "2: " + data_line[2].isEmpty() + "\n" + 
                              "1: " + data_line[1].isEmpty() + "\n");
            ErrorWriter.writeEmployeeError(error_message);
        } else {
            error_message += ("Line: " + line_index + "\nEmployee: Unknown error! Unable to parse line to Employee.\n");
            ErrorWriter.writeEmployeeError(error_message);
        }

        return created_employee;
    } 

    public static int getTotalEmployees() {
        return Employee.total_employees;
    }
    
    public void addDepartment( Department given_department ){
        if ( !departments.contains(given_department) ) {
            this.departments.add(given_department);
            given_department.addEmployee(this);
        }
    }
    
    public List<Department> getDepartments() {
        return this.departments;
    }
    
    public void addPosition( Position given_position ) {
        this.positions.add(given_position);
    }

    public List<Position> getPositions() {
        return this.positions;
    }

    public Integer getNumberOfPositions() {
        return this.positions.size();
    } 

    public String getPositionByDepartment( Department search_department ) {
        String result_string = "\nListing position details for employee " + this.first_name.toUpperCase() + " " + this.last_name.toUpperCase() + " (" + this.employee_id + "):\n";
        Double total_pay = 0.0;
        boolean position_found = false;
        for ( Position position : this.positions ) {
            if ( position.getDepartment().equals(search_department) ) {
                result_string = result_string + position.toString() + "\n";
                total_pay = total_pay + position.getPay();
                position_found = true;
            }
        }
        result_string = result_string + "Total pay from " + search_department.getDepartmentName().toUpperCase() + ": $" + String.format("%,.2f", total_pay);
        if ( position_found ) {
            return result_string;
        } else {
            return null;
        }
    }

    public String getPositionByTitle( String search_position ) {
        String result_string = "\nListing position details for employee " + this.first_name.toUpperCase() + " " + this.last_name.toUpperCase() + " (" + this.employee_id + "):\n";
        Double total_pay = 0.0;
        String search_string = search_position.toLowerCase()
                                              .replaceAll("[^a-z]", "");
        boolean position_found = false;
        for ( Position position : this.positions ) {
            String title_string = position.getTitle().toLowerCase()
                                                     .replaceAll("[^a-z]", "");
            if ( title_string.contains(search_string) ) {
                result_string = result_string + position.toString() + "\n";
                total_pay = total_pay + position.getPay();
                position_found = true;
            }
        }
        result_string = result_string + "Total pay from " + search_position.toUpperCase() + ":  $" + String.format("%,.2f", total_pay);
        if ( position_found ) {
            return result_string;
        } else {
            return null;
        }
    }

    public Double calculatePayByDepartment( Department search_department ) {
        Double result_sum = 0.0;
        for ( Position position : this.positions ) {
            if ( position.getDepartment().equals(search_department) ) {
                result_sum = result_sum + position.getPay();
            }
        }
        return result_sum;
    }

    public Double calculatePayByPosition( String search_position ) {
        Double result_sum = 0.0;
        String search_string = search_position.toLowerCase()
                                              .replaceAll("[^a-z]", "");
        boolean position_found = false;
        for ( Position position : this.positions ) {
            String title_string = position.getTitle().toLowerCase()
                                                     .replaceAll("[^a-z]", "");
            if ( title_string.contains(search_string) ) {
                result_sum = result_sum + position.getPay();
                position_found = true;
            }
        }
        if ( position_found ) {
            return result_sum;
        } else {
            return null;
        }
    }
    
    public Double getTotalPay() {
        Double total_pay = 0.0;
        for (Position position : this.positions) {
            total_pay = total_pay + position.getPay();
        }
        return total_pay;
    }

    public String getHighestPayingPositions() {
        Double highest_paid_income = 0.0;
        String highest_paid_position = "";
        for (Position position : this.positions) {
            if (position.getPay() > highest_paid_income) {
                highest_paid_income = position.getPay();
                highest_paid_position = position.getTitle();
            } 
        }
        return highest_paid_position + " $" + String.format("%.2f", highest_paid_income) + "\n";
    }

    public String getName() {
        return this.first_name + " " + this.last_name;
    }

    public long getEmployeeID() {
        return this.employee_id;
    }
    
    @Override
    public int hashCode() {
        return 59 * 61 * ((int) this.employee_id);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        } 
        if ( obj.getClass() != this.getClass() || obj == null) {
            return false;
        }
        Employee other = (Employee) obj;
        return other.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        String employee_string = "\nListing position details for employee " + this.first_name.toUpperCase() + " " + this.last_name.toUpperCase() + " (" + this.employee_id + "):\n";
        for (Position position : this.positions) {
            employee_string = employee_string + position.toString() + "\n";
        }
        employee_string = employee_string + "Total pay: $" + String.format("%,.2f", this.getTotalPay());
        return employee_string;
    }
}
