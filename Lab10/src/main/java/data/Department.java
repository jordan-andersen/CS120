package data;

import java.util.*;

public class Department {
    
    // ATTRIBUTES
    private final String department_name;  // <-- department_division [4]
    private final String department_code;  // <-- department_code [27]
    private final List<Employee> employee_list;

    // METHODS
    // Instantiates a DEPARTMENT object.
    public Department( String given_department_name, String given_department_code ) {
        this.department_name = given_department_name;
        this.department_code = given_department_code;
        this.employee_list = new ArrayList<>();
    }

    // Factory method, parses passed data line from data file.
    // Validates proper data formatting and ensures correct instantiation.
    public static Department parse( String[] data_line ) {
        Department created_department = null;
        String error_message = "";

        boolean input_is_correct_length = data_line.length >= 28;
        boolean no_empty_entries = !data_line[4].trim().isEmpty() && !data_line[27].trim().isEmpty();

        if ( input_is_correct_length && no_empty_entries ) {
            boolean department_code_correct_size = data_line[27].length() == 3;
            if ( department_code_correct_size ) {
                String parsed_department_name = data_line[4].substring(0, (data_line[4].length() - 6)).toUpperCase();
                String parsed_department_code = data_line[27];

                created_department = new Department(parsed_department_name, parsed_department_code);
            }
        }

        return created_department;
    }

    public void addEmployee( Employee new_employee ) {
        if ( !employee_list.contains(new_employee) ) {
            this.employee_list.add(new_employee);
        }
    }

    public void sortEmployees() {
        this.employee_list.sort(Comparator.comparing(Employee::getSearchName));
    }

    public Double calculateDepartmentTotalPay() {
        double result_sum = 0.0;
        for ( Employee employee : this.employee_list ) {
            result_sum = result_sum + employee.getSalaryByDepartment(this);
        }
        return result_sum;
    }

    public List<Employee> getDepartmentEmployeesByTitle( String search_position ) {
        List<Employee> result_employee = new ArrayList<>();
        for ( Employee employee : this.employee_list ) {
            List<Position> employee_positions = employee.getPositionByTitle(search_position);
            if ( employee_positions != null ) {
                result_employee.add(employee);
            }
        }
        return result_employee;
    }

    public String getDepartmentName() {
        return this.department_name;
    }

    public String getDepartmentCode() {
        return this.department_code;
    }

    public List<Employee> getDepartmentEmployees() {
        return this.employee_list;
    }

    @Override
    public String toString(){
        return this.department_code + ": " + this.department_name + "\n";
    }
}
