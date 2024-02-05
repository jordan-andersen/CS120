import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This Main class, displays usage info then continuously prompts the user for a path to a painting file until a valid path is provided.
 * Using the PaintingLoader class, it generates a Painting object from the provided painting file and stores it, or stores "null" if the path is invalid.
 * Once a valid painting has been loaded and generated, it converts the painting to a string and prints it out.
 * It then asks if the user would like to continue loading painting files. If yes, it repeats the above actions. If no, it ends the program.
 * Otherwise, the user will be informed to input a valid input.
 * 
 * @author Jordan Andersen
 * @version 1.0
 */
public class Main {

    public static void main(String[] args) throws FileNotFoundException {
        System.out.print(	
            "\n==================================== USAGE ====================================\n" +
            "Ensure the painting (.pnt) files are in the ./resources directory.\n" +
            "Input \'[insert_file_name].pnt\' to load and display the desired painting file.\n" +
            "After the painting is loaded and printed, you may choose to continue displaying\n" +
            "paintings or exit this program.\n" +
            "===============================================================================\n\n");

        Scanner user_input = new Scanner(System.in);
        boolean running = true;
        while (running) {
            boolean valid_input_1 = false;
            while (!valid_input_1) {
                System.out.print("Input filepath of painting file: resources/");
                String file_path = "resources/" + user_input.nextLine().trim();
                Painting new_painting = PaintingLoader.generate_painting(file_path);
                if (new_painting != null) {
                    System.out.println(new_painting.toString());
                    valid_input_1 = true;
                } else {
                    System.out.println("Invalid Filepath! \n");
                }
            }
            boolean valid_input_2 = false;
            while (!valid_input_2) {
                System.out.print("Continue? ");
                String user_continue = user_input.nextLine().trim();
                if (user_continue.toLowerCase().equals("no")) {
                    System.out.println("Exiting... \n");
                    running = false;
                    valid_input_2 = true;
                } else if (user_continue.toLowerCase().equals("yes")) {
                    System.out.println("Continuing... \n");
                    valid_input_2 = true;
                } else {
                    System.out.println("Invalid input. Please input either \"yes\" or \"no\".\n");
                }
            }
        }
        user_input.close();
    }
}
