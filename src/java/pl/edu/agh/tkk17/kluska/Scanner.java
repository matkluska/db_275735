package pl.edu.agh.tkk17.kluska;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class Scanner implements Iterator<Token>, Iterable<Token> {
    private InputStream input;
    private int position;
    private char character;
    private boolean end;

    public Scanner(InputStream input) {
        this.input = input;
        position = -1;
        end = false;
        readChar();
    }

    private static String locate(int position) {
        return String.valueOf(position);
    }

    private void readChar() {
        try {
            int character = input.read();
            position += 1;
            boolean end = character < 0;
            this.end = end;
            if (!end) {
                this.character = (char) character;
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Scanner input exception.", e);
        }
    }

    public boolean hasNext() {
        return !end;
    }

    public Token next() {
        if (end) {
            throw new NoSuchElementException("Scanner input ended.");
        }

        char character = this.character;
        Token token;

        if (character == '+') {
            token = makeToken(TokenType.ADD);
            readChar();
        } else if (character == '-') {
            token = makeToken(TokenType.SUB);
            readChar();
        } else if (character == '*') {
            token = makeToken(TokenType.MUL);
            readChar();
        } else if (character == '/') {
            token = makeToken(TokenType.DIV);
            readChar();
        } else if (character == '(') {
            token = makeToken(TokenType.LBR);
            readChar();
        } else if (character == ')') {
            token = makeToken(TokenType.RBR);
            readChar();
        } else if (Character.isDigit(character)) {
            String value = fetchNumber();
            token = makeToken(TokenType.NUM, value);
        } else if (character == '\n' || character == '\u0000') {
            token = makeToken(TokenType.END);
            readChar();
        } else if (Character.isWhitespace(character)) {
            readChar();
            token = next();
        } else {
            String location = locate(position);
            throw new UnexpectedCharacterException(character, location);
        }
        return token;
    }

    public Iterator<Token> iterator() {
        return this;
    }

    private Token makeToken(TokenType type) {
        return new Token(type, position);
    }

    private Token makeToken(TokenType type, String value) {
        return new Token(type, position, value);
    }

    private String fetchNumber() {
        StringBuilder number = new StringBuilder();
        do {
            number.append(character);
            readChar();
        } while (Character.isDigit(character) || character == '.');
        return number.toString();
    }
}
