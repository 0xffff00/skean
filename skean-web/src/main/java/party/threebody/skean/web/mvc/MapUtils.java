package party.threebody.skean.web.mvc;

import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MapUtils {

   public static Map<String, Object> toMap(MultiValueMap<String, String> mvmap) {
        return mvmap.keySet().stream().collect(Collectors.toMap(
                Function.identity(),
                key -> {
                    List<String> vals = mvmap.get(key);
                    if (vals == null || vals.size() == 0) {
                        return null;
                    } else if (vals.size() == 1) {
                        return vals.get(0);
                    } else {
                        return vals;
                    }
                }
        ));
    }

}
