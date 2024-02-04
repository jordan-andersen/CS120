import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GradeReader {

    // ATTRIBUTES

    private File transcript_file;
    private Transcript[] transcripts;
    private int transcript_index;

    // METHODS

    public GradeReader (File input_file) {
        this.transcript_file = input_file;
        this.transcripts = new Transcript[10];
        this.transcript_index = 0;
    }

    public void loadFile () throws FileNotFoundException {
        // LOADS STUDENT TRANSCRIPT FILE AND CREATES TRANSCRIPT OBJECTS FOR EACH INDIVIDUAL COURSE CONTAINED WITHIN THE FILE.
        Scanner transcript_reader = new Scanner(this.transcript_file);
        String current_line = transcript_reader.nextLine();
        while ( transcript_reader.hasNext() ) { // READS EACH LINE OF FILE UNTIL NO LINES REMAIN.
            if ( current_line.split(",")[1].split(" ").length == 2 ) { // ENSURE FIRST LINE IS CORRECT INFO LINE (CHECKS FOR NAME ENTRY).
                String[] transcript_info = current_line.split(",");
                this.transcripts[this.transcript_index] = new Transcript(transcript_info);
                // RECORDS ASSIGNMENTS FROM FILE
                current_line = transcript_reader.nextLine();
                while ( current_line != null && current_line.split(",")[1].split(" ").length < 2 ) { // LOOPS WHILE NEW LINE IS AN ASSIGNMENT.
                    String[] assignment_details = current_line.split(",");
                    this.transcripts[this.transcript_index].recordAssignment(assignment_details);
                    if ( transcript_reader.hasNextLine() ) { current_line = transcript_reader.nextLine(); }
                    else { current_line = null; }
                }
                this.transcript_index = this.transcript_index + 1;
            }
        }
        transcript_reader.close();
    }

    public String toString() {
        String transcripts_string = "";
        for (Transcript transcript : this.transcripts) {
            if ( transcript != null ) { transcripts_string = transcripts_string + transcript.toString(); }
        }
        return transcripts_string;
    }
}
