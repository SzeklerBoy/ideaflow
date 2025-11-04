package node;

import node.ShapeType;

import java.awt.*;

public class NodeType {
    private final Color color;
    private final int size;
    private final ShapeType shapeType;

    public NodeType(Color color, int size, ShapeType shapeType) {
        this.color = color;
        this.size = size;
        this.shapeType = shapeType;
    }

    public void draw(Graphics g, int x, int y, String label) {
        Graphics2D g2 = (Graphics2D) g.create(); // isolate context
        int half = size / 2;

        // Fill Shape
        g2.setColor(color);
        Shape shape = null;

        switch (shapeType) {
            case CIRCLE -> {
                shape = new java.awt.geom.Ellipse2D.Double(x - half, y - half, size, size);
                g2.fill(shape);
            }
            case RECTANGLE -> {
                shape = new Rectangle(x - half, y - half, size, size);
                g2.fill(shape);
            }
            case DIAMOND -> {
                Polygon diamond = new Polygon();
                diamond.addPoint(x, y - half);
                diamond.addPoint(x + half, y);
                diamond.addPoint(x, y + half);
                diamond.addPoint(x - half, y);
                g2.fillPolygon(diamond);
                shape = diamond;
            }
        }

        // Draw Border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(1));
        if (shape != null) {
            if (shapeType == ShapeType.DIAMOND) {
                g2.drawPolygon((Polygon) shape);
            } else {
                g2.draw(shape);
            }
        }

        // Draw Label
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(label);
        int textX = x - textWidth / 2;
        int textY = y + half + fm.getHeight();
        g2.drawString(label, textX, textY);

        g2.dispose();
    }

    // Getters
    public Color getColor() {
        return color;
    }

    public int getSize() {
        return size;
    }

    public ShapeType getShapeType() {
        return shapeType;
    }
}
