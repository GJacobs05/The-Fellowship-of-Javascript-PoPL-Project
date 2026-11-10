parser grammar PythonParser;
options { tokenVocab=PythonLexer; }

program
    : stat EOF
    ;
