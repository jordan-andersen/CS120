import java.io.*; 

public class ErrorWriter {

    private static File error_file = new File("resources/error.log");
    private static int failed_employee_instantiations = 0;
    private static int failed_position_instantiations = 0;
    private static int failed_department_instantiations = 0;

    public static void initializeErrorFile() {
        try{
            FileWriter wrapper = new FileWriter(error_file, false);
            wrapper.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static void writeDepartmentError( String error_message ) {
        failed_department_instantiations = failed_department_instantiations + 1;
        writeError(error_message);
    }

    public static void writeEmployeeError( String error_message ) {
        failed_employee_instantiations = failed_employee_instantiations + 1;
        writeError(error_message);
    }

    public static void writePositionError( String error_message ) {
        failed_position_instantiations = failed_position_instantiations + 1;
        writeError(error_message);
    }

    public static void writeError( String error_message ) {
        try {
            FileWriter wrapper = new FileWriter(error_file, true);
            PrintWriter error_writer = new PrintWriter(wrapper);
            error_writer.println(error_message);
            error_writer.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static String getErrorReport() {
        String error_report = ("\nFailed DEPARTMENT instantiations: " + failed_department_instantiations + "\n" +
                                "Failed EMPLOYEE instantiations: " + failed_employee_instantiations + "\n" +
                                "Failed POSITION instantiations: " + failed_position_instantiations + "\n" +
                                "See \'./resources/error.log\' for details.\n");
        return error_report;
    }
    
}
