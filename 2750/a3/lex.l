%{
#include "y.tab.h"
%}

%%

"title"			return TITLE;
"fields"		return FIELDS;
"buttons"		return BUTTONS;
=				return EQUAL;
[_a-zA-Z0-9]+	return STRING;;
["]				return QUOTE;
[,]				return COMMA;
[{]				return LISTL;
[}]				return LISTR;	
[;]				return SEMI;
#.*\n			{}
[\n]			{}
[ \t]+			{}
[\0]			{}
.				printf("Character not expected\n");

%%

int yywrap (void) {return 1;}