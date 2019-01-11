import java.util.List;
import java.util.Stack;

public class Parser
{
    public StringBuilder sb = new StringBuilder();

    public int parse(List<Token> tokensFromLexer)
    {
        // On récupère les tokens du lexeurs
        // On les mets dans un tableau de caractères
        String  chaine = "";
        for (Token token: tokensFromLexer)
        {
            String symbole = token.getSymbole();
            chaine = chaine +" "+ symbole;
        }
        char[] tokens = chaine.toCharArray();

        // pile des nombres: 'numberPile'
        Stack<Integer> numberPile = new Stack<Integer>();

        // pile des operateurs: 'operatorPile'
        Stack<Character> operatorPile = new Stack<Character>();

        //si premier caractère est un opérateur
        if (tokens[1] == '+' || tokens[1] == '-' ||
                tokens[1] == '*' || tokens[1] == '/')
        {
            throw new
                    UnsupportedOperationException("Une opération ne commence pas par un opérateur !");
        }
        else
        {

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
                    numberPile.push(Integer.parseInt(sbuf.toString())); //ajout dans la pile des nombres
                }

                // token courant est une parenthèse ouvrante, ajouter à la pile des opérateurs
                else if (tokens[i] == '(')
                    operatorPile.push(tokens[i]);

                    // token courant est une parenthèse fermante
                    // si pas de parenthèse ouvrante, calculer avec deux pop de numberPile et l'opérateur pop operatorPile
                    // pusher le résultat dans numberPile
                else if (tokens[i] == ')')
                {
                    while (operatorPile.peek() != '(')
                        numberPile.push(calculer(operatorPile.pop(), numberPile.pop(), numberPile.pop()));

                    operatorPile.pop();
                }

                // token courant est un opérateur
                else if (tokens[i] == '+' || tokens[i] == '-' ||
                        tokens[i] == '*' || tokens[i] == '/')
                {
                    // tantque la tete de la pile est plus prioritaire (ou meme priorité)
                    // que le token courant qui est un opérateur
                    // calculer avec deux pop de la pile numberPile et l'opérateur pop operatorPile
                    // pusher le résultat dans la pile numberPile
                    while (!operatorPile.empty() && plusPrioritaire(tokens[i], operatorPile.peek()))
                        numberPile.push(calculer(operatorPile.pop(), numberPile.pop(), numberPile.pop()));

                    // Ajouter le token courant qui est un opérateur à la pile des opérateurs
                    operatorPile.push(tokens[i]);
                }
            }

        }


        // tout le tableau token a été parcouru
        // faire les opérations restantes en popant les valeurs et l'opérateur et en pushant le résultat du calcul
        while (!operatorPile.empty())
            numberPile.push(calculer(operatorPile.pop(), numberPile.pop(), numberPile.pop()));

        // Retourner la tete de la pile numberPile qui contient le résultat final
        return numberPile.pop();
    }

    // méthode pour trouver la priorité
    //retourne true si op2 est plus prioritaire (ou meme priorité) que op1
    public static boolean plusPrioritaire(char op1, char op2)
    {
        if (op2 == '(' || op2 == ')')
            return false;
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-'))
            return false;
        else
            return true;
    }



    //méthode calculer; selon l'opérateur
    public  int calculer(char op, int b, int a)
    {
        sb.append(a);
        sb.append(b);
        sb.append(op);

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
                            UnsupportedOperationException("Division par zéro");
                return a / b;
        }

        return 0;
    }



}