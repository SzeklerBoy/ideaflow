package node;

import java.awt.*;

public class Node {
    private final int x;
    private final int y;
    private final String label;
    private final NodeType type;

    public Node(int x, int y, String label, NodeType type) {
        this.x = x;
        this.y = y;
        this.label = label;
        this.type = type;
    }

    public void draw(Graphics g) {
        type.draw(g, x, y, label);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getLabel() {
        return label;
    }

    public NodeType getType() {
        return type;
    }
}
