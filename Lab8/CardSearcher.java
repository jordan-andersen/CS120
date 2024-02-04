import java.util.*;

public class CardSearcher {
    // ATTRIBUTES:

    List<Card> cards_list;

    // METHODS:

    public CardSearcher( List<Card> given_list ) {
        this.cards_list = given_list;
    }

    public String searchByTitle( String search_string ) {
        String result_string = "";
        search_string = formatString(search_string);
        for ( Card card : this.cards_list ) {
            String card_title = formatString(card.getTitle());
            if ( card_title.contains(search_string) ) {
                result_string = "\n" + card + "\n";
            }
        }
        if ( !result_string.isEmpty() ) {
            return result_string;
        } else {
            return "No card with title '" + search_string.toUpperCase() + "' found!";
        }
    }

    public String searchByType( String search_string) {
        String result_string = "";
        search_string = formatString(search_string);
        for ( Card card : this.cards_list ) {
            String card_type = formatString(card.getType());
            String card_subtype = formatString(card.getSubType());
            System.out.println(card_type);
            
            if ( card_type.contains(search_string) || card_subtype.contains(search_string) ) {
                result_string = "\n" + card + "\n";
            }
        }
        if ( !result_string.isEmpty() ) {
            return result_string;
        } else {
            return "No card with type '" + search_string.toUpperCase() + "' found!";
        }
    }

    public String searchByBody( String search_string) {
        String result_string = "";
        search_string = formatString(search_string);
        for ( Card card : this.cards_list ) {
            String card_body = formatString(card.getBodyText());
            if ( card_body.contains(search_string) ) {
                result_string = "\n" + card + "\n";
            }
        }
        if ( !result_string.isEmpty() ) {
            return result_string;
        } else {
            return "No card has body text which contains '" + search_string.toUpperCase() + "'";
        }
    }

    private static String formatString( String input_string ) {
        return input_string.toLowerCase()                                                   // <-- Unifies case
                           .replaceAll("[^a-z]", "");                      // <-- Removes all non-alphabet characters
    }
}
