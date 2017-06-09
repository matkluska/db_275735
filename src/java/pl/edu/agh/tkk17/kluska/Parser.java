package pl.edu.agh.tkk17.kluska;

import java.util.Iterator;

public class Parser {
    private Iterator<Token> tokens;
    private Token ctoken;

    public Parser(Iterable<Token> tokens) {
        this.tokens = tokens.iterator();
        this.forward();
    }

    public static Node parse(Iterable<Token> tokens) {
        Parser parser = new Parser(tokens);
        return parser.parseProgram();
    }

    private void forward() {
        this.ctoken = tokens.next();
    }

    private String value() {
        return ctoken.getValue();
    }

    private boolean check(TokenType type) {
        return ctoken.getType() == type;
    }

    private void expect(TokenType type) {
        if (!this.check(type)) {
            throw new UnexpectedTokenException(ctoken, type);
        }
    }

    private Node parseNumber() {
        this.expect(TokenType.NUM);
        String value = this.value();
        this.forward();
        return new NodeNumber(value);
    }

    private Node parseTerm() {
        Node left = this.parseFactor();
        if (check(TokenType.MUL)) {
            forward();
            Node right = parseTerm();
            return new NodeMul(left, right);
        } else if (check(TokenType.DIV)) {
            forward();
            Node right = parseTerm();
            validate(right);
            return new NodeDiv(left, right);
        } else {
            return left;
        }
    }

    private Node parseExpression() {
        Node left = this.parseTerm();
        if (check(TokenType.ADD)) {
            forward();
            Node right = parseExpression();
            return new NodeAdd(left, right);
        } else if (check(TokenType.SUB)) {
            forward();
            Node right = parseExpression();
            return new NodeSub(left, right);
        } else {
            return left;
        }
    }

    private Node parseFactor() {
        Node left;
        if (check(TokenType.LBR)) {
            forward();
            left = parseExpression();
            expect(TokenType.RBR);
            forward();
        } else {
            left = parseNumber();
        }
        return left;
    }

    private void validate(Node right) {
        if (right instanceof NodeNumber) {
            if (((NodeNumber) right).getValue().equals("0")) {
                throw new OutputableException("Division by 0.");
            }
        }
    }

    private Node parseProgram() {
        Node root = this.parseExpression();
        this.expect(TokenType.END);
        return root;
    }
}
