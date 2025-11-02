package mindMap;

import javax.swing.*;
import java.awt.*;

public class MindMapCanvas extends JPanel {
    private final MindMapManager mindMapManager = MindMapManager.getInstance();

    public MindMapCanvas() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);
  }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        mindMapManager.draw(g);
    }
}