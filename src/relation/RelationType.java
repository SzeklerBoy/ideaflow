package relation;

import java.awt.*;

public class RelationType {
    private final Color color;
    private final float strokeWidth;
    private final boolean dashed;

    public RelationType(Color color, float strokeWidth, boolean dashed) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.dashed = dashed;
    }

    public void draw(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(this.color);

        if (dashed) {
            float[] dashPattern = {5f, 5f};
            g2.setStroke(new BasicStroke(strokeWidth,
                    BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER,
                    10.0f, dashPattern, 0.0f
            ));
        } else {
            g2.setStroke(new BasicStroke(strokeWidth));
        }

        g2.drawLine(x1, y1, x2, y2);

        drawArrowHead(g2, x1, y1, x2, y2);

        g2.dispose();
    }

    private void drawArrowHead(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double angle = Math.atan2(y2 - y1, x2 - x1);
        int arrowSize = 10;

        int xArrow1 = (int) (x2 - arrowSize * Math.cos(angle - Math.PI / 6));
        int yArrow1 = (int) (y2 - arrowSize * Math.sin(angle - Math.PI / 6));

        int xArrow2 = (int) (x2 - arrowSize * Math.cos(angle + Math.PI / 6));
        int yArrow2 = (int) (y2 - arrowSize * Math.sin(angle + Math.PI / 6));

        Polygon arrowHead = new Polygon();
        arrowHead.addPoint(x2, y2);
        arrowHead.addPoint(xArrow1, yArrow1);
        arrowHead.addPoint(xArrow2, yArrow2);

        g2.fillPolygon(arrowHead);
    }
}
