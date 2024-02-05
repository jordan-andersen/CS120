import java.util.*;

public class Department {
    
    // ATTRIBUTES
    private static int department_count = 0;
    private String department_name;  // <-- department_division [4]
    private String department_code;  // <-- department_code [27]
    private HashSet<Long> employee_ids;
    private List<Employee> employee_list;

    // METHODS
    public Department( String given_department_name, String given_department_code ) {
        this.department_name = given_department_name;
        this.department_code = given_department_code;
        this.employee_ids = new HashSet<Long>();
        this.employee_list = new ArrayList<Employee>();
        Department.department_count += 1;
    }

    public static Department parse( String[] data_line, int line_index ) {
        Department created_department = null;
        String error_message = "";

        boolean input_is_correct_length = data_line.length >= 28;
        boolean department_code_correct_size = data_line[27].length() == 3;
        boolean no_null_entries = data_line[4] != null && !data_line[4].trim().isEmpty() &&
                                  data_line[27] != null && !data_line[27].trim().isEmpty();

        if ( department_code_correct_size && input_is_correct_length && no_null_entries ) {
            String parsed_department_name = data_line[4].substring(0, (data_line[4].length() - 6)).toUpperCase();
            String parsed_department_code = data_line[27]; 

            created_department = new Department(parsed_department_name, parsed_department_code);
        } else if ( !department_code_correct_size ) {
            error_message = ("Line: " + line_index + "\nDepartment: Error! Incorrect department_code entry was passed! Unable to parse line to Department.\n" +
                              "Department Code = " + data_line[27] + "\n");
            ErrorWriter.writeDepartmentError(error_message);
        } else if ( !input_is_correct_length ) {
            error_message = ("Line: " + line_index + "\nDepartment: Error! Incorrect string array length was passed! Unable to parse line to Department.\n" +
                              "String array length = " + data_line.length + "\n");
            ErrorWriter.writeDepartmentError(error_message);
        } else if ( !no_null_entries ) {
            error_message = ("Line: " + line_index + "\nDepartment: Error! A NULL value was passed! Unable to parse line to Department.\n" +
                              "4: " + data_line[4].isEmpty() + "\n" + 
                              "27: " + data_line[27].isEmpty() + "\n");
            ErrorWriter.writeDepartmentError(error_message);
        } else {
            error_message = ("Line: " + line_index + "\nDepartment: Unknown error! Unable to parse line to Department.\n");
            ErrorWriter.writeDepartmentError(error_message);
        }

        return created_department;
    }

    public static int getTotalDepartments() {
        return Department.department_count;
    } 

    public int getEmployeesCount() {
        return this.employee_list.size();
    } 

    public void addEmployee( Employee new_employee ) {
        if ( !employee_list.contains(new_employee) ) {
            this.employee_list.add(new_employee);
        }
    }
    
    public void sortEmployees() {
        Collections.sort(this.employee_list, Comparator.comparing(Employee ::getName));
    }

    public HashSet<String> searchDepartmentEmployees( String search_name ) {
        HashSet<String> result_string = new HashSet<String>();
        String name_string = formatEmployeeName(search_name);
        result_string.add("\n\n===== Listing employees by name: " + search_name.toUpperCase() + " =====\n");
        for ( Employee employee : this.employee_list ) {
            String employee_name = formatEmployeeName(employee.getName());
            if ( employee_name.equals(name_string) ) {
                result_string.add(employee.toString() + "\n"); 
            }
        }
        result_string.add("\nTotal employees with name \'" + search_name.toUpperCase() + "\': " + (result_string.size() - 1));
        return result_string;
    }

    public List<String> getDepartmentEmployees() {
        List<String> result_string = new ArrayList<String>();
        result_string.add("\n\n===== Listing " + this.department_name.toUpperCase() + " employees =====\n");
        for ( Employee employee : this.employee_list ) {
            result_string.add(employee.getPositionByDepartment(this) + "\n");
        }
        result_string.add("\nTotal Department Employees: " + (this.employee_list.size()));
        return result_string;
    }
    
    public List<String> getDepartmentEmployeesByTitle( String search_position ) {
        List<String> result_string = new ArrayList<String>();
        result_string.add("\n\n===== Listing " + this.department_name.toUpperCase() + " employees by position title: " + search_position.toUpperCase() + " =====\n");
        for ( Employee employee : this.employee_list ) {
            String employee_string = employee.getPositionByTitle(search_position);
            if ( employee_string != null ) {
                result_string.add(employee_string + "\n");
            }
        }
        result_string.add("\nTotal employees with position title \'" + search_position.toUpperCase() + "\': " + (result_string.size() - 1));
        return result_string;
    }

    public String calculateDepartmentTotalPay() {
        Double result_sum = 0.0;
        for ( Employee employee : this.employee_list ) {
            result_sum = result_sum + employee.calculatePayByDepartment(this);
        }
        if ( result_sum != 0.0 ) {
            return "\nTotal salary paid to employees of " + this.department_name.toUpperCase() + ": $" + String.format("%,.2f", result_sum);
        } else {
            return null;
        }
    }

    public String getDepartmentName() {
        return this.department_name;
    }

    public String getDepartmentCode() {
        return this.department_code;
    }

    public HashSet<Long> getDepartmentEmployeeIDs() {
        return this.employee_ids;
    }

    private static String formatEmployeeName( String input_string ) {
        return input_string.toLowerCase()                                                   // <-- Unifies case
                           .replaceAll("[^a-z]", "")                      // <-- Removes all non-alphabet characters
                           .replaceAll("jr","")                           // <-- Removes JR suffix
                           .replaceAll("iii","")                          // <-- Removes III suffix
                           .replaceAll("iv","");                          // <-- Removes IV suffix       
    }

    @Override
    public String toString(){
        return this.department_code + ": " + this.department_name + "\n";
    }
}
