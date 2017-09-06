package party.threebody.herd.domain

import java.time.LocalDateTime

class Media {
    String hash
    String type
    String subtype
    String desc
    Integer size
    LocalDateTime syncTime

    //DTO fields
    List<MediaPath> paths
    String path0Path;

    void setTypeAndSubtype(MediaTypeAndSubType tast){
        type=tast.type
        subtype=tast.subtype
    }

}
class MediaPath {
    String hash
    String path
    String type
    String repoName
    LocalDateTime syncTime

    MediaPath(){}

    MediaPath(String hash, String path, String type, String repoName, LocalDateTime syncTime) {
        this.hash = hash
        this.path = path
        this.type = type
        this.repoName = repoName
        this.syncTime = syncTime
    }
}

class MediaTypeAndSubType {
    String type
    String subtype
}