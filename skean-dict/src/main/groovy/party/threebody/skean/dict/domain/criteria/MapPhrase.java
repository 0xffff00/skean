package party.threebody.skean.dict.domain.criteria;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class MapPhrase implements WordCriteriaPhrase {
    private Type type;
    private String arg;

    public enum Type {
        ref(4), attr(7),
        subOf(9), instanceOf(8), subsetOf(3), subtopicOf(3),
        superOf(3), definitionOf(2), supersetOf(2), supertopicOf(2);
        private int cost;

        Type(int cost) {
            this.cost = cost;
        }

        public int getCost() {
            return cost;
        }

        static List<Type> arrangeFromFewestCost =
                Stream.of(Type.values()).sorted(Comparator.comparingInt(Type::getCost)).collect(toList());
    }

    public Type getType() {
        return type;
    }

    public String getArg() {
        return arg;
    }
}
