package popl.example;
import java.util.ArrayList;

// A simple lexer for parsing input strings into variable tokens,
// number tokens, operator tokens, and other character tokens.
// The parsed tokens will be sent to the Parser, 
// which will use the tokens to build an abstract syntax tree.

public class lexer {
    // The input string to be lexed, the current position in the input, 
    // the current character, and an array to store the lexed tokens.
    String input;
    int position;
    char ch;
    ArrayList<String> tokens;

    // Constructor to initialize the lexer with the input string.
    public lexer(String input) {
        this.input = input;
        this.position = 0;
        this.ch = input.charAt(position);
        this.tokens = new ArrayList<>();
    }
    // Parses the input string and stores the lexed tokens.
    public void parse() {
        while (!isEOF()) {
            // Skips whitespace characters.
            if (isWhitespace()) {
                position++;
                if (position < input.length()) {
                    ch = input.charAt(position);
                } else {
                    ch = '\0';
                }
            } else if (isLetter() || ch == '_') {
                // Parses a sequence of letters, digits, underscores as identifier.
                StringBuilder identifier = new StringBuilder();
                while (isLetter() || isDigit() || ch == '_' ) {
                    identifier.append(ch);
                    position++;
                    if (position < input.length()) {
                        ch = input.charAt(position);
                    } else {
                        ch = '\0';
                    }
                }
                tokens.add(identifier.toString());
            } else if (isDigit()) {
                // Parses a sequence of digits as a number.
                // Allows decimals, note that '1.' is a valid input from here currently.
                StringBuilder number = new StringBuilder();
                boolean hasDecimal = false; // Condition check for decimal.
                while (isDigit() || (ch == '.' && !hasDecimal)) {
                    if (ch == '.') {
                        hasDecimal = true;
                        }
                    number.append(ch);
                    position++;
                    if (position < input.length()) {
                        ch = input.charAt(position);
                    } else {
                        ch = '\0';
                    }
                }
                tokens.add(number.toString());  
            } else if (ch == '\"' || ch == '\'') {
                // Parses a string enclosed by single/double quote as one string.
                // Continues consuming until it hits same quote character/EOF.
                char quoteCharacter = ch;
                StringBuilder string = new StringBuilder();

                string.append(ch);
                position++;
                if (position < input.length()) {
                    ch = input.charAt(position);
                } else {
                    ch = '\0';
                    }
                while ((ch != quoteCharacter) && (ch != '\0')) {
                    string.append(ch);
                    position++;
                    if (position < input.length()) {
                        ch = input.charAt(position);
                        } 
                    else {
                        ch = '\0';
                        }
                    }
                if (ch == quoteCharacter) {
                    string.append(ch);
                    position++;
                    if (position < input.length()) {
                        ch = input.charAt(position);
                        }  
                    else {
                        ch = '\0';
                        }
                    }
                tokens.add(string.toString());
            } else if (isOperator()) {
                // Parses for operators, needed for multi-char operators like +=.
                StringBuilder operator = new StringBuilder();
                char firstch = ch;
                position++;
                if (position < input.length()) {
                    ch = input.charAt(position);
                    } 
                else {
                    ch = '\0';
                    }
                // If input is +=, -=, *=, /=, consume both chars as one token.    
                if (firstch != '%' && ch == '=')    {
                    operator.append(firstch);
                    operator.append(ch);
                    position++;
                    if (position < input.length()) {
                        ch = input.charAt(position);
                        } 
                    else {
                        ch = '\0';
                        }
                    }
                // Else consume only the initial ch and leave current alone.    
                else    {
                    operator.append(firstch);  
                    }   
                tokens.add(operator.toString());               
            } else {
                // Handles other characters as needed.
                tokens.add(String.valueOf(ch));
                position++;
                if (position < input.length()) {
                    ch = input.charAt(position);
                } else {
                    ch = '\0';
                }
            }
        }
    }

    // Getters for the lexer's state and lexed tokens.
    public char getChar() {
        return ch;
    }   
    // Returns the current position in the input string.
    public int getPosition() {
        return position;
    }
    // Returns the input string.
    public String getInput() {
        return input;
    }
    // Returns the array of lexed tokens.
    public ArrayList<String> getTokens() {
        return tokens;
    }
    // Adds a token to the array of lexed tokens.
    public void addToTokens(String value) {
        tokens.add(value);
    }
    // Checks if the lexer has reached the end of the input.
    public boolean isEOF() {
        return ch == '\0';
    }
    // Checks if the current character is whitespace.
    public boolean isWhitespace() {
        return ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r';
    }
    // Checks if the current character is a letter.
    public boolean isLetter() {
        return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
    }
    // Checks if the current character is a digit.
    public boolean isDigit() {
        return ch >= '0' && ch <= '9';
    }
    // Checks if the current character is a digit.
    public boolean isOperator() {
        return ch == '+' || ch == '-' || ch == '*' || ch == '/' || ch == '%';
    }
}
