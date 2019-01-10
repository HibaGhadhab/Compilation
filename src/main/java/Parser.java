import java.util.List;
import java.util.Stack;

public class Parser
{
    public static int parse(List<Token> tokensFromLexer)
    {
        // On récupère les tokens du lexeurs
        // On les mets dans un tableau de caractères
        String  chaine = "";
        for (Token token: tokensFromLexer)
        {
            String symbole = token.getSymbole();
            chaine = chaine + " " +symbole;
        }
        char[] tokens = chaine.toCharArray();

        // pile des nombres: 'values'
        Stack<Integer> values = new Stack<Integer>();

        // pile des operateurs: 'ops'
        Stack<Character> ops = new Stack<Character>();

        for (int i = 0; i < tokens.length; i++) //Parcours du tableau de caractères
        {
            // le token courant espace à ignorer
            if (tokens[i] == ' ')
                continue;

            //le token courant s'agit d'un nombre, ajouter le dans la pile des nombres
            if (tokens[i] >= '0' && tokens[i] <= '9')
            {
                StringBuffer sbuf = new StringBuffer();
                // si le nombre contient plus qu'un chiffre
                // tant que i est un nombre, il s'arrête lorsqu'il trouve un operateur qu'il ne peut pas comparer avec 0 ou 9
                while (i < tokens.length && tokens[i] >= '0' && tokens[i] <= '9')
                    sbuf.append(tokens[i++]);
                values.push(Integer.parseInt(sbuf.toString())); //ajout dans la pile des nombres
            }

            // token courant est une parenthèse ouvrante, ajouter à la pile des opérateurs
            else if (tokens[i] == '(')
                ops.push(tokens[i]);

            // token courant est une parenthèse fermante
            // si pas de parenthèse ouvrante, calculer avec deux pop de Values et l'opérateur pop ops
            // pusher le résultat dans Values
            else if (tokens[i] == ')')
            {
                while (ops.peek() != '(')
                    values.push(calculer(ops.pop(), values.pop(), values.pop()));
                ops.pop();
            }

            // token courant est un opérateur
            else if (tokens[i] == '+' || tokens[i] == '-' ||
                    tokens[i] == '*' || tokens[i] == '/')
            {
                // tantque la tete de la pile est plus prioritaire (ou meme priorité)
                // que le token courant qui est un opérateur
                // calculer avec deux pop de la pile Values et l'opérateur pop ops
                // pusher le résultat dans la pile Values
                while (!ops.empty() && hasPrecedence(tokens[i], ops.peek()))
                    values.push(calculer(ops.pop(), values.pop(), values.pop()));

                // Ajouter le token courant qui est un opérateur à la pile des opérateurs
                ops.push(tokens[i]);
            }
        }

        // tout le tableau token a été parcouru
        // faire les opérations restantes en popant les valeurs et l'opérateur et en pushant le résultat du calcul
        while (!ops.empty())
            values.push(calculer(ops.pop(), values.pop(), values.pop()));

        // Retourner la tete de la pile Values qui contient le résultat final
        return values.pop();
    }

    // méthode pour trouver la priorité
    //retourne true si op2 est plus prioritaire (ou meme priorité) que op1
    public static boolean hasPrecedence(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }

    //méthode calculer; selon l'opérateur
    public static int calculer(char op, int b, int a)
    {
        switch (op)
        {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0)
                    throw new
                            UnsupportedOperationException("Division par zéro impossible");
                return a / b;
        }
        return 0;
    }


}