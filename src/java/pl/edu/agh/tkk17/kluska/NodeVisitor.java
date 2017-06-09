package pl.edu.agh.tkk17.kluska;

public interface NodeVisitor {
    void visit(NodeAdd node);

    void visit(NodeSub node);

    void visit(NodeMul node);

    void visit(NodeDiv node);

    void visit(NodeNumber node);
}
