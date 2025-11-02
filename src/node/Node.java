package node;

import java.awt.*;

public class Node {
    private int x;
    private int y;
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

    public boolean contains(int mx, int my) {
        return mx >= x - type.getSize() / 2 && mx <= x + type.getSize() / 2 &&
                my >= y - type.getSize() / 2 && my <= y + type.getSize() / 2;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
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
