public class Card {

    // ATTRIBUTES:
    
    private String title;
    private String type;
    private String subtype;
    private String body_text;

    // METHODS:

    public Card( String given_title, String given_type, String given_subtype, String given_body_text ) {
        this.title = given_title;
        this.type = given_type;
        this.subtype = given_subtype;
        this.body_text = given_body_text;
    }

    public static Card parse( String card_title, String card_type, String card_body ) {
        Card new_card = null;
        boolean entries_not_null = card_title != null && !card_title.isEmpty()
                                   && card_type != null && !card_type.isEmpty()
                                   && card_body != null && !card_body.isEmpty(); 
 
        if ( entries_not_null ) {
            String[] split_type = card_type.split("-");
            String parsed_title = card_title.strip();
            String parsed_body = card_body.strip().replaceAll("\n\n", "\n");
            String parsed_type = card_type.strip();
            String parsed_subtype = "NONE";
            if ( split_type.length == 2 ) {
                parsed_type = split_type[0].strip();
                parsed_subtype = split_type[1].strip();
            }

            new_card = new Card(parsed_title, parsed_type, parsed_subtype, parsed_body);
        } 

        return new_card;
    }

    public String getTitle(){
        return this.title;
    }

    public String getType(){
        return this.type;
    }

    public String getSubType(){
        return this.subtype;
    }

    public String getBodyText(){
        return this.body_text;
    }

    public String toString(){
        return (this.title + "\n"
                + "--------------------------------------------------" + "\n"
                + this.type + " - " + this.subtype + "\n"
                + "--------------------------------------------------" + "\n"
                + this.body_text);
    }
    
    
    
}
