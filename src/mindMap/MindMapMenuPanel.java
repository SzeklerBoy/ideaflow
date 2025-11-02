package mindMap;

import node.Node;
import node.ShapeType;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class MindMapMenuPanel extends JPanel {
    // Node UI
    private JTextField labelField;
    private JComboBox<ShapeType> shapeCombo;
    private JSpinner sizeSpinner;
    private Color selectedColor = Color.BLUE;

    // Relation UI
    private JComboBox<Node> fromNodeCombo;
    private JComboBox<Node> toNodeCombo;
    private JTextField relationLabelField;
    private JSpinner strokeSpinner;
    private JCheckBox dashedCheck;
    private Color selectedRelationColor = Color.BLACK;

    private final MindMapCanvas mindMapCanvas;

    public MindMapMenuPanel(MindMapCanvas mindMapCanvas) {
        this.mindMapCanvas = mindMapCanvas;

        // Use BorderLayout to hold a scrollable content panel
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 245));

        // Inner content panel that defines the scrollable content
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(245, 245, 245));
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Build both sections
        addNodeSection(content);
        content.add(Box.createVerticalStrut(20));
        addRelationSection(content);

        // Add the content to the top so BoxLayout keeps natural height
        add(content, BorderLayout.NORTH);
    }

    // ===========================
    // NODE CREATION SECTION
    // ===========================
    private void addNodeSection(JPanel parent) {
        JLabel title = new JLabel("🟦 Add Node");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        parent.add(title);
        parent.add(Box.createVerticalStrut(10));

        parent.add(new JLabel("Label:"));
        labelField = new JTextField("New Node");
        labelField.setMaximumSize(new Dimension(300, 25));
        parent.add(labelField);
        parent.add(Box.createVerticalStrut(10));

        parent.add(new JLabel("Shape:"));
        shapeCombo = new JComboBox<>(ShapeType.values());
        shapeCombo.setMaximumSize(new Dimension(300, 25));
        parent.add(shapeCombo);
        parent.add(Box.createVerticalStrut(10));

        parent.add(new JLabel("Color:"));
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
        parent.add(colorButton);
        parent.add(Box.createVerticalStrut(10));

        parent.add(new JLabel("Size:"));
        sizeSpinner = new JSpinner(new SpinnerNumberModel(60, 30, 150, 5));
        sizeSpinner.setMaximumSize(new Dimension(300, 25));
        parent.add(sizeSpinner);
        parent.add(Box.createVerticalStrut(10));

        // Buttons panel
        JButton addButton = new JButton("Add Node");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        JButton clearButton = new JButton("Clear Map");
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(245, 245, 245));
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        JButton saveButton = new JButton("💾 Save as PNG");
        saveButton.setBackground(new Color(34, 139, 34));
        saveButton.setForeground(Color.WHITE);
        saveButton.setFocusPainted(false);

        buttonPanel.add(saveButton);


        parent.add(buttonPanel);
        parent.add(Box.createVerticalStrut(10));

        addButton.addActionListener(e -> addNodeAction());
        clearButton.addActionListener(e -> clearMapAction());
        saveButton.addActionListener(e -> saveCanvasAsImage());

    }

    private void addNodeAction() {
        String label = labelField.getText();
        ShapeType shape = (ShapeType) shapeCombo.getSelectedItem();
        int size = (int) sizeSpinner.getValue();
        Color color = selectedColor;

        int x = mindMapCanvas.getWidth() / 2;
        int y = mindMapCanvas.getHeight() / 2;

        MindMapManager.getInstance().addNode(x, y, label, color, size, shape);
        refreshNodeCombos();
        mindMapCanvas.repaint();
    }

    private void clearMapAction() {
        MindMapManager mindMapManager = MindMapManager.getInstance();
        mindMapManager.getNodes().clear();
        mindMapManager.getRelations().clear();
        refreshNodeCombos();
        mindMapCanvas.repaint();
    }

    // ===========================
    // RELATION CREATION SECTION
    // ===========================
    private void addRelationSection(JPanel parent) {
        parent.add(new JSeparator());
        parent.add(Box.createVerticalStrut(20));

        JLabel title = new JLabel("🔗 Create Relation");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        parent.add(title);
        parent.add(Box.createVerticalStrut(10));

        // FROM node
        parent.add(new JLabel("From Node:"));
        fromNodeCombo = new JComboBox<>();
        fromNodeCombo.setMaximumSize(new Dimension(300, 25));
        parent.add(fromNodeCombo);
        parent.add(Box.createVerticalStrut(10));

        // TO node
        parent.add(new JLabel("To Node:"));
        toNodeCombo = new JComboBox<>();
        toNodeCombo.setMaximumSize(new Dimension(300, 25));
        parent.add(toNodeCombo);
        parent.add(Box.createVerticalStrut(10));

        JButton refreshButton = new JButton("↻ Refresh Nodes");
        refreshButton.addActionListener(e -> refreshNodeCombos());
        parent.add(refreshButton);
        parent.add(Box.createVerticalStrut(10));

        // Color
        parent.add(new JLabel("Color:"));
        JButton colorButton = new JButton("Choose Relation Color");
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choose Relation Color", selectedRelationColor);
            if (newColor != null) {
                selectedRelationColor = newColor;
                colorButton.setBackground(selectedRelationColor);
            }
        });
        colorButton.setBackground(selectedRelationColor);
        colorButton.setForeground(Color.WHITE);
        parent.add(colorButton);
        parent.add(Box.createVerticalStrut(10));

        // Stroke width
        parent.add(new JLabel("Stroke Width:"));
        strokeSpinner = new JSpinner(new SpinnerNumberModel(2.0, 1.0, 10.0, 0.5));
        strokeSpinner.setMaximumSize(new Dimension(300, 25));
        parent.add(strokeSpinner);
        parent.add(Box.createVerticalStrut(10));

        dashedCheck = new JCheckBox("Dashed Line");
        dashedCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        parent.add(dashedCheck);
        parent.add(Box.createVerticalStrut(10));

        JButton addRelationButton = new JButton("Add Relation");
        addRelationButton.setBackground(new Color(100, 149, 237));
        addRelationButton.setForeground(Color.WHITE);
        addRelationButton.setFocusPainted(false);
        parent.add(addRelationButton);
        parent.add(Box.createVerticalStrut(10));

        addRelationButton.addActionListener(e -> addRelationAction());

        refreshNodeCombos(); // initial population
    }

    private void refreshNodeCombos() {
        List<Node> nodes = MindMapManager.getInstance().getNodes();
        fromNodeCombo.removeAllItems();
        toNodeCombo.removeAllItems();

        for (Node node : nodes) {
            fromNodeCombo.addItem(node);
            toNodeCombo.addItem(node);
        }
    }

    private void addRelationAction() {
        Node from = (Node) fromNodeCombo.getSelectedItem();
        Node to = (Node) toNodeCombo.getSelectedItem();
        if (from == null || to == null || from == to) {
            JOptionPane.showMessageDialog(this, "Please select two different nodes.");
            return;
        }

        Color color = selectedRelationColor;
        float strokeWidth = ((Double) strokeSpinner.getValue()).floatValue();
        boolean dashed = dashedCheck.isSelected();

        MindMapManager.getInstance().addRelation(from, to, color, strokeWidth, dashed);
        mindMapCanvas.repaint();
    }

    private void saveCanvasAsImage() {
        // Let the user choose a save location
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Save Mind Map as PNG");
        fileChooser.setSelectedFile(new java.io.File("mindmap.png"));

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            java.io.File file = fileChooser.getSelectedFile();
            try {
                // Create a BufferedImage the size of the canvas
                int width = mindMapCanvas.getWidth();
                int height = mindMapCanvas.getHeight();
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

                // Paint the canvas onto the image
                Graphics2D g2d = image.createGraphics();
                mindMapCanvas.paint(g2d);
                g2d.dispose();

                // Ensure file ends with .png
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new java.io.File(file.getParentFile(), file.getName() + ".png");
                }

                // Write to disk
                javax.imageio.ImageIO.write(image, "png", file);

                JOptionPane.showMessageDialog(this, "Mind map saved to:\n" + file.getAbsolutePath());
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving image: " + ex.getMessage());
            }
        }
    }

}
