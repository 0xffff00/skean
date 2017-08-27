package party.threebody.skean.mvc.generic;

import org.apache.commons.lang3.time.DurationFormatUtils;

/**
 * an immutable class.
 *
 * @author hzk
 * @since 2017-08-24
 */
public class AffectCount {
    protected final int numCreated, numUpdated, numDeleted;
    protected final int numFailed;
    protected final long millisUsed;

    public static final AffectCount NOTHING = new AffectCount(0, 0, 0);
    public static final AffectCount ONE_CREATED = new AffectCount(1, 0, 0);
    public static final AffectCount ONE_UPDATED = new AffectCount(0, 1, 0);
    public static final AffectCount ONE_DELETED = new AffectCount(0, 0, 1);
    public static final AffectCount ONE_FAILED = new AffectCount(0, 0, 0, 1, 0);

    public static AffectCount ofOnlyCreated(int numCreated) {
        return new AffectCount(numCreated, 0, 0);
    }

    public static AffectCount ofOnlyUpdated(int numUpdated) {
        return new AffectCount(0, numUpdated, 0);
    }

    public static AffectCount ofOnlyDeleted(int numDeleted) {
        return new AffectCount(0, 0, numDeleted);
    }

    public AffectCount() {
        this(0, 0, 0);
    }

    public AffectCount(int numCreated, int numUpdated, int numDeleted) {
        this(numCreated, numUpdated, numDeleted, 0, 0);
    }

    public AffectCount(int numCreated, int numUpdated, int numDeleted, int numFailed, long millisUsed) {
        super();
        this.numCreated = numCreated;
        this.numUpdated = numUpdated;
        this.numDeleted = numDeleted;
        this.numFailed = numFailed;
        this.millisUsed = millisUsed;
    }

    public AffectCount tillNow(long millisStarting) {
        return new AffectCount(numCreated, numUpdated, numDeleted, numFailed, System.currentTimeMillis() - millisStarting);
    }

    public AffectCount withMillisUsed(long millisUsed) {
        return new AffectCount(numCreated, numUpdated, numDeleted, numFailed, millisUsed);
    }

    public AffectCount add(AffectCount another) {
        return new AffectCount(this.numCreated + another.numCreated, this.numUpdated + another.numUpdated,
                this.numDeleted + another.numDeleted, this.numFailed + another.numFailed, this.millisUsed + another.millisUsed);
    }

    public int total() {
        return numCreated + numDeleted + numUpdated + numFailed;
    }

    @Override
    public String toString() {
        return toString("item", "items");
    }

    public String toString(String nameOfItem) {
        return toString(nameOfItem, nameOfItem + "s");
    }

    public String toString(String nameOfItem, String nameOfItems) {
        return String.join(", ",
                genNumText0(numCreated, "created", nameOfItem, nameOfItems),
                genNumText0(numUpdated, "updated", nameOfItem, nameOfItems),
                genNumText0(numDeleted, "deleted", nameOfItem, nameOfItems),
                genNumText0(numFailed, "failed", nameOfItem, nameOfItems)
        ) + ". "
                + (millisUsed > 0 ? (DurationFormatUtils.formatDurationHMS(millisUsed) + " used.") : "");

    }

    private static String genNumText0(int num, String whatHappened, String nameOfItem, String nameOfItems) {
        return (num == 0) ? null : genNumText0(num, whatHappened, nameOfItem, nameOfItems);
    }

    private static String genNumText(int num, String whatHappened, String nameOfItem, String nameOfItems) {
        return (num < 2) ? num + " " + nameOfItem + whatHappened: num + " " + nameOfItems + " " + whatHappened;
    }

    public int getNumCreated() {
        return numCreated;
    }

    public int getNumUpdated() {
        return numUpdated;
    }

    public int getNumDeleted() {
        return numDeleted;
    }

    public int getNumFailed() {
        return numFailed;
    }

    public long getMillisUsed() {
        return millisUsed;
    }
}