package mindMap;

import node.MindNode;
import relation.Relation;

import java.util.ArrayList;
import java.util.List;

public class MindMapManager {
    private static MindMapManager mindMapInstance;
    private List<MindNode> nodes = new ArrayList<>();
    private List<Relation> relations = new ArrayList<>();

    private MindMapManager() {
    }

    public static synchronized MindMapManager getInstance() {
        if (mindMapInstance == null) {
            mindMapInstance = new MindMapManager();
        }
        return mindMapInstance;
    }

    public void addNode(MindNode mindNode) {
        nodes.add(mindNode);
    }


    public void addRelation(Relation relation) {
        relations.add(relation);
    }

}
