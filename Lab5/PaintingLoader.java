import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *  A painting loader does not have attributes. A painting loader, creates a Painting object by parsing a given file object into a 
 *  individual line strings in an array, then each parsed line is split into individual characters which paints the given line,
 *  then returns the finished Painting object.
 *
 * @author Jordan Andersen
 * @version 1.0
 */
public class PaintingLoader {

    /**
     * This method processes a file specified by a given path:
     * If the file is valid (has a ".pnt" extension):
     *      Reads the file using a Scanner.
     *      Tracks then sets number of rows inside the file. (Assumed max is 25 lines)
     *      Sets the number of columns based on the columns of the first row. (Assumed to be the same throughout the file.)
     *      Creates a new painting with the rows and columns data.
     *      Generates the painting by converting each lines' characters into colors.
     *      Returns the resulting painting.
     * Otherwise, the path is invalid, the method returns null.
     * 
     * @param file_path filepath to painting file data.
     * @return returns a Painting object representing the data from the given file if the given file path exists, otherwise returns null.
     * @throws FileNotFoundException
     */
    public static Painting generate_painting(String file_path) throws FileNotFoundException{
        File painting_file = new File(file_path);
        if (painting_file.exists() && file_path.endsWith(".pnt")) {
            Scanner file_input = new Scanner(painting_file);
            String[] painting_lines = new String[25];
            int rows = 0;
            while (file_input.hasNext()) {
                painting_lines[rows] = file_input.nextLine();
                rows = rows + 1;
            }
            int columns = painting_lines[0].length();
            Painting generated_painting = new Painting(rows, columns);
            for (int row_index = 0; row_index < rows; row_index ++) {
                String[] line_characters = painting_lines[row_index].split("");
                for (int column_index = 0; column_index < columns; column_index ++) {
                    String color = char_to_color(line_characters[column_index]);
                    generated_painting.paint_cell(row_index, column_index, color);
                }
                
            }
            file_input.close();
            return generated_painting;
        } else {
            return null;
        }      
    }

     /**
     * Returns the appropriate color string for a given input character.
     * @param input_string the individual character from a split file line.
     * @return a string representing the color of the "pixel".
     */
    private static String char_to_color(String input_string) {
        if (input_string.equals("R")) {
            return "red";
        } else if (input_string.equals("Y")) {
            return "yellow";
        } else if (input_string.equals("G")) {
            return "green";
        } else if (input_string.equals("C")) {
            return "cyan";
        } else if (input_string.equals("B")) {
            return "blue";
        } else if (input_string.equals("P")) {
            return "purple";
        } else if (input_string.equals("X")) {
            return "black";
        } else if (input_string.equals("O")) {
            return "white";
        } else {
            return null;
        }
    }
}
