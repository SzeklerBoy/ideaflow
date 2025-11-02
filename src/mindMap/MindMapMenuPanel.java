package mindMap;

import node.Node;
import node.ShapeType;
import relation.RelationKind;

import javax.swing.*;
import java.awt.*;
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
    private JComboBox<RelationKind> relationKindCombo;
    private JTextField relationLabelField;
    private JSpinner strokeSpinner;
    private JCheckBox dashedCheck;
    private Color selectedRelationColor = Color.BLACK;

    private final MindMapCanvas mindMapCanvas;

    public MindMapMenuPanel(MindMapCanvas mindMapCanvas) {
        this.mindMapCanvas = mindMapCanvas;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(245, 245, 245));
        setPreferredSize(new Dimension(260, 600));

        addNodeSection();
        add(Box.createVerticalStrut(20));
        addRelationSection();
        add(Box.createVerticalGlue());
    }

    // ===========================
    // NODE CREATION SECTION
    // ===========================
    private void addNodeSection() {
        JLabel title = new JLabel("🟦 Add Node");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        add(title);
        add(Box.createVerticalStrut(10));

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

        add(new JLabel("Color:"));
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

        // Create Add and Clear buttons
        JButton addButton = new JButton("Add Node");
        addButton.setBackground(new Color(70, 130, 180));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        JButton clearButton = new JButton("Clear Map");
        clearButton.setBackground(new Color(220, 20, 60));
        clearButton.setForeground(Color.WHITE);
        clearButton.setFocusPainted(false);

// Put both buttons in a horizontal panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0)); // spacing = 10px
        buttonPanel.setBackground(new Color(245, 245, 245)); // same as parent background
        buttonPanel.add(addButton);
        buttonPanel.add(clearButton);

        add(buttonPanel);
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
    private void addRelationSection() {
        add(new JSeparator());
        add(Box.createVerticalStrut(20));

        JLabel title = new JLabel("🔗 Create Relation");
        title.setFont(title.getFont().deriveFont(Font.BOLD, 14f));
        add(title);
        add(Box.createVerticalStrut(10));

        // FROM node
        add(new JLabel("From Node:"));
        fromNodeCombo = new JComboBox<>();
        fromNodeCombo.setMaximumSize(new Dimension(300, 25));
        add(fromNodeCombo);
        add(Box.createVerticalStrut(10));

        // TO node
        add(new JLabel("To Node:"));
        toNodeCombo = new JComboBox<>();
        toNodeCombo.setMaximumSize(new Dimension(300, 25));
        add(toNodeCombo);
        add(Box.createVerticalStrut(10));

        // Refresh node list
        JButton refreshButton = new JButton("↻ Refresh Nodes");
        refreshButton.addActionListener(e -> refreshNodeCombos());
        add(refreshButton);
        add(Box.createVerticalStrut(10));

        // Color
        add(new JLabel("Color:"));
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
        add(colorButton);
        add(Box.createVerticalStrut(10));

        // Stroke
        add(new JLabel("Stroke Width:"));
        strokeSpinner = new JSpinner(new SpinnerNumberModel(2.0, 1.0, 10.0, 0.5));
        strokeSpinner.setMaximumSize(new Dimension(300, 25));
        add(strokeSpinner);
        add(Box.createVerticalStrut(10));

        dashedCheck = new JCheckBox("Dashed Line");
        dashedCheck.setAlignmentX(Component.LEFT_ALIGNMENT);
        add(dashedCheck);
        add(Box.createVerticalStrut(10));

        JButton addRelationButton = new JButton("Add Relation");
        addRelationButton.setBackground(new Color(100, 149, 237));
        addRelationButton.setForeground(Color.WHITE);
        addRelationButton.setFocusPainted(false);
        add(addRelationButton);

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

        String label = relationLabelField.getText();
        RelationKind kind = (RelationKind) relationKindCombo.getSelectedItem();
        Color color = selectedRelationColor;
        float strokeWidth = ((Double) strokeSpinner.getValue()).floatValue();
        boolean dashed = dashedCheck.isSelected();

        MindMapManager.getInstance().addRelation(from, to, kind, color, strokeWidth, dashed, label);
        mindMapCanvas.repaint();
    }
}
