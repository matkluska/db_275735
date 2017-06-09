package pl.edu.agh.tkk17.kluska;

import java.util.Stack;

public class RpnEvaluatorVisitor implements NodeVisitor {
    private Stack<Float> stack;

    public RpnEvaluatorVisitor() {
        stack = new Stack<>();
    }

    public float getValue() {
        return stack.peek();
    }

    public void visit(NodeAdd node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        float a = stack.pop();
        float b = stack.pop();
        float c = b + a;
        stack.push(c);
    }

    public void visit(NodeSub node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        float a = stack.pop();
        float b = stack.pop();
        float c = b - a;
        stack.push(c);
    }

    public void visit(NodeMul node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        float a = stack.pop();
        float b = stack.pop();
        float c = b * a;
        stack.push(c);
    }

    public void visit(NodeDiv node) {
        node.getLeft().accept(this);
        node.getRight().accept(this);
        float a = stack.pop();
        float b = stack.pop();
        float c = b / a;
        stack.push(c);
    }

    public void visit(NodeNumber node) {
        String value = node.getValue();
        float numericValue = Float.parseFloat(value);
        stack.push(numericValue);
    }
}
