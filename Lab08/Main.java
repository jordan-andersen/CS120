import net.sourceforge.tess4j.*;
import java.awt.Rectangle;
import java.util.*;
import java.io.*; 

public class Main {
    public static void main(String[] args) throws TesseractException, IOException{
        // MAIN PROGRAM VARIABLES
        List<Card> card_list = new ArrayList<>();

        // INSTANTIATE AND INITIALIZE TITLE OCR
        Tesseract title_reader = new Tesseract(); 
        title_reader.setDatapath("resources/trained_data/"); 
        title_reader.setLanguage("title"); 

        // INSTANTIATE AND INITIALIZE BODY OCR
        Tesseract body_reader = new Tesseract();  
        body_reader.setDatapath("resources/trained_data/"); 
        body_reader.setLanguage("body"); 

        // DEFINE NECESSARY PARAMETERS FOR OCR PROCESS
        File card_images_directory = new File("resources/cards");
        File[] directory_files= card_images_directory.listFiles();
        new File("resources/out").mkdirs();
        Rectangle title_box = new Rectangle(55, 55, 400, 35); 
        Rectangle type_box = new Rectangle(55, 537, 400, 35);         
        Rectangle body_box = new Rectangle(50, 600, 565, 260);


        // PROCESS IMAGE FILES
        if ( card_images_directory.exists() && directory_files != null ) {
            System.out.println("Processing cards...");

            for (File child_file : directory_files ) {
                String file_name = child_file.getName();
                String title_result = title_reader.doOCR(child_file, title_box);  
                String type_result = title_reader.doOCR(child_file, type_box);
                String body_result = body_reader.doOCR(child_file, body_box);
                Card new_card = Card.parse(title_result, type_result, body_result);
                if ( new_card != null ) {
                    card_list.add(new_card);
                    String card_file_name = "resources/out/" + file_name.strip().replaceAll(".jpg","") + ".txt";
                    File card_file = new File(card_file_name);
                    FileWriter card_wrapper = new FileWriter(card_file, false);
                    PrintWriter card_writer = new PrintWriter(card_wrapper);
                    card_writer.println(new_card);
                    card_writer.close();
                    System.out.println("Processed: " + file_name);
                } else {
                    System.out.println("Failed to process: " + file_name);
                }
            }
        }

        // MENU SYSTEM
        CardSearcher card_search = new CardSearcher(card_list);
        Menu.mainMenu(card_search);
        System.out.println("Quitting...");
    }  
}