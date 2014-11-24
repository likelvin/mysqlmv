grammar mv;

@header {
import java.util.HashMap;
}

@members {
/** Map variable name to Integer object holding value */
HashMap memory = new HashMap();
}

prog:   stat+ ;
                
stat:   expr NEWLINE {System.out.println($expr.value);}
    |   ID '=' expr NEWLINE
        {memory.put($ID.text, new Integer($expr.value));}
    |   NEWLINE
    ;

expr returns [int value]
    :   e=multExpr {$value = $e.value;}
        (   '+' e=multExpr {$value += $e.value;}
        |   '-' e=multExpr {$value -= $e.value;}
        )*
    ;

multExpr returns [int value]
    :   e=atom {$value = $e.value;} ('*' e=atom {$value *= $e.value;})*
    ; 

atom returns [int value]
    :   INT {$value = Integer.parseInt($INT.text);}
    |   ID
        {
        Integer v = (Integer)memory.get($ID.text);
        if ( v!=null ) $value = v.intValue();
        else System.err.println("undefined variable "+$ID.text);
        }
    |   '(' expr ')' {$value = $expr.value;}
    ;
SELECT_EXPR 
	:	NEWLINE+ G_ID NEWLINE+(',' NEWLINE+ G_ID NEWLINE+)+ ;
/** identifier */
ID  :   ('0'..'9'|'a'..'z'|'A'..'Z'|'$'|'_')+ ;
/** quoted identifier */
Q_ID	:	'`'ID'`';
/** General identifier */
G_ID	:	ID|Q_ID;
IS_DISTINCT
	:	'ALL' | 'DISTINCT' | 'DISTINCTROW';
HIGH_PRIORITY
	:	'HIGH_PRIORITY';
SQL_SMALL_RESULT
	:	'SQL_SMALL_RESULT';

INT :   '0'..'9'+ ;
NEWLINE:'\r'? '\n' ;
WS  :   (' '|'\t')+ {skip();} ;
SQL_BIG_RESULT
	:	'SQL_BIG_RESULT';
SQL_BUFFER_RESULT
	:	'SQL_BUFFER_RESULT';
HAS_CACHE
	:	'SQL_CACHE' | 'SQL_NO_CACHE';