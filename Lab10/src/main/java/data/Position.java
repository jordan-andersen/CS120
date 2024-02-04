package data;

public class Position{
        
    // ATTRIBUTES
    private final Department department;
    private final Employee employee;
    private final String title;           // <-- (5) "position_title"
    private final String type;            // <-- (6) "position_type"
    private final Double salary;             // <-- (8) "pay_total_actual"
    private final String contract;        // <-- (24) "contract"
    private final String union;           // <-- (26) "bargaining_group_title"

    // METHOD
    // Instantiates a POSITION object.
    public Position(Department given_department,
                    Employee given_employee,
                    String given_title,
                    String given_type,
                    double given_salary,
                    String given_contract,
                    String given_union) {
        this.department = given_department;
        this.employee = given_employee;
        this.title = given_title;
        this.type = given_type;
        this.salary = given_salary;
        this.contract = given_contract;
        this.union = given_union;
    }

    // Factory method, parses passed data line from data file.
    // Validates proper data formatting and ensures correct instantiation.
    public static Position parse( Department parsed_department, Employee parsed_employee, String[] data_line ) {
        Position created_position = null;
        String error_message = "";

        boolean department_not_null = parsed_department != null;
        boolean employee_not_null = parsed_employee != null;
        boolean input_is_correct_length = data_line.length >= 28;
        boolean pay_is_correct_format = data_line[8].matches("[0-9.\\-]+");
        boolean shifted_pay_is_correct = false;
        if ( !pay_is_correct_format ) { if ( data_line[9].matches("[0-9.\\-]+" ) ) { shifted_pay_is_correct = true; } }

        boolean required_attributes_correct = department_not_null
                                            && employee_not_null
                                            && input_is_correct_length
                                            && (pay_is_correct_format || shifted_pay_is_correct);

        boolean title_not_null = data_line[5] != null && !data_line[5].trim().isEmpty();
        boolean contract_field_not_null = data_line[24] != null && !data_line[24].trim().isEmpty();
        boolean union_field_not_null = data_line[26] != null && !data_line[26].trim().isEmpty();
        boolean type_not_null = data_line[6] != null && !data_line[6].trim().isEmpty();

        if ( required_attributes_correct ) {
            String parsed_title = "UNKNOWN TITLE";
            if ( title_not_null ) { parsed_title = data_line[5].toUpperCase(); }
            String parsed_union = "NO UNION";
            if ( union_field_not_null ) { parsed_union = data_line[26]; }
            String parsed_contract = "NO CONTRACT";
            if ( contract_field_not_null ) { parsed_contract = data_line[24]; }
            String parsed_type ="UNKNOWN TYPE";
            if ( type_not_null ) { parsed_type = data_line[6]; }
            double parsed_salary;

            if ( !pay_is_correct_format && shifted_pay_is_correct && data_line[6] != null) {
                parsed_title = parsed_title + data_line[6].toUpperCase();
                if ( union_field_not_null ) { parsed_union = data_line[27]; }
                if ( contract_field_not_null ) { parsed_contract = data_line[25]; }
                if ( type_not_null ) { parsed_type = data_line[7]; }
                parsed_salary = Double.parseDouble(data_line[9]);
            } else {
                parsed_salary = Double.parseDouble(data_line[8]);
            }

            if ( parsed_type.equals("N/A") ) { parsed_type = "Contracted Employee"; }

            created_position = new Position(parsed_department,
                                            parsed_employee,
                                            parsed_title,
                                            parsed_type,
                                            parsed_salary,
                                            parsed_contract,
                                            parsed_union);
        }

        return created_position;
    }

    public String getTitle() {
        return this.title;
    }

    public Department getDepartment() {
        return this.department;
    }

    public Employee getEmployee() {
        return employee;
    }

    public String getContract() {
        return contract;
    }

    public String getUnion() {
        return union;
    }

    public String getLastName() { return this.employee.getLastName(); }

    public String getFirstName() { return this.employee.getFirstName(); }

    public Long getTransID() { return this.employee.getTransID(); }

    public String getDeptName() { return this.department.getDepartmentName(); }

    public double getSalary() {
        return this.salary;
    }

    public String getSalaryStr() { return String.format("%,.2f", this.salary); }

    @Override
    public String toString() {
        return this.type + " position: " + this.title.toUpperCase() + " at " + this.department.getDepartmentName().toUpperCase() + ". Paid: $" + String.format("%,.2f", this.salary);
    }
}
