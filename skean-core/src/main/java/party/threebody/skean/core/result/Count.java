package party.threebody.skean.core.result;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.time.DurationFormatUtils;
import party.threebody.skean.lang.Strings;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Count {

    protected String entityName;
    protected String entitiesName;

    Map<String, Integer> cntMap;    //action -> count
    protected long millisUsed;


    protected Count(String entityName, String entitiesName, Map<String, Integer> cntMap, long millisUsed) {
        this.entityName = entityName;
        this.entitiesName = entitiesName;
        this.cntMap = cntMap;
        this.millisUsed = millisUsed;
    }

    protected Count() {
        cntMap = new HashMap<>(8);
    }

    public String getEntityName() {
        return (entityName == null) ? "item" : entityName;
    }

    @JsonIgnore
    public String getEntitiesName() {
        return (entitiesName == null) ? getEntityName() + "s" : entitiesName;
    }

    public long countMillisUsed() {
        return millisUsed;
    }


    public Map<String, Integer> getCounts() {
        return cntMap;
    }

    public long getMillisUsed() {
        return millisUsed;
    }

    //------ builders ------
    public Count since(long millisStarting) {
        this.millisUsed = System.currentTimeMillis() - millisStarting;
        return this;
    }

    public Count since(LocalDateTime timeStarting) {
        this.millisUsed = ChronoUnit.MILLIS.between(timeStarting, LocalDateTime.now());
        return this;
    }

    public Count append(String action, int num) {
        if (cntMap.containsKey(action)) {
            cntMap.put(action, cntMap.get(action) + num);
        } else {
            cntMap.put(action, num);
        }
        return this;
    }

    public Count append(Count another) {
        another.cntMap.keySet().forEach(action -> {
            int sum = this.cntMap.get(action) + another.cntMap.get(action);
            this.cntMap.put(action, sum);
        });
        return this;
    }

    public static Count add(Count one, Count another) {
        Map<String, Integer> cntMap3 = new HashMap<>(one.cntMap);
        cntMap3.putAll(another.cntMap);
        return new Count(one.entityName, one.entitiesName, cntMap3, one.millisUsed + another.millisUsed);
    }


    //------builders (grammar candy) ------

    public Count created(int num) {
        return append("created", num);
    }

    public Count updated(int num) {
        return append("updated", num);
    }

    public Count deleted(int num) {
        return append("deleted", num);
    }

    public Count completed(int num) {
        return append("completed", num);
    }

    public Count failed(int num) {
        return append("failed", num);
    }

    public Count skipped(int num) {
        return append("skipped", num);
    }

    public Count createdOne() {
        return created(1);
    }

    public Count updatedOne() {
        return updated(1);
    }

    public Count completedOne() {
        return completed(1);
    }

    public Count deletedOne() {
        return deleted(1);
    }

    public Count failedOne() {
        return failed(1);
    }

    public Count skippedOne() {
        return skipped(1);
    }

    //----- toString & summaries -------


    protected String summarizeAction(int cnt, String whatHappened) {
        return cnt + " " + (cnt < 2 ? getEntityName() : getEntitiesName()) + " " + whatHappened + "; ";
    }

    public String getSummary() {
        String[] clauses = cntMap.keySet().stream()
                .map(action -> summarizeAction(cntMap.get(action), action))
                .toArray(String[]::new);
        return Strings.joinIgnoreEmpty(", ", clauses) + ". "
                + (millisUsed > 0 ? (DurationFormatUtils.formatDurationHMS(millisUsed) + " used.") : "");
    }

    @Override
    public String toString() {
        return getSummary();
    }

}

