import mindMap.MindMapCanvas;
import mindMap.MindMapMenuPanel;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ideaflow - Demo");
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        frame.setLayout(new BorderLayout(10, 10));
        frame.setSize(1000, 700);

        MindMapCanvas mindMapCanvas = new MindMapCanvas();
        MindMapMenuPanel menuPanel = new MindMapMenuPanel(mindMapCanvas);
        JScrollPane scrollPane = new JScrollPane(menuPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // smoother scroll

        frame.add(scrollPane, BorderLayout.WEST);
        frame.add(new JScrollPane(mindMapCanvas), BorderLayout.CENTER);

        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}