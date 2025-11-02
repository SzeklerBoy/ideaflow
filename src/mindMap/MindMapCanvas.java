package mindMap;

import node.Node;
import relation.RelationKind;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class MindMapCanvas extends JPanel {
    private final MindMapManager mindMapManager = MindMapManager.getInstance();
    private Node selectedNode;
    private int offsetX, offsetY;
    private boolean relationMode = false;
    private Node startNode = null;
    private RelationKind currentKind;
    private Color currentRelationColor;
    private float currentStrokeWidth;
    private boolean currentDashed;
    private String currentLabel;

    // Called from MindMapMenuPanel
    public void enableRelationCreation(RelationKind kind, Color color, float strokeWidth, boolean dashed, String label) {
        this.relationMode = true;
        this.currentKind = kind;
        this.currentRelationColor = color;
        this.currentStrokeWidth = strokeWidth;
        this.currentDashed = dashed;
        this.currentLabel = label;
        this.startNode = null;
    }


    public MindMapCanvas() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        MouseAdapter mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                List<Node> nodes = mindMapManager.getNodes();
                for (Node node : nodes) {
                    if (node.contains(e.getX(), e.getY())) {
                        selectedNode = node;
                        offsetX = e.getX() - node.getX();
                        offsetY = e.getY() - node.getY();
                        break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                if (selectedNode != null) {
                    selectedNode.setPosition(e.getX() - offsetX, e.getY() - offsetY);
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                selectedNode = null;
            }
        };

        addMouseListener(mouseAdapter);
        addMouseMotionListener(mouseAdapter);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        mindMapManager.draw(g);
    }
}
