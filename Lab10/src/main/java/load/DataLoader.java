package load;

import java.io.File;
import java.io.IOException;
import java.util.*;
import data.*;

public class DataLoader {
    
    // ATTRIBUTES
    // Stores the current line index to for error tracking.
    private Integer line_index;

    private final List<Department> department_list;
    private final HashMap<String, Integer> department_indices;
    private Integer department_list_index;

    private final List<Employee> employee_list;
    private final HashMap<Long, Integer> employee_indices;
    private Integer employee_list_index;
    private final HashSet<Integer> employee_department_hashes;

    // METHODS
    // Instantiates a DATA-LOADER object
    public DataLoader() {
        this.line_index = 1;

        this.department_list = new ArrayList<>();
        this.department_indices = new HashMap<>();
        this.department_list_index = 0;

        this.employee_list = new ArrayList<>();
        this.employee_indices = new HashMap<>();
        this.employee_list_index = 0;

        this.employee_department_hashes = new HashSet<>();
    }

    // Loads data FILE into a LIST of individual file lines. Then iterates over the LIST three times to instantiate
    // the DEPARTMENT, EMPLOYEE, and POSITION objects. This ensures that EMPLOYEES can be added to their respective
    // DEPARTMENT objects, and ensures that POSITIONS can be added to their respective EMPLOYEE and DEPARTMENT objects.
    public void loadFile( File data_file ) {
        try {
            // READS FILE LINES INTO ARRAYLIST
            Scanner data_input = new Scanner(data_file);
            data_input.nextLine(); // <-- skips header line.
            List<String> file_lines = new ArrayList<>();

            while ( data_input.hasNext() ) {
                file_lines.add(data_input.nextLine());
            }

            data_input.close();

            // INSTANTIATES DEPARTMENTS
            for ( String line : file_lines ) {
                parseDepartment(line);
                this.line_index = this.line_index + 1;
            }
            this.line_index = 1;
            
            // INSTANTIATES EMPLOYEES
            for ( String line : file_lines ) {
                parseEmployee(line);
                this.line_index = this.line_index + 1;
            }
            this.line_index = 1; 

            // INSTANTIATES POSITIONS
            for ( String line : file_lines ) {
                parsePosition(line);
                this.line_index = this.line_index + 1;
            }
            this.line_index = 1;


        } catch ( IOException error ) {
            System.out.println("DATA LOADER: Error! Failed to open data file!");
        }
    } 

    // Parses each file line into DEPARTMENT objects and checks to ensure that each created DEPARTMENT is unique.
    public void parseDepartment( String data_string ) {
        String[] data_line = data_string.split(",");
        String department_code = data_line[27];
        if (!this.department_indices.containsKey(department_code)) {
            Department new_department = Department.parse(data_line);
            if ( new_department != null ) {
                this.department_list.add(new_department);
                this.department_indices.put(department_code, this.department_list_index);
                this.department_list_index = this.department_list_index + 1;
            } else {
                System.out.println("DATA LOADER: Line " + line_index + " Error! Department failed instantiation!");
            }
        }
    }

    // Parses each file line into EMPLOYEE objects and checks to ensure that each created EMPLOYEE is unique.
    public void parseEmployee( String data_string ) {
        String[] data_line = data_string.split(",");
        Long employee_id = Long.parseLong(data_line[1]);

        if (!this.employee_indices.containsKey(employee_id)) {  // <-- Checks if employee already exists.
            Employee new_employee = Employee.parse(data_line);
            if ( new_employee != null ) {
                this.employee_list.add(new_employee);
                this.employee_indices.put(employee_id, this.employee_list_index);
                this.employee_list_index = this.employee_list_index + 1;
            } else {
                System.out.println("DATA LOADER: Line " + line_index + " Error! Employee failed instantiation!");
            }
        }
    }

    // Parses each file line into POSITION objects.
    public void parsePosition( String data_string ) {
        String[] data_line = data_string.split(",");

        Long employee_id = Long.parseLong(data_line[1]);
        Integer employee_index = this.employee_indices.get(employee_id);

        String department_code = data_line[27];
        Integer department_index = this.department_indices.get(department_code);

        boolean department_code_correct_format = department_code.length() == 3;
        if ( department_code_correct_format ) {
            Department current_department = this.department_list.get(department_index);
            Employee current_employee = this.employee_list.get(employee_index);
            Integer employee_department_hash = current_employee.hashCode() + current_department.hashCode();
            Position new_position = Position.parse(current_department,
                                                   current_employee,
                                                   data_line);
            if ( new_position != null ) {
                current_employee.addPosition(new_position);
                if ( !this.employee_department_hashes.contains(employee_department_hash) ){
                    current_employee.addDepartment(current_department);
                    this.employee_department_hashes.add(employee_department_hash);
                }
            } else {
                System.out.println("DATA LOADER: Line " + line_index + " Error! Position failed instantiation!");
            }
        } else {
            System.out.println("DATA LOADER: Line " + line_index + " Error! Position failed instantiation!");
        }

    }

    // Returns EMPLOYEE list.
    public List<Employee> getEmployeeList() {
        return this.employee_list;
    }

    // Returns DEPARTMENT list.
    public List<Department> getDepartmentList() {
        return this.department_list;
    }

}
