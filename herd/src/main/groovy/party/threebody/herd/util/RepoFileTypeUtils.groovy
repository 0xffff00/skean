package party.threebody.herd.util

import org.apache.commons.io.FilenameUtils
import party.threebody.herd.domain.TypeAndSubType

import java.nio.file.Path

class RepoFileTypeUtils {

    private static final Map<String, String> IMAGE_EXTS = [
            "jpg" : "jpg",
            "jpeg": "jpg",
            "gif" : "gif",
            "png" : "png",
            "tiff": "tiff",
    ]

    static TypeAndSubType guessRepoFileTypeByPath(Path path) {
        def ext = FilenameUtils.getExtension(path.toString())
        def subtype = IMAGE_EXTS.get(ext.toLowerCase())
        if (subtype) {
            return new TypeAndSubType(type: "image", subtype: subtype)
        }

        return new TypeAndSubType(type: null, subtype: null)
    }

}


