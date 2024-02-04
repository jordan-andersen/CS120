    public class Position{
        
        // ATTRIBUTES
        private static int total_positions = 0;
        private Department department;
        private Employee employee;
        private String title;           // <-- (5) "position_title"
        private String type;            // <-- (6) "position_type"
        private Double pay;             // <-- (8) "pay_total_actual"
        private String contract;        // <-- (24) "contract"
        private String union;           // <-- (26) "bargaining_group_title"

        // METHOD
        public Position(Department given_department, 
                        Employee given_employee, 
                        String given_title,  
                        String given_type, 
                        double given_pay,
                        String given_contract, 
                        String given_union) {
            this.department = given_department;
            this.employee = given_employee;
            this.title = given_title;
            this.type = given_type;
            this.pay = given_pay;
            this.contract = given_contract;
            this.union = given_union;
            Position.total_positions = Position.total_positions + 1;
        }

        public static Position parse( Department parsed_department, Employee parsed_employee, String[] data_line, int line_index ) {
            Position created_position = null;
            String error_message = "";
            
            boolean department_not_null = parsed_department != null;
            boolean employee_not_null = parsed_employee != null;
            boolean input_is_correct_length = data_line.length >= 28;
            boolean pay_is_correct_format = data_line[8].matches("[0-9\\.\\-]+");
            boolean shifted_pay_is_correct = false;
            if ( !pay_is_correct_format ) { if ( data_line[9].matches("[0-9\\.\\-]+") ) { shifted_pay_is_correct = true; } }

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
                double parsed_pay;

                if ( !pay_is_correct_format && shifted_pay_is_correct ) {
                    parsed_title = parsed_title + data_line[6].toUpperCase();
                    if ( union_field_not_null ) { parsed_union = data_line[27]; }
                    if ( contract_field_not_null ) { parsed_contract = data_line[25]; }
                    if ( type_not_null ) { parsed_type = data_line[7]; }
                    parsed_pay = Double.parseDouble(data_line[9]);
                } else {
                    parsed_pay = Double.parseDouble(data_line[8]);
                }
                
                if ( parsed_type.equals("N/A") ) { parsed_type = "Contracted Employee"; }

                created_position = new Position(parsed_department, parsed_employee, parsed_title, parsed_type, parsed_pay, parsed_contract, parsed_union);
            } else if ( !department_not_null ) {
                error_message += ("Line: " + line_index + "\nPosition: Error! NULL DEPARTMENT was passed! Unable to parse line to Position.\n");
                ErrorWriter.writePositionError(error_message);
            } else if ( !employee_not_null ) {
                error_message += ("Line: " + line_index + "\nPosition: Error! NULL EMPLOYEE was passed! Unable to parse line to Position.\n");
                ErrorWriter.writePositionError(error_message);
            } else if ( !input_is_correct_length ) {
                error_message += ("Line: " + line_index + "\nPosition: Error! Incorrect string array length was passed! Unable to parse line to Position.\n" + 
                                    "String array length = " + data_line.length + "\n");
                ErrorWriter.writePositionError(error_message);
            } else if ( !pay_is_correct_format && !shifted_pay_is_correct) {
                error_message += ("Line: " + line_index + "\nPosition: Error! Incorrect pay format was passed! Unable to parse line to Position.\n" +
                                    "Incorrect pay entry is: " + data_line[8] + "\n");
                ErrorWriter.writePositionError(error_message);
            } else {
                error_message += ("Line: " + line_index + "\nPosition: Unknown error! Unable to parse line to Position.\n");
                ErrorWriter.writePositionError(error_message);
            }

            return created_position;
        } 

        public static int getTotalPositions() {
            return Position.total_positions;
        }

        public String getTitle() {
            return this.title;
        }

        public Department getDepartment() {
            return this.department;
        }

        public Employee getEmployee() {
            return this.employee;
        }

        public String getUnion() {
            return this.union;
        }

        public String getContract() {
            return this.contract;
        }

        public String getType() {
            return this.type;
        }

        public double getPay() {
            return this.pay;
        }

        @Override
        public String toString() {
            return this.type + " position: " + this.title.toUpperCase() + " at " + this.department.getDepartmentName().toUpperCase() + ". Paid: $" + String.format("%,.2f", this.pay);
        }
    }
