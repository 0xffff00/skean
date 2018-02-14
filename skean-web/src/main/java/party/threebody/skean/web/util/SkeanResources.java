package party.threebody.skean.web.util;

import com.fasterxml.jackson.core.type.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;
import party.threebody.skean.lang.ObjectMappers;

import java.io.File;
import java.io.IOException;

/**
 * @see org.springframework.util.ResourceUtils
 */
public final class SkeanResources {
    static final Logger logger = LoggerFactory.getLogger(SkeanResources.class);

    public static <T> T readValueFromLocalJsonFile(String jsonFilePath, TypeReference<T> typeReference) {
        try {
            File file = ResourceUtils.getFile(jsonFilePath);
            T result = ObjectMappers.DEFAULT.readValue(file, typeReference);
            return result;
        } catch (IOException e) {
            logger.info(e.getMessage(), e);
            return null;
        }
    }
}
