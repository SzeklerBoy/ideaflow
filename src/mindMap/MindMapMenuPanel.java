package mindMap;

import node.ShapeType;

import javax.swing.*;
import java.awt.*;

public class MindMapMenuPanel extends JPanel {
    private JTextField labelField;
    private JComboBox<ShapeType> shapeCombo;
    private JSpinner sizeSpinner;
    private Color selectedColor = Color.BLUE;

    private final MindMapCanvas mindMapCanvas;

    public MindMapMenuPanel(MindMapCanvas mindMapCanvas) {
        this.mindMapCanvas = mindMapCanvas;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(250, 600));

        add(new JLabel("Label:"));
        labelField = new JTextField("New Node");
        labelField.setMaximumSize(new Dimension(300, 25));
        add(labelField);
        add(Box.createVerticalStrut(10));

        add(new JLabel("Shape:"));
        shapeCombo = new JComboBox<>(ShapeType.values());
        shapeCombo.setMaximumSize(new Dimension(300, 25));
        add(shapeCombo);
        add(Box.createVerticalStrut(10));

        add(new JLabel("Color: "));
        JButton colorButton = new JButton("Choose Color");
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Node Color", selectedColor);
            if (newColor != null) {
                selectedColor = newColor;
                colorButton.setBackground(selectedColor);
            }
        });
        colorButton.setBackground(selectedColor);
        colorButton.setForeground(Color.WHITE);
        add(colorButton);
        add(Box.createVerticalStrut(10));

        add(new JLabel("Size:"));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(60, 30, 150, 5));
        sizeSpinner.setMaximumSize(new Dimension(300, 25));
        add(sizeSpinner);
        add(Box.createVerticalStrut(10));


        JButton addButton = new JButton("Add Node");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        add(addButton);
        add(Box.createVerticalStrut(10));


        JButton clearButton = new JButton("Clear Map");
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);
        add(clearButton);
        add(Box.createVerticalGlue());

        addButton.addActionListener(e -> addNodeAction());
        clearButton.addActionListener(e -> clearMapAction());
    }

    private void addNodeAction() {
        String label = labelField.getText();
        ShapeType shape = (ShapeType) shapeCombo.getSelectedItem();
        int size = (int) sizeSpinner.getValue();
        Color color = selectedColor;

        int x = mindMapCanvas.getWidth() / 2;
        int y = mindMapCanvas.getHeight() / 2;

        MindMapManager.getInstance().addNode(x, y, label, color, size, shape);
        mindMapCanvas.repaint();

    }

    private void clearMapAction() {
        MindMapManager mindMapManager = MindMapManager.getInstance();
        mindMapManager.getNodes().clear();
        mindMapManager.getRelations().clear();
        mindMapManager.draw(null);
        mindMapCanvas.repaint();

    }
}