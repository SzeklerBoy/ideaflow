package mindMap;

import node.Node;
import node.NodeType;
import node.NodeTypeFactory;
import node.ShapeType;
import relation.Relation;
import relation.RelationKind;
import relation.RelationType;
import relation.RelationTypeFactory;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MindMapManager {
    private static MindMapManager mindMapInstance;
    private final List<Node> nodes = new ArrayList<>();
    private List<Relation> relations = new ArrayList<>();

    private MindMapManager() {
    }

    public static synchronized MindMapManager getInstance() {
        if (mindMapInstance == null) {
            mindMapInstance = new MindMapManager();
        }
        return mindMapInstance;
    }

    public void addNode(int x, int y, String label, Color color, int size, ShapeType shapeType) {
        NodeType nodeType = NodeTypeFactory.getInstance().getNodeType(color, size, shapeType);
        Node node = new Node(x, y, label, nodeType);
        nodes.add(node);
    }


    public void addRelation(Node from, Node to, RelationKind kind, Color color, float strokeWidth, boolean dashed, String label) {
        RelationType type = RelationTypeFactory.getInstance().getRelationType(color, strokeWidth, dashed, kind);
        relations.add(new Relation(from, to, type, label, 0, 0));
    }


    public void draw(Graphics g) {
        if (g == null) return;
        for (Node node : nodes) {
            node.draw(g);
        }

        for (Relation relation : relations) {
            relation.draw(g);
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Relation> getRelations() {
        return relations;
    }
}
