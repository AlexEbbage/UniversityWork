//ASSIGNMENT 2
//Modified by Alex Ebbage - 1504283.
//Last updated 15/3/17.

package assignment2;

import java.io.*;

class ParseException extends RuntimeException
{ public ParseException(String s)
  { super("Invalid expression: "+s);
  }
}

public class Parser
{ private Lexer lex;

  public Parser()
  { lex = new Lexer();
  }

  public ExpTree parseLine()
  { lex.init();
    lex.getToken();
    ExpTree result = parseExp(true);
    if (lex.token==Lexer.where)
    { lex.getToken();
      ExpTree defs = parseDefList();
      result = makeWhereTree(result, defs);
    }
    if (lex.token!=Lexer.semico)
    { throw new ParseException("semicolon expected");
    }
    return result;
  }

  public String getLine()
  { return lex.getLine();
  }

  private ExpTree parseExp(boolean idsAllowed)
  { ExpTree result = parseTerm(idsAllowed);
    { while (lex.token==Lexer.plus || lex.token==Lexer.minus)
      { int op = lex.token;
        lex.getToken();
        if (op==Lexer.plus)
          result = makePlusTree(result, parseTerm(idsAllowed));
        else
          result = makeMinusTree(result, parseTerm(idsAllowed));
	  }
    }
    return result;
  }

  private ExpTree parseTerm(boolean idsAllowed)
  { ExpTree result = parseOpd(idsAllowed);
    { while (lex.token==Lexer.times || lex.token==Lexer.div || lex.token==Lexer.mod)
      { int op = lex.token;
        lex.getToken();
        if (op==Lexer.times)
          result = makeTimesTree(result, parseOpd(idsAllowed));
        else if (op==Lexer.mod)
          result = makeModTree(result, parseOpd(idsAllowed));
        else
          result = makeDivideTree(result, parseOpd(idsAllowed));
	  }
    }
    return result;
  }

  private ExpTree parseOpd(boolean idsAllowed)
  { ExpTree result;
    switch(lex.token)
    { case Lexer.num:
        result = makeNumberLeaf(lex.numval);
        lex.getToken();
        return result;
      case Lexer.id:
        if (!idsAllowed)
          throw new ParseException("identifier not allowed in identifier defintion");
        result = makeIdLeaf(lex.idval);
        lex.getToken();
        return result;
      case Lexer.lp:
        lex.getToken();
        result = parseExp(idsAllowed);
        if (lex.token!=Lexer.rp)
          throw new ParseException("right parenthesis expected");
        lex.getToken();
        return result;
      default:
        throw new ParseException("invalid operand");
    }
  }

  private ExpTree parseDefList()
  { ExpTree result = parseDef();
	while (lex.token==Lexer.and)
    { lex.getToken();
      result = makeAndTree(result, parseDef());
    }
	return result;
  }

  private ExpTree parseDef()
  { if (lex.token!=Lexer.id)
      throw new ParseException("definition must start with identifier");
    char id = lex.idval;
    if (Character.isUpperCase(id))
      throw new ParseException("upper-case identifiers cannot be used in defintion list");
    lex.getToken();
    if (lex.token!=Lexer.eq)
      throw new ParseException("'=' expected");
    lex.getToken();
    return makeDefTree(id, parseExp(false));
  }

//===========================================================
//Methods for assignment.
  
  static ExpTree makeNumberLeaf(int n){ 
	  return new NumberLeaf(n);
  }


  static ExpTree makeIdLeaf(char c){ 
	  return new IdentityLeaf(c);
  }

  
  static ExpTree makePlusTree(ExpTree l, ExpTree r){ 
	  return new OperatorNode('+', l, r);
  }
  

  static ExpTree makeMinusTree(ExpTree l, ExpTree r){ 
	  return new OperatorNode('-', l, r);
  }
  

  static ExpTree makeTimesTree(ExpTree l, ExpTree r){ 
	  return new OperatorNode('*', l, r);
  }
  

  static ExpTree makeDivideTree(ExpTree l, ExpTree r){ 
	  return new OperatorNode('/', l, r);
  }
  

  static ExpTree makeModTree(ExpTree l, ExpTree r){ 
	  return new OperatorNode('%', l, r);
  }

  
  static ExpTree makeWhereTree(ExpTree l, ExpTree r){ 
	  return new WhereTree(l, r);
  }

  
  static ExpTree makeAndTree(ExpTree l, ExpTree r){ 
	  return new AndNode(l, r);
  }

  
  static ExpTree makeDefTree(char c, ExpTree t){ 
	  return new EqualsNode(c, t);
  }
  
//===========================================================
}

class Lexer
{ static final int err = 0, num = 1, id = 2, plus = 3, minus = 4, times = 5, div = 6, mod = 7,
                   lp = 8, rp = 9, semico = 10, where = 11, and = 12, eq = 13;
  int token;
  char idval;
  int numval;
  private String line = "";
  private BufferedReader buf;

  Lexer()
  { buf = new BufferedReader(new InputStreamReader(System.in));
  }

  void init()
  { do
      try
      { line = buf.readLine().trim();
      }
      catch(Exception e)
      { System.out.println("Error in input");
        System.exit(1);
	  }
    while (line.length()==0);
  }

  String getLine()
  { init();
    return(line);
  }

  void getToken()
  { if (line.length()==0)
      token = err;
    else switch (line.charAt(0))
    { case '+':
        token = plus;
        line = line.substring(1).trim();
        break;
      case '-':
        token = minus;
        line = line.substring(1).trim();
        break;
      case '*':
        token = times;
        line = line.substring(1).trim();
        break;
      case '/':
        token = div;
        line = line.substring(1).trim();
        break;
      case '%':
        token = mod;
        line = line.substring(1).trim();
        break;
      case '(':
        token = lp;
        line = line.substring(1).trim();
        break;
      case ')':
        token = rp;
        line = line.substring(1).trim();
        break;
      case ';':
        token = semico;
        line = line.substring(1).trim();
        break;
      case '=':
        token = eq;
        line = line.substring(1).trim();
        break;
      default:
        if (Character.isDigit(line.charAt(0)))
        { token = num;
          numval = line.charAt(0) - '0';
          int i = 1;
          while (i<line.length()&&Character.isDigit(line.charAt(i)))
          { numval = numval*10+line.charAt(i)-'0';
            i++;
		  }
          line = line.substring(i).trim();
		}
		else if (Character.isLowerCase(line.charAt(0)))
		{ char c = line.charAt(0);
		  if (c=='w' && line.length()>=5 && line.charAt(1)=='h' && line.charAt(2)=='e' && line.charAt(3)=='r' &&
		      line.charAt(4)=='e')
		  { token = where;
            line = line.substring(5).trim();
	      }
	      else if (c=='a' && line.length()>=3 && line.charAt(1)=='n' && line.charAt(2)=='d')
		  { token = and;
            line = line.substring(3).trim();
	      }
	      else if (line.length()>1 && Character.isLetter(line.charAt(1)))
	      { token = err;
	      }
		  else
		  { token = id;
		    idval = c;
            line = line.substring(1).trim();
	      }
	    }
		else
		  token = err;
	}
  }
}
