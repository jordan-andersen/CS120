import java.util.*; 

public class Menu {
    private static final Scanner user_input = new Scanner(System.in);
    private static final String user_prompt = "Input selection: ";

    public static void mainMenu( CardSearcher card_search ){
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.println("""

                    ========= MAIN MENU =========
                    1. Search cards by TITLE
                    2. Search cards by TYPE or SUBTYPE
                    3. Search cards by BODY TEXT
                    """);
            System.out.print(user_prompt);
            String user_selection = user_input.nextLine().trim();
            boolean safe_input = !user_selection.isEmpty();
            if ( safe_input ) {
                end_loop = checkInput(user_selection);
                switch (user_selection) {
                    case "1" -> subMenu1(card_search);
                    case "2" -> subMenu2(card_search);
                    case "3" -> subMenu3(card_search);
                    default ->
                            System.out.println("Invalid selection! Enter only 1 - 3 or 'quit' to end program.\n\n");
                }
            }
        }
    }

    public static void subMenu1( CardSearcher card_search ) {
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.println("""

                    ========= Search cards by TITLE =========
                    Input 'card-title' or 'back' to return to main menu

                    """);
            System.out.print(user_prompt);
            String user_selection = user_input.nextLine().trim();
            boolean safe_input = !user_selection.isEmpty();
            if ( safe_input ) {
                end_loop = checkInput(user_selection);
                if ( !end_loop ) {
                    String search_results = card_search.searchByTitle(user_selection);
                    System.out.println(search_results);
                }
            }
        }
    }

    public static void subMenu2( CardSearcher card_search ) {
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.println("""

                    ========= Search cards by TYPE or SUBTYPE =========
                    Input 'card-type OR card-subtype' or 'back' to return to main menu

                    """);
            System.out.print(user_prompt);
            String user_selection = user_input.nextLine().trim();
            boolean safe_input = !user_selection.isEmpty();
            if ( safe_input ) {
                end_loop = checkInput(user_selection);
                if ( !end_loop ) {
                    String search_results = card_search.searchByType(user_selection);
                    System.out.println(search_results);
                }
            }
        }
    }

    public static void subMenu3( CardSearcher card_search ) {
        boolean end_loop = false;
        while ( !end_loop ) {
            System.out.println("""

                    ========= Search cards by BODY TEXT =========
                    Input search text or 'back' to return to main menu

                    """);
            System.out.print(user_prompt);
            String user_selection = user_input.nextLine().trim();
            boolean safe_input = !user_selection.isEmpty();
            if ( safe_input ) {
                end_loop = checkInput(user_selection);
                if ( !end_loop ) {
                    String search_results = card_search.searchByBody(user_selection);
                    System.out.println(search_results);
                }
            }
        }
    }

    private static boolean checkInput(String inputString) {
        return inputString.equalsIgnoreCase("back") || inputString.equalsIgnoreCase("quit");
    }
}
