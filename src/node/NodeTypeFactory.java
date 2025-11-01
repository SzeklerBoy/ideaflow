package node;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class NodeTypeFactory {
    private static NodeTypeFactory instance;
    private final Map<String, NodeType> nodeTypes = new HashMap<>();

    private NodeTypeFactory() {
    }

    public static synchronized NodeTypeFactory getInstance() {
        if (instance == null) {
            instance = new NodeTypeFactory();
        }
        return instance;

    }

    public NodeType getNodeType(Color color, int size, ShapeType shapeType) {
        String key = color.toString() + "_" + size + "_" + shapeType.name();
        return nodeTypes.computeIfAbsent(key, k -> new NodeType(color, size, shapeType));
    }

    public int getTotalNodeTypes() {
        return nodeTypes.size();
    }
}
