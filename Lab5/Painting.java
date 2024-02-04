
/**
 *  A painting hold data about an ASCII painting. A painting has three attributes: 
 *  1. the height of the painting (as an integer)
 *  2. the width of the painting (as an integer)
 *  3. the "pixels" of the painting (as a 2D array of PaintingCell objects)
 *
 * @author Jordan Andersen
 * @version 1.0
 */
public class Painting {
    /**
     * This attribute stores the height of the painting: # of rows of character lines. Set only by the Constructor method.
     */
    private int height;
    /**
     * This attribute stores the width of the painting: # of columns of character spaces. Set only by the Constructor method.
     */
    private int width;
    /**
     * This attribute stores the individual "pixels" of the painting: a 2D array of PaintingCell objects. Set only by Constructor, paint_cell, and erase_cell methods.
     */
    private PaintingCell[][] painting_cells;

    /**
     *  Constructor to create new Painting.
     *
     *  Example Usage:
     *  <pre>
     *      int rows = 10
     *      int columns = 10
     *      Painting new_painting = new Painting(rows, columns);
     *  </pre>
     *
     *  This snippet will create a new empty Painting that is 10 by 10 in size.
     *
     */
    public Painting(int given_height, int given_width) {
        this.height = given_height;
        this.width = given_width;
        this.painting_cells = new PaintingCell[given_height][given_width];

        for (int row = 0; row < this.height; row++) {
            for (int column = 0; column < this.width; column++) {
                painting_cells[row][column] = new PaintingCell();
            }
        }
    }

    /**
     * This private method is used to verify that desired cell to change will be within the 2D array painting_cells.
     * 
     * @param row y coordinate of the cell.
     * @param column x coordinate of the cell.
     * @return boolean if the passed coordinates are within bounds.
     */
    private boolean check_boundaries(int row, int column) {
        if ((0 <= row && row < this.height) && (0 <= column && column < this.width)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used to set the color attribute and the visibility attribute of a parameter cell (row, column) to the passed color parameter and to true, respectively.
     * 
     * Example Usage:
     * <pre>
     * 
     * for (int y = 0; y < rows; y++) {
     *      for (int x = 0; x < columns; x++) {
     *           new_painting.paint_cell(y, x, "blue");
     *      }
     * }
     * 
     * </pre>
     * 
     * This snippets will set all "pixels" in the painting to blue. 
     * 
     * @param row
     * @param column
     * @param color
     * @return
     */
    public boolean paint_cell(int row, int column, String color) {
        boolean cell_is_valid = check_boundaries(row, column);
        if (cell_is_valid){
            this.painting_cells[row][column].changeColor(color);
            this.painting_cells[row][column].setVisibility(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * This method is used to set the visibility attribute of a parameter cell (row, column).
     * 
     * Example Usage:
     * <pre>
     * 
     * for (int y = 0; y < rows; y++) {
     *      for (int x = 0; x < columns; x++) {
     *           new_painting.paint_cell(y, x, "black");
     *      }
     * }
     * 
     * </pre>
     * 
     * This snippets will "erase" all "pixels" in the painting. 
     * 
     * @param row
     * @param column
     * @return a value of true when the 
     */
    public boolean erase_cell(int row, int column) {
        boolean cell_is_valid = check_boundaries(row, column);
        if (cell_is_valid){
            this.painting_cells[row][column].setVisibility(false);
            return true;
        } else {
            return false;
        }
    }

    
    @Override
    /**
     * This method overrides the default behavior when the object is coerced into a string.
     * 
     * @return a string representation of the Painting object.
     */
    public String toString() {
        String painting_string = "\n";
        for (int row = 0; row < this.height; row++) {
            for (int column = 0; column < this.width; column++) {
                painting_string += this.painting_cells[row][column].toString();
            }
            painting_string += "\n";
        }
        return painting_string;
    }
}
