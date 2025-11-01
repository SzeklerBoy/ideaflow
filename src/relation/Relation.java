package relation;

import node.Node;

import java.awt.*;

public class Relation {
    private final Node from;
    private final Node to;
    private final RelationType type;
    private final int offsetX;
    private final int offsetY;

    public Relation(Node from, Node to, RelationType type, String label, int offsetX, int offsetY) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public void draw(Graphics g){
        int x1 = from.getX() + offsetX;
        int y1 = from.getY() + offsetY;
        int x2 = to.getX();
        int y2 = from.getY();

        type.draw(g,x1,y1,x2,y2);
    }
}
