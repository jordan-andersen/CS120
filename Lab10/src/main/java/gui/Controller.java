package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.util.*;
import java.io.*;

import data.*;
import load.DataLoader;
import search.DataSearcher;

public class Controller {
    @FXML
    private MenuItem menu_load_file;
    @FXML
    private MenuItem menu_close;
    @FXML
    private DialogPane opening_dialog;
    @FXML
    private Tab searchByPositionTab;
    @FXML
    private Button position_search_button;
    @FXML
    private TextField position_search_box;
    @FXML
    private CheckBox limit_results_box;
    @FXML
    private TextArea position_average_salary_text;
    @FXML
    private TableView<Position> position_high_table;
    @FXML
    private TableColumn<Position, Long> position_high_id_column;
    @FXML
    private TableColumn<Position, String> position_high_last_column;
    @FXML
    private TableColumn<Position, String> position_high_first_column;
    @FXML
    private TableColumn<Position, String> position_high_dept_column;
    @FXML
    private TableColumn<Position, String> position_high_position_column;
    @FXML
    private TableColumn<Position, String> position_high_salary_column;
    @FXML
    private TableView<Position> position_low_table;
    @FXML
    private TableColumn <Position, Long> position_low_id_column;
    @FXML
    private TableColumn<Position, String> position_low_last_column;
    @FXML
    private TableColumn<Position, String> position_low_first_column;
    @FXML
    private TableColumn<Position, String> position_low_dept_column;
    @FXML
    private TableColumn<Position, String> position_low_position_column;
    @FXML
    private TableColumn<Position, String> position_low_salary_column;
    @FXML
    private Tab searchByEmployeeTab;
    @FXML
    private Button employee_search_button;
    @FXML
    private TextField employee_search_box;
    @FXML
    private TableView<Employee> employee_table;
    @FXML
    private TableColumn<Employee, Long> employee_id_column;
    @FXML
    private TableColumn<Employee, String> employee_last_column;
    @FXML
    private TableColumn<Employee, String> employee_first_column;
    @FXML
    private TableColumn<Employee, String> employee_dept_column;
    @FXML
    private TableColumn<Employee, String> employee_position_column;
    @FXML
    private TableColumn<Employee, String> employee_salary_column;
    @FXML
    private Tab searchByDepartmentTab;
    @FXML
    private Button department_search_button;
    @FXML
    private TextField department_search_box;
    @FXML
    private TableView<Employee> department_table;
    @FXML
    private TableColumn<Employee, Long> department_id_column;
    @FXML
    private TableColumn<Employee, String> department_last_column;
    @FXML
    private TableColumn<Employee, String> department_first_column;
    @FXML
    private TableColumn<Employee, String> department_dept_column;
    @FXML
    private TableColumn<Employee, String> department_position_column;
    @FXML
    private TableColumn<Employee, String> department_salary_column;

    private Stage constructed_stage;
    private List<Employee> employee_list;
    private List<Department> department_list;
    private DataSearcher payroll_search;

    // Initializes GUI objects and data lists when programs loads.
    @FXML
    public void initialize() {

        PropertyValueFactory<Employee, Long> employee_trans_id_getter = new PropertyValueFactory<>("TransID");
        PropertyValueFactory<Employee, String> employee_first_name_getter = new PropertyValueFactory<>("FirstName");
        PropertyValueFactory<Employee, String> employee_last_name_getter = new PropertyValueFactory<>("LastName");
        PropertyValueFactory<Employee, String> employee_department_getter = new PropertyValueFactory<>("DepartmentsStr");
        PropertyValueFactory<Employee, String> employee_positions_getter = new PropertyValueFactory<>("PositionsStr");
        PropertyValueFactory<Employee, String> employee_total_salary_getter = new PropertyValueFactory<>("SalaryStr");

        PropertyValueFactory<Position, Long> position_trans_id_getter = new PropertyValueFactory<>("TransID");
        PropertyValueFactory<Position, String> position_last_name_getter = new PropertyValueFactory<>("LastName");
        PropertyValueFactory<Position, String> position_first_name_getter = new PropertyValueFactory<>("FirstName");
        PropertyValueFactory<Position, String> position_department_getter = new PropertyValueFactory<>("DeptName");
        PropertyValueFactory<Position, String> position_position_getter = new PropertyValueFactory<>("Title");
        PropertyValueFactory<Position, String> position_salary_getter = new PropertyValueFactory<>("SalaryStr");

        position_high_id_column.setCellValueFactory(position_trans_id_getter);
        position_high_last_column.setCellValueFactory(position_last_name_getter);
        position_high_first_column.setCellValueFactory(position_first_name_getter);
        position_high_dept_column.setCellValueFactory(position_department_getter);
        position_high_position_column.setCellValueFactory(position_position_getter);
        position_high_salary_column.setCellValueFactory(position_salary_getter);

        position_low_id_column.setCellValueFactory(position_trans_id_getter);
        position_low_last_column.setCellValueFactory(position_last_name_getter);
        position_low_first_column.setCellValueFactory(position_first_name_getter);
        position_low_dept_column.setCellValueFactory(position_department_getter);
        position_low_position_column.setCellValueFactory(position_position_getter);
        position_low_salary_column.setCellValueFactory(position_salary_getter);

        employee_id_column.setCellValueFactory(employee_trans_id_getter);
        employee_last_column.setCellValueFactory(employee_last_name_getter);
        employee_first_column.setCellValueFactory(employee_first_name_getter);
        employee_dept_column.setCellValueFactory(employee_department_getter);
        employee_position_column.setCellValueFactory(employee_positions_getter);
        employee_salary_column.setCellValueFactory(employee_total_salary_getter);

        department_id_column.setCellValueFactory(employee_trans_id_getter);
        department_last_column.setCellValueFactory(employee_last_name_getter);
        department_first_column.setCellValueFactory(employee_first_name_getter);
        department_dept_column.setCellValueFactory(employee_department_getter);
        department_position_column.setCellValueFactory(employee_positions_getter);
        department_salary_column.setCellValueFactory(employee_total_salary_getter);

        this.employee_list = new ArrayList<>();
        this.department_list = new ArrayList<>();
    }

    // Allows user to select data file, loads the selected data file, and then enables the rest of the GUI for the user
    // when the Load MenuItem is pressed.
    @FXML
    public void loadInputFile(ActionEvent e) throws FileNotFoundException {
        opening_dialog.setContentText("LOADING...");
        FileChooser file_input = new FileChooser();
        File data_file = file_input.showOpenDialog(this.constructed_stage);

        if (data_file != null && String.valueOf(data_file.toPath()).endsWith(".csv")) {
            // LOAD PAYROLL DATA
            DataLoader payroll_data = new DataLoader();
            payroll_data.loadFile(data_file);
            this.department_list = payroll_data.getDepartmentList();
            this.employee_list = payroll_data.getEmployeeList();

            // SORT LISTS
            this.department_list.sort(Comparator.comparing(Department::getDepartmentCode));
            for ( Department department : department_list ) {
                department.sortEmployees();
            }

            // LOAD DATA SEARCHER
            this.payroll_search = DataSearcher.parse(this.department_list, this.employee_list);

            // ENABLE APPLICATION
            opening_dialog.setVisible(false);
            menu_load_file.setDisable(true);
            position_search_box.setEditable(true);
            employee_search_box.setEditable(true);
            department_search_box.setEditable(true);
        } else {
            opening_dialog.setContentText("INVALID FILE PROVIDED!");
        }
    }

    // Searches by POSITION name when the Position search Button is pressed.
    @FXML
    public void positionSearch(ActionEvent e) {
        // ASSIGN VARIABLES AND PROCESS DATA REQUEST.
        String search_position = position_search_box.getText();
        List<Position> result_positions = payroll_search.searchByPosition(search_position);
        result_positions.sort(Comparator.comparing(Position::getSalary));
        int list_size = result_positions.size();
        double mean_salary = payroll_search.calculateMeanSalary(search_position);
        double median_salary = payroll_search.calculateMedianSalary(search_position);
        List<Position> high_position_data = position_high_table.getItems();
        List<Position> low_position_data = position_low_table.getItems();
        List<Position> low_positions;
        List<Position> high_positions;
        
        // CREATE SUB-LISTS HIGH EARNERS AND LOW EARNERS SUB-LISTS AND ADD TO OBSERVED LISTS
        if ( limit_results_box.isSelected() ) {
            low_positions = new ArrayList<>(result_positions.subList(0, 5));
            high_positions = new ArrayList<>(result_positions.subList(list_size - 5, list_size));
        } else {
            low_positions = new ArrayList<>(result_positions.subList(0, list_size / 2));
            high_positions = new ArrayList<>(result_positions.subList(list_size / 2, list_size));
        }
        Collections.reverse(high_positions);

        // UPDATE GUI DATA
        high_position_data.clear();
        low_position_data.clear();
        high_position_data.addAll(high_positions);
        low_position_data.addAll(low_positions);
        String salary_info_text = "Mean Salary: $" +
                String.format("%,.2f", mean_salary) +
                "\nMedian Salary: $" +
                String.format("%,.2f", median_salary);
        position_average_salary_text.setText(salary_info_text);
    }

    // Searches by EMPLOYEE name or transaction ID when the Employee search Button is pressed.
    @FXML
    public void employeeSearch(ActionEvent e) {
        // ASSIGN VARIABLES AND PROCESS DATA REQUEST.
        String search_employee = employee_search_box.getText();
        List<Employee> result_employees = payroll_search.searchByEmployee(search_employee);
        result_employees.sort(Comparator.comparing(Employee::getTransID));
        List<Employee> employees_data = employee_table.getItems();

        // UPDATE GUI DATA
        employees_data.clear();
        employees_data.addAll(result_employees);
    }

    // Searches by DEPARTMENT name or code when the Department search Button is pressed.
    @FXML
    public void departmentSearch(ActionEvent e) {
        // ASSIGN VARIABLES AND PROCESS DATA REQUEST.
        String search_department = department_search_box.getText();
        List<Employee> result_employees = payroll_search.searchByDepartment(search_department);
        result_employees.sort(Comparator.comparing(Employee::getSortName));
        List<Employee> employees_data = department_table.getItems();

        // UPDATE GUI DATA
        employees_data.clear();
        employees_data.addAll(result_employees);
    }

    // Stops application when Close MenuItem is pressed.
    @FXML
    public void stopApplication(ActionEvent e) {
        System.exit(0);
    }

    // Passes Stage from Main class.
    public void setStage(Stage s) {
        this.constructed_stage  = s;
    }

}
