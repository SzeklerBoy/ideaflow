package node;

import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class NodeTypeFactory {
    private static NodeTypeFactory instance;
    private final Map<NodeTypeKey, NodeType> nodeTypes = new ConcurrentHashMap<>();

    private NodeTypeFactory() {
    }

    public static synchronized NodeTypeFactory getInstance() {
        if (instance == null) {
            instance = new NodeTypeFactory();
        }
        return instance;

    }

    public NodeType getNodeType(Color color, int size, ShapeType shapeType) {
        NodeTypeKey key = new NodeTypeKey(color, size, shapeType);
        return nodeTypes.computeIfAbsent(key, k -> new NodeType(color, size, shapeType));
    }

    private static final class NodeTypeKey {
        private final Color color;
        private final int size;
        private final ShapeType shapeType;

        private NodeTypeKey(Color color, int size, ShapeType shapeType) {
            this.color = color;
            this.size = size;
            this.shapeType = shapeType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            NodeTypeKey that = (NodeTypeKey) o;
            return size == that.size &&
                    Objects.equals(color, that.color) &&
                    shapeType == that.shapeType;
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, size, shapeType);
        }
    }
}
