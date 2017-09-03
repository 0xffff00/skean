package party.threebody.herd.domain

import java.time.LocalDateTime

class RepoLogItem {
    LocalDateTime actionTime
    String actionType
    String entityKey
    String entityVal
    String result
    String desc

    RepoLogItem() {
    }

    RepoLogItem(LocalDateTime actionTime, String actionType, String entityKey, String entityVal, String result, String desc) {
        this.actionTime = actionTime
        this.actionType = actionType
        this.entityKey = entityKey
        this.entityVal = entityVal
        this.result = result
        this.desc = desc
    }


}
