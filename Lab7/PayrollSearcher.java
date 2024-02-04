import java.util.*;

public class PayrollSearcher {

    // ATTRIBUTES
    private List<Department> department_list;
    private HashMap<String, Integer> department_indices;    // <-- < department_code, department_list_index >
    private HashMap<String, String>  department_codes;      // <-- < department_name, department_code > 

    private List<Employee> employee_list;

    public PayrollSearcher( List<Department> given_department_list, 
                            HashMap<String, Integer> given_department_indices, 
                            HashMap<String, String> given_department_codes,
                            List<Employee> given_employee_list ) {
        this.department_list = given_department_list;
        this.department_indices = given_department_indices;
        this.department_codes = given_department_codes;
        this.employee_list = given_employee_list;
    }

    public static PayrollSearcher parse( List<Department> department_list, List<Employee> employee_list ) {
        PayrollSearcher created_payroll_searcher = null;
        boolean department_list_not_null = department_list != null;
        boolean employee_list_not_null = employee_list != null;

        if ( department_list_not_null && employee_list_not_null ) {
            HashMap<String, Integer> parsed_department_indices = new HashMap<String, Integer>();
            HashMap<String, String>  parsed_department_codes = new HashMap<String, String>(); 
            Integer department_index = 0;

            for ( Department department : department_list ) {
                String department_name = formatDepartmentName(department.getDepartmentName());
                String department_code = department.getDepartmentCode();
                parsed_department_indices.put(department_code, department_index);
                parsed_department_codes.put(department_name, department_code);
                department_index = department_index + 1;
            }

            created_payroll_searcher = new PayrollSearcher(department_list, 
                                                           parsed_department_indices, 
                                                           parsed_department_codes, 
                                                           employee_list);
        }

        return created_payroll_searcher;
    }

    public List<String> searchByEmployee( String search_name ) {
        List<String> result_string = new ArrayList<String>();
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
    
    public List<String> searchByDepartment( String search_department ) {
        Integer department_index = getDepartmentIndex(search_department);
        return this.department_list.get(department_index).getDepartmentEmployees();
    }

    public List<String> searchByPosition( String search_position ) {
        List<String> result_string = new ArrayList<String>();
        result_string.add("\n\n===== Listing employees by position title: " + search_position.toUpperCase() + " =====\n");
        for ( Employee employee : this.employee_list ) {
            String employee_string = employee.getPositionByTitle(search_position);
            if ( employee_string != null ) {
                result_string.add(employee_string + "\n");
            }
        }
        result_string.add("\nTotal employees with position title \'" + search_position.toUpperCase() + "\': " + (result_string.size() - 1));
        return result_string;
    }

    public List<String> searchByDepartmentAndPosition( String search_department, String search_position ) {
        Integer department_index = getDepartmentIndex(search_department);
        return this.department_list.get(department_index).getDepartmentEmployeesByTitle(search_position);
    }
    
    public String calculateDepartmentTotalPay( String search_department ) {
        Integer department_index = getDepartmentIndex(search_department);
        return this.department_list.get(department_index).calculateDepartmentTotalPay();
    }

    public String calculatePositionAvgPay( String search_position ) {
        Double result_sum = 0.0;
        Integer total_employees = 0;
        for ( Employee employee : this.employee_list ) {
            Double employee_pay = employee.calculatePayByPosition(search_position);
            if ( employee_pay != null ) {
                result_sum = result_sum + employee_pay;
                total_employees = total_employees + 1;
            }
        }
        if ( result_sum != 0.0 ) {
            return "\nTotal employees found with position title \'" + search_position.toUpperCase() + "\': " + total_employees + "\n"
                    + "Average salary paid to employees with position title \'" + search_position.toUpperCase() + "\': $"
                    + String.format("%,.2f", result_sum / total_employees);
        } else {
            return null;
        }
    }

    private Integer getDepartmentIndex( String search_department ) {
        search_department = formatDepartmentName(search_department);
        if ( search_department.length() == 3 ) {
            return this.department_indices.get(search_department);
        } else {
            return this.department_indices.get(this.department_codes.get(search_department));
        }
    }
    
    private static String formatDepartmentName( String input_string ) {
        return input_string.toUpperCase()  // <-- Unifies case
                           .replaceAll("[^A-Z]", "");  // <-- Removes all non-alphabet characters
    }

    private static String formatEmployeeName( String input_string ) {
        return input_string.toLowerCase()                                                   // <-- Unifies case
                           .replaceAll("[^a-z]", "")                      // <-- Removes all non-alphabet characters
                           .replaceAll("jr","")                           // <-- Removes JR suffix
                           .replaceAll("iii","")                          // <-- Removes III suffix
                           .replaceAll("iv","");                          // <-- Removes IV suffix       
    }
}