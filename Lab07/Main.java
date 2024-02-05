import java.io.*;
import java.util.*;

public class Main {

    private static Scanner user_input = new Scanner(System.in);

    public static void main( String[] args ) {
        boolean end_program = false;

        System.out.print(	
                        "\n============================ USAGE ============================\n" +
                        "Ensure the data files are in the ./resources directory.\n" +
                        "Input \'[insert_file_name].csv\' to load desired data file.\n" +
                        "Input \'quit\' to end program.\n" +
                        "===============================================================\n\n");
        
        while ( !end_program ) {
            // FILE INPUT
            System.out.print("Input filepath to data file: resources/");
            String file_name = user_input.nextLine().trim();
            String file_path = "resources/" + file_name;
            File data_file = new File(file_path);
            end_program = checkInput(file_name);

            if ( !end_program && data_file.exists() && file_path.endsWith(".csv")) {  // <-- checks if end_program is false and that the data file exists and is correct format
                end_program = true;

                // DATA LOADING
                System.out.println("\nLoading...\n");
                ErrorWriter.initializeErrorFile(); // < Initializes error file.
                DataReader payroll_data = new DataReader();
                payroll_data.readFile(data_file);  // <-- Loads data file.
                List<Department> department_list = payroll_data.getDepartmentList();  // <-- retrieves department list
                List<Employee> employee_list = payroll_data.getEmployeeList();  // <-- retrieves employee list

                // DATA SORTING
                System.out.println("Sorting Departments...\n");
                Collections.sort(department_list, Comparator.comparing(Department ::getDepartmentCode));

                System.out.println("Sorting Employees and printing out Departments...\n");
                for ( Department department : department_list ) {
                    System.out.print(department.toString());  // <-- lists each loaded Department
                    department.sortEmployees();  // <-- sorts each Department's employee list
                }
                
                // ERROR REPORTING
                System.out.println(ErrorWriter.getErrorReport());
                System.out.println("Total DEPARTMENTS loaded: " + Department.getTotalDepartments() + "\n" +
                                   "Total EMPLOYEES loaded: " + Employee.getTotalEmployees() + "\n" +
                                   "Total POSITIONS loaded: " + Position.getTotalPositions() + "\n");
                
                // BEGIN MAIN PROGRAM
                PayrollSearcher payroll_search = PayrollSearcher.parse(department_list, employee_list);
                mainMenu(payroll_search);
            } else {
                System.out.println("Error: Invalid file provided!");
            }
        }
        System.out.println("Quitting...");
        user_input.close();
    }

    private static void mainMenu ( PayrollSearcher payroll_search ) {
        // =========== MAIN MENU ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.println("\n========= MAIN MENU =========\n" +
                                "1. Search employees by Name\n" +
                                "2. Search employees by Department\n" +
                                "3. Search employees by Position Title\n" +
                                "4. Search employees by Department & Position Title\n" +
                                "5. Calculate total salary paid by Department\n" +
                                "6. Calculate average salary paid by Position Title\n");
            System.out.print("Input selection: ");
            String user_selection = user_input.nextLine().trim();
            end_loop = checkInput(user_selection);
            if ( end_loop == true ) {
                return;
            } else if ( user_selection.equals("1")) {
                subMenu1(payroll_search);
            } else if ( user_selection.equals("2")) {
                subMenu2(payroll_search);
            } else if ( user_selection.equals("3")) {
                subMenu3(payroll_search);
            } else if ( user_selection.equals("4")) {
                subMenu4(payroll_search);
            } else if ( user_selection.equals("5")) {
                subMenu5(payroll_search);
            } else if ( user_selection.equals("6")) {
                subMenu6(payroll_search);
            } else {
                System.out.println("Invalid selection! Enter only 1 - 6 or \'quit\' to end program.\n\n");
            }
        } 
    }

    private static void subMenu1( PayrollSearcher payroll_search ) {
        // =========== SUBMENU 1: SEARCH EMPLOYEES BY NAME ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.print("\n\n======== Print all employees by Name ========\n" +
                                "Input \'first-name last-name\'' or \'back\' to return to main menu\n\n" +
                                "Input: ");
            String user_selection_name = user_input.nextLine().trim();
            Boolean safe_input = user_selection_name != null && !user_selection_name.isEmpty();
            end_loop = checkInput(user_selection_name);
            if ( !end_loop && safe_input ) { 
                List<String> search_results = payroll_search.searchByEmployee(user_selection_name);
                Integer result_index = 0;
                if ( search_results != null ){
                    for ( String result : search_results ) {
                        System.out.println(result);
                        if ( result_index % 100 == 0 && result_index != 0 ) {
                            System.out.println("\nDisplaying " + (result_index - 100) + " to " + result_index + " of " + (search_results.size() - 2) + " results. \nPress \'enter\' to continue...");
                            user_input.nextLine();
                        }
                        result_index = result_index + 1;
                    }
                } else {
                    System.out.println("No employee found!");
                }
            }
        }
    }

    private static void subMenu2( PayrollSearcher payroll_search ) {
        // =========== SUBMENU 2: SEARCH EMPLOYEES BY DEPARTMENT ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.print("\n\n======== Print all employees by Department ========\n" +
                                "Input \'department-name or department-code\'' or \'back\' to return to main menu\n\n" +
                                "Input: ");
            String user_selection_department = user_input.nextLine().trim();
            Boolean safe_input = user_selection_department != null && !user_selection_department.isEmpty();
            end_loop = checkInput(user_selection_department);
            if ( !end_loop && safe_input ) { 
                List<String> search_results = payroll_search.searchByDepartment(user_selection_department);
                Integer result_index = 0;
                for ( String result : search_results ) {
                    System.out.println(result);
                    if ( result_index % 100 == 0 && result_index != 0 ) {
                        System.out.println("\nDisplaying " + (result_index - 100) + " to " + result_index + " of " + (search_results.size() - 2) + " results. \nPress \'enter\'' to continue...");
                        user_input.nextLine();
                    }
                    result_index = result_index + 1;
                }
            }
        }
    }

    private static void subMenu3( PayrollSearcher payroll_search ) {
        // =========== SUBMENU 3: SEARCH EMPLOYEES BY POSITION TITLE ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.print("\n\n======== SEARCH EMPLOYEES BY POSITION TITLE ========\n" +
                                "Input \'position-title\'' or \'back\' to return to main menu\n\n" +
                                "Input: ");
            String user_selection_position = user_input.nextLine().trim();
            Boolean safe_input = user_selection_position != null && !user_selection_position.isEmpty();
            end_loop = checkInput(user_selection_position);
            if ( !end_loop && safe_input ) { 
                List<String> search_results = payroll_search.searchByPosition(user_selection_position);
                Integer result_index = 0;
                for ( String result : search_results ) {
                    System.out.println(result);
                    if ( result_index % 100 == 0 && result_index != 0 ) {
                        System.out.println("\nDisplaying " + (result_index - 100) + " to " + result_index + " of " + (search_results.size() - 2) + " results. \nPress \'enter\'' to continue...");
                        user_input.nextLine();
                    }
                    result_index = result_index + 1;
                }
            }
        }
    }

    private static void subMenu4( PayrollSearcher payroll_search ) {
        // =========== SUBMENU 4: SEARCH EMPLOYEES BY DEPARTMENT & POSITION TITLE ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.print("\n\n======== SEARCH EMPLOYEES BY DEPARTMENT & POSITION TITLE ========\n" +
                                "Input \'department-name or department-code\'' or \'back\' to return to main menu\n\n" +
                                "Input: ");
            String user_selection_department = user_input.nextLine().trim();
            Boolean safe_input_department = user_selection_department != null && !user_selection_department.isEmpty();
            end_loop = checkInput(user_selection_department);
            if ( !end_loop && safe_input_department ) {
                System.out.print("\nInput \'position-title\'' or \'back\' to return to main menu\n" +
                                "Input: ");
                String user_selection_position = user_input.nextLine().trim();  
                Boolean safe_input_position = user_selection_position != null && !user_selection_position.isEmpty();        
                end_loop = checkInput(user_selection_position);
                if ( !end_loop && safe_input_position ) { 
                    List<String> search_results = payroll_search.searchByDepartmentAndPosition(user_selection_department, user_selection_position);
                    Integer result_index = 0;
                    for ( String result : search_results ) {
                        System.out.println(result);
                        if ( result_index % 100 == 0 && result_index != 0 ) {
                            System.out.println("\nDisplaying " + (result_index - 100) + " to " + result_index + " of " + (search_results.size() - 2) + " results. \nPress \'enter\'' to continue...");
                            user_input.nextLine();
                        }
                        result_index = result_index + 1;
                    } 
                }
            }
        }
    }

    private static void subMenu5( PayrollSearcher payroll_search ) {
        // =========== SUBMENU 5: CALCULATE TOTAL SALARY PAID BY DEPARTMENT ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.print("\n\n======== CALCULATE TOTAL SALARY PAID BY DEPARTMENT ========\n" +
                                "Input \'department-name or department-code\'' or \'back\' to return to main menu\n\n" +
                                "Input: ");
            String user_selection_department = user_input.nextLine().trim();
            Boolean safe_input = user_selection_department != null && !user_selection_department.isEmpty();
            end_loop = checkInput(user_selection_department);
            if ( !end_loop && safe_input ) { 
                String total_salary_result = payroll_search.calculateDepartmentTotalPay(user_selection_department);
                if ( total_salary_result != null && !total_salary_result.isEmpty() ) { 
                    System.out.println(total_salary_result);
                } else {
                    System.out.println("\nNo department with name \'" + user_selection_department.toUpperCase() + "\' found!"); 
                }
            }
        }
    }

    private static void subMenu6( PayrollSearcher payroll_search ) {
        // =========== SUBMENU 6: CALCULATE AVERAGE SALARY PAID BY POSITION TITLE ===========
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.print("\n\n======== Calculate average salary paid by Position Title ========\n" +
                                "Input \'position-title\'' or \'back\' to return to main menu\n\n" +
                                "Input: ");
            String user_selection_position = user_input.nextLine().trim();
            Boolean safe_input = user_selection_position != null && !user_selection_position.isEmpty();
            end_loop = checkInput(user_selection_position);
            if ( !end_loop && safe_input ) { 
                String average_pay_result = payroll_search.calculatePositionAvgPay(user_selection_position);
                if ( average_pay_result != null && !average_pay_result.isEmpty() ){
                    System.out.println(average_pay_result);
                } else {
                    System.out.println("\nNo position with name \'" + user_selection_position.toUpperCase() + "\' found!");
                }
            }
        }
    }

    private static boolean checkInput( String input_string ) {  //<-- Checks if input is correct 
        if ( input_string.equalsIgnoreCase("back") || input_string.equalsIgnoreCase("quit") ) {
            return true;
        } else {
            return false;
        }
    }
}
