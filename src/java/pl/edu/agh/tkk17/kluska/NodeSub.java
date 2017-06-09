package pl.edu.agh.tkk17.kluska;

public class NodeSub extends Node {
    public NodeSub(Node left, Node right) {
        super(left, right);
    }

    public void accept(NodeVisitor visitor) {
        visitor.visit(this);
    }
}
