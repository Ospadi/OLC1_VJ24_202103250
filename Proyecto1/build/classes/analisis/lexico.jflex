package analisis;

import java_cup.runtime.Symbol;
import java.util.LinkedList;
import excepciones.Errores;

%%

%{
    String cadena = "";
    public LinkedList<Errores> listaErrores = new LinkedList<>();
%}

%init{
    yyline = 1;
    yycolumn = 1;
    listaErrores = new LinkedList<>();
%init}

%cup
%class scanner
%public
%line
%column
%char
%full
//%debug
%ignorecase
%state CADENA

// definir los simbolos del sistema
PARIZQ = "("
PARDER = ")"
LLAVIZQ = "{"
LLAVDER = "}"
CORIZQ = "["
CORDER = "]"
PTCOMA = ";"
COMA = ","
PUNTO = "."
DOSPT = ":"
INCRE = "++"
DECRE = "--"
MAS = "+"
MENOS = "-"
MULT = "*"
DIV = "/"
POTENCIA = "**"
MODULO = "%"
IGUAL = "="
IGUALA = "=="
DIFER = "!="
MENOR = "<"
MENORQUE = "<="
MAYOR = ">"
MAYORQUE = ">="
OR = "||"
AND = "&&"
XOR = "^"
NOT = "!"
FLECHA = "=>"
DEFAULT = "_"

BLANCOS = [\ \r\t\n\f]+
ENTERO = [0-9]+
DECIMAL = [0-9]+"."[0-9]+
ID = [A-Za-z]([A-Za-z0-9])*
CARAC = \'[^\']\'
COMENTARIOS = (\/\/[^\n]*|\/\*[^*]*\*\/)

// palabras reservadas
VAR = "var"
INT = "int"
RDOUBLE = "double"
BOOL = "bool"
CHAR = "char"
RSTRING = "string"
CONST = "const"
IF = "if"
ELSE = "else"
MATCH = "match"
WHILE = "while"
FOR = "for"
DO = "do"
BREAK = "break"
CONTINUE = "continue"
PRINT = "println"
RTRUE = "true"
RFALSE = "false"
APP = "append"
REM = "remove" 
NEW = "new"
LIST = "list" 
ROUND = "round"
LEN = "length"
TOSTR = "tostring"
FIND = "find"

%%

<YYINITIAL> {PRINT}    {return new Symbol(sym.PRINT, yyline, yycolumn,yytext());}
<YYINITIAL> {VAR}      {return new Symbol(sym.VAR, yyline, yycolumn,yytext());}
<YYINITIAL> {INT}      {return new Symbol(sym.INT, yyline, yycolumn,yytext());}
<YYINITIAL> {RDOUBLE}  {return new Symbol(sym.RDOUBLE, yyline, yycolumn,yytext());}
<YYINITIAL> {BOOL}     {return new Symbol(sym.BOOL, yyline, yycolumn,yytext());}
<YYINITIAL> {CHAR}     {return new Symbol(sym.CHAR, yyline, yycolumn,yytext());}
<YYINITIAL> {RSTRING}  {return new Symbol(sym.RSTRING, yyline, yycolumn,yytext());}
<YYINITIAL> {CONST}    {return new Symbol(sym.CONST, yyline, yycolumn,yytext());}
<YYINITIAL> {IF}       {return new Symbol(sym.IF, yyline, yycolumn,yytext());}
<YYINITIAL> {ELSE}     {return new Symbol(sym.ELSE, yyline, yycolumn,yytext());}
<YYINITIAL> {MATCH}    {return new Symbol(sym.MATCH, yyline, yycolumn,yytext());}
<YYINITIAL> {WHILE}    {return new Symbol(sym.WHILE, yyline, yycolumn,yytext());}
<YYINITIAL> {FOR}      {return new Symbol(sym.FOR, yyline, yycolumn,yytext());}
<YYINITIAL> {DO}       {return new Symbol(sym.DO, yyline, yycolumn,yytext());}
<YYINITIAL> {BREAK}    {return new Symbol(sym.BREAK, yyline, yycolumn,yytext());}
<YYINITIAL> {CONTINUE} {return new Symbol(sym.CONTINUE, yyline, yycolumn,yytext());}
<YYINITIAL> {RTRUE}    {return new Symbol(sym.RTRUE, yyline, yycolumn,yytext());}
<YYINITIAL> {RFALSE}   {return new Symbol(sym.RFALSE, yyline, yycolumn,yytext());}
<YYINITIAL> {APP}      {return new Symbol(sym.APP, yyline, yycolumn,yytext());}
<YYINITIAL> {REM}      {return new Symbol(sym.REM, yyline, yycolumn,yytext());}
<YYINITIAL> {NEW}      {return new Symbol(sym.NEW, yyline, yycolumn,yytext());}
<YYINITIAL> {LIST}     {return new Symbol(sym.LIST, yyline, yycolumn,yytext());}
<YYINITIAL> {ROUND}    {return new Symbol(sym.ROUND, yyline, yycolumn,yytext());}
<YYINITIAL> {LEN}      {return new Symbol(sym.LEN, yyline, yycolumn,yytext());}
<YYINITIAL> {TOSTR}    {return new Symbol(sym.TOSTR, yyline, yycolumn,yytext());}
<YYINITIAL> {FIND}     {return new Symbol(sym.FIND, yyline, yycolumn,yytext());}


<YYINITIAL> {DECIMAL} {return new Symbol(sym.DECIMAL, yyline, yycolumn,yytext());}
<YYINITIAL> {ENTERO}  {return new Symbol(sym.ENTERO, yyline, yycolumn,yytext());}
<YYINITIAL> {ID}      {return new Symbol(sym.ID, yyline, yycolumn,yytext());}
<YYINITIAL> {CARAC}   {return new Symbol(sym.CARAC, yyline, yycolumn,yytext());}

<YYINITIAL> [\"]        {cadena = ""; yybegin(CADENA);}
<CADENA> {
    [\"]        {String str= cadena;
                cadena="";
                yybegin(YYINITIAL);
                return new Symbol(sym.CADENA, yyline, yycolumn,str);}
    [^\"]         { cadena += yytext(); }
    "\\n"         { cadena += "\n"; }    
    "\\\\"        { cadena += "\\"; }   
    "\\\""        { cadena += "\""; }          
    "\\t"         { cadena += "\t"; }        
    "\\'"         { cadena += "'";  } 
}

<YYINITIAL> {PTCOMA}   {return new Symbol(sym.PTCOMA, yyline, yycolumn,yytext());}
<YYINITIAL> {COMA}     {return new Symbol(sym.COMA, yyline, yycolumn,yytext());}
<YYINITIAL> {PUNTO}    {return new Symbol(sym.PUNTO, yyline, yycolumn,yytext());}
<YYINITIAL> {PARIZQ}   {return new Symbol(sym.PARIZQ, yyline, yycolumn,yytext());}
<YYINITIAL> {PARDER}   {return new Symbol(sym.PARDER, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAVIZQ}  {return new Symbol(sym.LLAVIZQ, yyline, yycolumn,yytext());}
<YYINITIAL> {LLAVDER}  {return new Symbol(sym.LLAVDER, yyline, yycolumn,yytext());}
<YYINITIAL> {CORIZQ}   {return new Symbol(sym.CORIZQ, yyline, yycolumn,yytext());}
<YYINITIAL> {CORDER}   {return new Symbol(sym.CORDER, yyline, yycolumn,yytext());}
<YYINITIAL> {DOSPT}    {return new Symbol(sym.DOSPT, yyline, yycolumn,yytext());}
<YYINITIAL> {INCRE}    {return new Symbol(sym.INCRE, yyline, yycolumn,yytext());}
<YYINITIAL> {DECRE}    {return new Symbol(sym.DECRE, yyline, yycolumn,yytext());}
<YYINITIAL> {IGUAL}    {return new Symbol(sym.IGUAL, yyline, yycolumn,yytext());}
<YYINITIAL> {IGUALA}   {return new Symbol(sym.IGUALA, yyline, yycolumn,yytext());}
<YYINITIAL> {DIFER}    {return new Symbol(sym.DIFER, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOR}    {return new Symbol(sym.MENOR, yyline, yycolumn,yytext());}
<YYINITIAL> {MENORQUE} {return new Symbol(sym.MENORQUE, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYOR}    {return new Symbol(sym.MAYOR, yyline, yycolumn,yytext());}
<YYINITIAL> {MAYORQUE} {return new Symbol(sym.MAYORQUE, yyline, yycolumn,yytext());}
<YYINITIAL> {OR}       {return new Symbol(sym.OR, yyline, yycolumn,yytext());}
<YYINITIAL> {AND}      {return new Symbol(sym.AND, yyline, yycolumn,yytext());}
<YYINITIAL> {XOR}      {return new Symbol(sym.XOR, yyline, yycolumn,yytext());}
<YYINITIAL> {NOT}      {return new Symbol(sym.NOT, yyline, yycolumn,yytext());}
<YYINITIAL> {FLECHA}   {return new Symbol(sym.FLECHA, yyline, yycolumn,yytext());}
<YYINITIAL> {DEFAULT}  {return new Symbol(sym.DEFAULT, yyline, yycolumn,yytext());}

<YYINITIAL> {MAS}      {return new Symbol(sym.MAS, yyline, yycolumn,yytext());}
<YYINITIAL> {MENOS}    {return new Symbol(sym.MENOS, yyline, yycolumn,yytext());}
<YYINITIAL> {MULT}     {return new Symbol(sym.MULT, yyline, yycolumn,yytext());}
<YYINITIAL> {DIV}      {return new Symbol(sym.DIV, yyline, yycolumn,yytext());}
<YYINITIAL> {POTENCIA} {return new Symbol(sym.POTENCIA, yyline, yycolumn,yytext());}
<YYINITIAL> {MODULO}   {return new Symbol(sym.MODULO, yyline, yycolumn,yytext());}

<YYINITIAL> {BLANCOS}               {}
<YYINITIAL> {COMENTARIOS}           {}

<YYINITIAL> . {
                listaErrores.add(new Errores("LEXICO","El caracter "+
                yytext()+" NO pertenece al lenguaje", yyline, yycolumn));
}