package party.threebody.skean.dict.domain.criteria;

import com.fasterxml.jackson.core.type.TypeReference;
import party.threebody.skean.lang.ObjectMappers;
import party.threebody.skean.misc.SkeanException;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class CritTrees {

    public static List<CritTreeNode> fromJson(String json) {
        if (json == null) {
            return Collections.emptyList();
        }
        try {
            return ObjectMappers.DEFAULT.readValue(json, new TypeReference<List<CritTreeNode>>() {
            });
        } catch (IOException e) {
            throw new SkeanException(e.getMessage(), e);
        }
    }
}
