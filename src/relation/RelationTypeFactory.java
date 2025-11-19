package relation;

import java.awt.*;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class RelationTypeFactory {
    private static RelationTypeFactory instance;
    private final Map<RelationTypeKey, RelationType> relationTypes = new ConcurrentHashMap<>();

    private RelationTypeFactory() {
    }

    public static synchronized RelationTypeFactory getInstance() {
        if (instance == null) {
            instance = new RelationTypeFactory();
        }
        return instance;
    }

    public RelationType getRelationType(Color color, float strokeWidth, boolean dashed) {
        RelationTypeKey key = new RelationTypeKey(color, strokeWidth, dashed);
        return relationTypes.computeIfAbsent(key, k -> new RelationType(color, strokeWidth, dashed));
    }


    private static final class RelationTypeKey {
        private final Color color;
        private final float strokeWidth;
        private final boolean dashed;

        private RelationTypeKey(Color color, float strokeWidth, boolean dashed) {
            this.color = color;
            this.strokeWidth = strokeWidth;
            this.dashed = dashed;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            RelationTypeKey that = (RelationTypeKey) o;
            return Float.compare(that.strokeWidth, strokeWidth) == 0 &&
                    dashed == that.dashed &&
                    Objects.equals(color, that.color);
        }

        @Override
        public int hashCode() {
            return Objects.hash(color, strokeWidth, dashed);
        }
    }
}
