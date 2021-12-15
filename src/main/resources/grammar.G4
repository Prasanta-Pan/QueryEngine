expr :	'(' expr ')'
     |	operand
     |	operand operator
     ;
operator 
	: ('+' | '-')
	| ('*' | '/' | '%')
	| ('>' | '>=' | '<' | '<=')
	
	
	