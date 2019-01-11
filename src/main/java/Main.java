
import java.util.List;

public class Main
{
    //public static String CODE = "7 + 3 * 2 * ( 2 + 3 )";
    public static String CODE = "2+5*3";

    public static int position = 0;

    public static void main (String args[]) {
        System.out.println("\n ************* Lexer *************");
        Lexer lexer = new Lexer(CODE);
        lexer.parse();
        lexer.getTokens().forEach(System.out::println);

        System.out.println("\n ************* Parser Result *************");
        Parser parser = new Parser();
        System.out.println(parser.parse(lexer.getTokens()));
        System.out.println(parser.sb);



//        System.out.println("\n\nOther tests");
//        "10 + 2 * 6";
//        "100 * 2 + 12";
//        "100 * ( 2 + 12 )";
//        "100 * ( 2 + 12 ) / 14";

    }

}