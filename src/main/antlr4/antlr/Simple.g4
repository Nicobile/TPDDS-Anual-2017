grammar Simple;

calc: expr EOF;

expr
:BR_OPEN expr BR_CLOSE
|expr TIMES expr
|expr DIV expr 
|expr PLUS expr
|expr MINUS expr
|number
|indicador
|cuenta
; 
indicador:INDICADOR;
cuenta:CUENTA;
number: NUMBER;

PLUS:   'plus'  | '+';
MINUS:  'minus' | '-';
TIMES:  'times' | '*';
DIV:    'div'   | '/';

INDICADOR:[i_][a-zA-Z_]*;
CUENTA:[c_][a-zA-Z_]*;
NUMBER: '-'? [0-9]+;
BR_OPEN: '(';
BR_CLOSE: ')';

WS: [ \t\r\n]+ -> skip;
