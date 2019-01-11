//Classe Lexer

import java.util.ArrayList;
import java.util.List;

public class Lexer {

    private final String code;
    private final List<Token> tokens;
    private int position;

    public Lexer(String code)
    {
        this.code = code;
        this.tokens = new ArrayList<>();
        this.position = 0;
    }

    public List<Token> getTokens()
    {
        return tokens;
    }


    public String getCurrentChar()
    {
        return String.valueOf(this.code.charAt(this.position));
    }

    public void addToken(String symbole, Token.Type type)
    {
        tokens.add(new Token(symbole, type));
    }

    public void next()
    {
        if (!hasNext()) throw new RuntimeException("end");
        this.position++;
    }

    public boolean hasNext()
    {
        return this.position<code.length();
    }

    public boolean parse()
    {
        try
        {
            parseExprList();
        } finally
        {
            return true;
        }
    }

    public boolean parseOperator()
    {
        if ("/*-+%".contains(getCurrentChar()))
        {
            addToken(getCurrentChar(), Token.Type.OPERATOR);
            next();
        }
        else {
            return false;
        }
        return true;
    }

    public boolean parseSymbole()
    {
        if ("()".contains(getCurrentChar()))
        {
            addToken(getCurrentChar(), Token.Type.SYMBOLE);
            next();
        }
        else {
            return false;
        }
        return true;
    }


    public boolean parseNull()
    {
        if (" \n".contains(getCurrentChar()))
        {
            next();
        }
        else {
            return false;
        }
        return true;
    }


    public boolean parseNumber()
    {
        if (!"0123456789".contains(getCurrentChar())) return false;
        String value = "";
        while (hasNext() &&"0123456789".contains(getCurrentChar()))
        {
            value += getCurrentChar();
            next();
        }
        addToken(value, Token.Type.NUMBER);
        return true;
    }

    public boolean parseExpression()
    {
        if (parseSymbole() | parseNumber() | parseOperator()) {
            return true;
        }
        return false;
    }

    public  boolean parseExprList()
    {
        if (!parseExpression()) return false;

        while (parseNull());

        while(parseExpression())
        {
            while (parseNull());
        }
        return true;
    }
}
