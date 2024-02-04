package search;

import java.util.*;
import data.*;

public class DataSearcher {

    // ATTRIBUTES
    private final List<Department> department_list;
    private final HashMap<String, Integer> department_indices;    // <-- < department_code, department_list_index >
    private final HashMap<String, String>  department_codes;      // <-- < department_name, department_code >
    private final List<Employee> employee_list;
    private final HashMap<Long, Integer> employee_indices;

    // Instantiates a DATA-SEARCHER object.
    public DataSearcher(List<Department> given_department_list,
                        HashMap<String, Integer> given_department_indices,
                        HashMap<String, String> given_department_codes,
                        List<Employee> given_employee_list,
                        HashMap<Long, Integer> given_employee_indices) {
        this.department_list = given_department_list;
        this.department_indices = given_department_indices;
        this.department_codes = given_department_codes;
        this.employee_list = given_employee_list;
        this.employee_indices = given_employee_indices;
    }

    // Factory method, creates necessary collections for DATA-SEARCHER instantiation.
    public static DataSearcher parse(List<Department> department_list, List<Employee> employee_list ) {
        DataSearcher created_payroll_searcher = null;
        boolean department_list_not_null = department_list != null;
        boolean employee_list_not_null = employee_list != null;

        if ( department_list_not_null && employee_list_not_null ) {
            HashMap<String, Integer> parsed_department_indices = new HashMap<>();
            HashMap<String, String>  parsed_department_codes = new HashMap<>();
            HashMap<Long, Integer> parsed_employee_indices = new HashMap<>();
            int department_index = 0;
            int employee_index = 0;

            // CREATES DEPARTMENT NAME > CODE AND DEPARTMENT CODE > LIST INDEX HASHSETS.
            for ( Department department : department_list ) {
                String department_name = formatDepartmentName(department.getDepartmentName());
                String department_code = department.getDepartmentCode();
                parsed_department_indices.put(department_code, department_index);
                parsed_department_codes.put(department_name, department_code);
                department_index = department_index + 1;
            }

            // CREATES EMPLOYEE ID > LIST INDEX HASHSET.
            for ( Employee employee : employee_list ) {
                Long employee_id = employee.getTransID();
                parsed_employee_indices.put(employee_id, employee_index);
                employee_index = employee_index + 1;
            }
            created_payroll_searcher = new DataSearcher(department_list,
                                                        parsed_department_indices,
                                                        parsed_department_codes,
                                                        employee_list,
                                                        parsed_employee_indices);
        }

        return created_payroll_searcher;
    }

    // Search EMPLOYEES by full name or trans ID.
    public List<Employee> searchByEmployee( String search_name ) {
        List<Employee> result_employees = new ArrayList<>();
        if ( search_name.matches("^[0-9]+$")) {
            long employee_id = Long.parseLong(search_name);
            int employee_index = employee_indices.get(employee_id);
            result_employees.add(employee_list.get(employee_index));
        } else {
            String name_string = formatEmployeeName(search_name);
            for ( Employee employee : this.employee_list ) {
                String employee_name = formatEmployeeName(employee.getSearchName());
                if ( employee_name.equals(name_string) ) {
                    result_employees.add(employee);
                }
            }
        }
        return result_employees;
    }

    // Search EMPLOYEES by DEPARTMENT name or code.
    public List<Employee> searchByDepartment( String search_department ) {
        Integer department_index = getDepartmentIndex(search_department);
        return this.department_list.get(department_index).getDepartmentEmployees();
    }

    // Search POSITION by partial name.
    public List<Position> searchByPosition( String search_position ) {
        List<Position> result_positions = new ArrayList<>();
        for ( Employee employee : this.employee_list ) {
            List<Position> employee_positions = employee.getPositionByTitle(search_position);
            if ( employee_positions != null ) {
                result_positions.addAll(employee_positions);
            }
        }
        return result_positions;
    }

    // Search EMPLOYEES by DEPARTMENT name or code and POSITION partial name.
    public List<Employee> searchByDepartmentAndPosition( String search_department, String search_position ) {
        Integer department_index = getDepartmentIndex(search_department);
        return this.department_list.get(department_index).getDepartmentEmployeesByTitle(search_position);
    }

    // Calculate DEPARTMENT total salary by DEPARTMENT name or code.
    public Double calculateDepartmentTotalSalary(String search_department ) {
        Integer department_index = getDepartmentIndex(search_department);
        return this.department_list.get(department_index).calculateDepartmentTotalPay();
    }

    // Calculate mean salary by POSITION partial name.
    public Double calculateMeanSalary(String search_position ) {
        List<Position> found_positions = this.searchByPosition(search_position);
        double salary_sum = 0.0;
        for ( Position position : found_positions ) {
            salary_sum = salary_sum + position.getSalary();
        }
        return salary_sum / found_positions.size();
    }

    // Calculate median salary by POSITION partial name.
    public Double calculateMedianSalary( String search_position ) {
        List<Position> found_positions = this.searchByPosition(search_position);
        found_positions.sort(Comparator.comparing(Position::getSalary));
        if ( found_positions.size() % 2 == 0 ) {
            return ( found_positions.get(found_positions.size() / 2).getSalary() +
                     found_positions.get(found_positions.size() / 2 - 1).getSalary()) / 2;
        } else {
            return found_positions.get(found_positions.size() / 2).getSalary();
        }
    }

    // Returns DEPARTMENT index by via hashset operations.
    private Integer getDepartmentIndex( String search_department ) {
        search_department = formatDepartmentName(search_department);
        if ( search_department.length() == 3 ) {
            return this.department_indices.get(search_department);
        } else {
            return this.department_indices.get(this.department_codes.get(search_department));
        }
    }

    // Unifies format of DEPARTMENT names.
    private static String formatDepartmentName( String input_string ) {
        return input_string.toUpperCase()  // <-- Unifies case
                           .replaceAll("[^A-Z]", "");  // <-- Removes all non-alphabet characters
    }

    // Unifies format of EMPLOYEE search names.
    private static String formatEmployeeName( String input_string ) {
        return input_string.toLowerCase()                                                   // <-- Unifies case
                           .replaceAll("[^a-z]", "")                      // <-- Removes all non-alphabet characters
                           .replaceAll("jr","")                           // <-- Removes JR suffix
                           .replaceAll("iii","")                          // <-- Removes III suffix
                           .replaceAll("iv","");                          // <-- Removes IV suffix       
    }
}