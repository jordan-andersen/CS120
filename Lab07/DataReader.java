import java.io.File;
import java.io.IOException;
import java.util.*;

public class DataReader {
    
    // ATTRIBUTES
    private Integer line_index; 

    private List<Department> department_list;
    private HashMap<String, Integer> department_indices;
    private Integer department_list_index;  

    private List<Employee> employee_list;
    private HashMap<Long, Integer> employee_indices;
    private Integer employee_list_index;

    private HashSet<Integer> employee_department_hashes;

    // METHODS

    public DataReader() {
        this.line_index = 1;

        this.department_list = new ArrayList<Department>();
        this.department_indices = new HashMap<String, Integer>();
        this.department_list_index = 0;  // <-- Allows for tracking list index of unique departments.

        this.employee_list = new ArrayList<Employee>();
        this.employee_indices = new HashMap<Long, Integer>();
        this.employee_list_index = 0;

        this.employee_department_hashes = new HashSet<Integer>();
    }

    public void readFile( File data_file ) {
        try {
            // READS FILE LINES INTO ARRAYLIST
            Scanner data_input = new Scanner(data_file);
            data_input.nextLine(); // <-- skips header line.
            List<String> file_lines = new ArrayList<String>();

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


        } catch (IOException error) {
            ErrorWriter.writeError("\nDataReader: Error! IOException found!\n");
        }
    } 

    public void parseDepartment( String data_string ) {
        String[] data_line = data_string.split(",");
        String department_code = data_line[27];
        if (!this.department_indices.containsKey(department_code)) {
            Department new_department = Department.parse(data_line, this.line_index);
            if ( new_department != null ) {
                this.department_list.add(new_department);
                this.department_indices.put(department_code, this.department_list_index);
                this.department_list_index = this.department_list_index + 1;
            } else {
                ErrorWriter.writeError("Line: " + line_index + "\nDataReader: Error! Department failed instantiation!\n");
            }
        }
    }


    public void parseEmployee( String data_string ) {
        String[] data_line = data_string.split(",");
        Long employee_id = Long.parseLong(data_line[1]);

        if (!this.employee_indices.containsKey(employee_id)) {  // <-- Checks if employee already exists.
            Employee new_employee = Employee.parse(data_line, this.line_index);
            if ( new_employee != null ) {
                this.employee_list.add(new_employee);
                this.employee_indices.put(employee_id, this.employee_list_index);
                this.employee_list_index = this.employee_list_index + 1;
            } else {
                ErrorWriter.writeError("Line: " + line_index + "\nDataReader: Error! Employee failed instantiation!\n");
            }
        }
    }


    public void parsePosition( String data_string ) {
        String[] data_line = data_string.split(",");

        Long employee_id = Long.parseLong(data_line[1]);
        Integer employee_index = this.employee_indices.get(employee_id);

        String department_code = data_line[27];
        Integer department_index = this.department_indices.get(department_code);

        Boolean department_code_correct_format = department_code.length() == 3;
        if ( department_code_correct_format ) {
            Department current_department = this.department_list.get(department_index);
            Employee current_employee = this.employee_list.get(employee_index);
            Integer employee_department_hash = current_employee.hashCode() + current_department.hashCode();
            Position new_position = Position.parse(current_department,
                                                   current_employee,
                                                   data_line, 
                                                   this.line_index);
            if ( new_position != null ) {
                current_employee.addPosition(new_position);  // <-- Adds Position object to Employee object.
                if ( !this.employee_department_hashes.contains(employee_department_hash) ){  // <-- Checks if Employee-Department combo exists.
                    current_employee.addDepartment(current_department);                 // <-- If not, adds Department to Employee, which adds Employee to Department.
                    this.employee_department_hashes.add(employee_department_hash);           // <-- Adds Employee-Department combo to HashSet.
                }
            } else {
                ErrorWriter.writeError("Line: " + line_index + "\nDataReader: Unknown Error! Position failed instantiation!\n");
            }
        } else {
            ErrorWriter.writePositionError("Line: " + line_index + "\nDataReader: Error! Incorrect department_code entry was passed! Unable to parse Position!\n");
        }

    }

    public List<Employee> getEmployeeList() {
        return this.employee_list;
    }

    public List<Department> getDepartmentList() {
        return this.department_list;
    }

}
