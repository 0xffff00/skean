package party.threebody.skean.mvc.generic;

/**
 * an immutable class.
 * 
 * @author hzk
 * @since 2017-08-24
 */
public final class AffectCount {
	protected final int numCreated, numUpdated, numDeleted;
	protected final int numFailed;

	public static final AffectCount NOTHING = new AffectCount(0, 0, 0);
	public static final AffectCount ONE_CREATED = new AffectCount(1, 0, 0);
	public static final AffectCount ONE_UPDATED = new AffectCount(0, 1, 0);

	public static AffectCount ofOnlyCreated(int numCreated) {
		return new AffectCount(numCreated, 0, 0);
	}

	public static AffectCount ofOnlyUpdated(int numUpdated) {
		return new AffectCount(0, numUpdated, 0);
	}

	public AffectCount(int numCreated, int numUpdated, int numDeleted) {
		this(numCreated, numUpdated, numDeleted, 0);
	}

	public AffectCount(int numCreated, int numUpdated, int numDeleted, int numFailed) {
		super();
		this.numCreated = numCreated;
		this.numUpdated = numUpdated;
		this.numDeleted = numDeleted;
		this.numFailed = numFailed;
	}

	public static AffectCount add(AffectCount one, AffectCount another) {
		return new AffectCount(one.numCreated + another.numCreated, one.numUpdated + another.numUpdated,
				one.numDeleted + another.numDeleted, one.numFailed + another.numFailed);
	}

	@Override
	public String toString() {
		return genNumText(numCreated) + " created, " + genNumText(numUpdated) + " updated, " + genNumText(numCreated)
				+ " deleted. " + (numFailed > 0 ? ("But " + genNumText(numFailed) + " failed.") : "");
	}

	private static String genNumText(int num) {
		return (num < 2) ? num + " item" : num + " items";

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

}