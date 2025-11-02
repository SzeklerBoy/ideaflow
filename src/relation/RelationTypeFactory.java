package relation;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class RelationTypeFactory {
    private static RelationTypeFactory instance;
    private final Map<String, RelationType> relationTypes = new HashMap<>();

    private RelationTypeFactory() {
    }

    public static synchronized RelationTypeFactory getInstance() {
        if (instance == null) {
            instance = new RelationTypeFactory();
        }
        return instance;
    }

    public RelationType getRelationType(Color color, float strokeWidth, boolean dashed) {
        String key = color.toString() + "_" + strokeWidth + "_" + dashed;
        return relationTypes.computeIfAbsent(key, k -> new RelationType(color, strokeWidth, dashed));
    }

    public int getPoolSize() {
        return relationTypes.size();
    }
}
