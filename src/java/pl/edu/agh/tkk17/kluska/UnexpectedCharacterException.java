package pl.edu.agh.tkk17.kluska;

public class UnexpectedCharacterException extends OutputableException {
    public UnexpectedCharacterException(char character, String location) {
        super("Unexpected character '" + character + "' at " + location + ".");
    }
}
