package data;

import java.util.*;

public class Employee {

    // ATTRIBUTES
    private final String first_name;          // <-- (3) "name_first"
    private final String last_name;           // <-- (2) "name_last"
    private final long employee_id;             // <-- (1) "trans_no"
    private final List<Department> departments;
    private final List<Position> positions;

    // METHODS
    // Instantiates a EMPLOYEE object.
    public Employee( String given_first_name, String given_last_name, long given_id ) {
        this.departments = new ArrayList<>();
        this.first_name = given_first_name;
        this.last_name = given_last_name;
        this.employee_id = given_id;
        this.positions = new ArrayList<>();
    }

    // Factory method, parses passed data line from data file.
    // Validates proper data formatting and ensures correct instantiation.
    public static Employee parse( String[] data_line ) {
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
        }

        return created_employee;
    }
    
    public void addDepartment( Department given_department ){
        if ( !departments.contains(given_department) ) {
            this.departments.add(given_department);
            given_department.addEmployee(this);
        }
    }

    public void addPosition( Position given_position ) {
        this.positions.add(given_position);
    }

    public String getPositionByDepartment( Department search_department ) {
        StringBuilder result_string = new StringBuilder();
        boolean position_found = false;
        for ( Position position : this.positions ) {
            if ( position.getDepartment().equals(search_department) ) {
                result_string.append(position.getTitle()).append(", ");
                position_found = true;
            }
        }
        if ( position_found ) {
            return result_string.toString();
        } else {
            return null;
        }
    }

    public List<Position> getPositionByTitle( String search_position ) {
        List<Position> result_positions = new ArrayList<>();
        String search_string = search_position.toLowerCase()
                                              .replaceAll("[^a-z]", "");
        boolean position_found = false;
        for ( Position position : this.positions ) {
            String title_string = position.getTitle().toLowerCase()
                                                     .replaceAll("[^a-z]", "");
            if ( title_string.contains(search_string) ) {
                result_positions.add(position);
                position_found = true;
            }
        }
        if ( position_found ) {
            return result_positions;
        } else {
            return null;
        }
    }

    public Double getSalaryByDepartment(Department search_department ) {
        double result_sum = 0.0;
        for ( Position position : this.positions ) {
            if ( position.getDepartment().equals(search_department) ) {
                result_sum = result_sum + position.getSalary();
            }
        }
        return result_sum;
    }

    public Double getSalaryByPosition(String search_position ) {
        double result_sum = 0.0;
        String search_string = search_position.toLowerCase()
                                              .replaceAll("[^a-z]", "");
        boolean position_found = false;
        for ( Position position : this.positions ) {
            String title_string = position.getTitle().toLowerCase()
                                                     .replaceAll("[^a-z]", "");
            if ( title_string.contains(search_string) ) {
                result_sum = result_sum + position.getSalary();
                position_found = true;
            }
        }
        if ( position_found ) {
            return result_sum;
        } else {
            return null;
        }
    }
    
    public Double getTotalSalary() {
        double total_pay = 0.0;
        for (Position position : this.positions) {
            total_pay = total_pay + position.getSalary();
        }
        return total_pay;
    }

    public String getSalaryStr() {
        return String.format("%,.2f", this.getTotalSalary());
    }

    public String getFirstName() { return this.first_name; }

    public String getLastName() { return this.last_name; }

    public Long getTransID() { return this.employee_id; }

    public String getDepartmentsStr() {
        StringBuilder result_departments = new StringBuilder();
        for (Position position : this.positions ) {
            result_departments.append(position.getDeptName());
            result_departments.append("\n");
        }
        return result_departments.toString();
    }

    public String getPositionsStr() {
        StringBuilder result_positions = new StringBuilder();
        for ( Position position : this.positions ) {
            result_positions.append(position.getTitle());
            result_positions.append("\n");
        }
        return  result_positions.toString();
    }

    public String getSearchName() {
        return this.first_name + " " + this.last_name;
    }

    public String getSortName() { return this.last_name + this.first_name; }

    @Override
    public int hashCode() {
        return 59 * 61 * ((int) this.employee_id);
    }

    @Override
    public boolean equals(Object obj){
        if (obj == this) {
            return true;
        } 
        if (obj.getClass() != this.getClass()) {
            return false;
        }
        Employee other = (Employee) obj;
        return other.hashCode() == this.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder employee_string = new StringBuilder("\nListing position details for employee " + this.first_name.toUpperCase() + " " + this.last_name.toUpperCase() + " (" + this.employee_id + "):\n");
        for (Position position : this.positions) {
            employee_string.append(position.toString()).append("\n");
        }
        employee_string.append("Total pay: $").append(this.getTotalSalary());
        return employee_string.toString();
    }
}
