
import java.util.List;

public class Main
{
    public static String CODE = "7 + 3 * 2 * ( 2 + 3 )";
    public static int position = 0;
    public static List<Token> tokens;

    public static void main (String args[]) {
        System.out.println("\n ************* Lexer *************");
        Lexer lexer = new Lexer(CODE);
        //Lexer lexer = new Lexer(CODE, position, tokens);
        lexer.parse();
        lexer.getTokens().forEach(System.out::println);

        System.out.println("\n ************* Parser *************");
        System.out.println(Parser.parse(lexer.getTokens()));

//        System.out.println("\n\nOther tests");
//        "10 + 2 * 6";
//        "100 * 2 + 12";
//        "100 * ( 2 + 12 )";
//        "100 * ( 2 + 12 ) / 14";

    }

}