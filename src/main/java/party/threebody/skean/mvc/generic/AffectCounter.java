package party.threebody.skean.mvc.generic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 
 * @author hzk
 * @since 2017-08-24
 */
public class AffectCounter {
	protected AtomicInteger numCreated, numUpdated, numDeleted;
	protected AtomicInteger numFailed;

	public AffectCounter() {
		numCreated = new AtomicInteger();
		numUpdated = new AtomicInteger();
		numDeleted = new AtomicInteger();
		numFailed = new AtomicInteger();
	}

	public AffectCount result() {
		return new AffectCount(numCreated.get(), numUpdated.get(), numDeleted.get(), numFailed.get());
	}

	public AffectCounter addAndGet(AffectCount another) {
		if (another == null) {
			return this;
		}
		this.numCreated.addAndGet(another.numCreated);
		this.numUpdated.addAndGet(another.numUpdated);
		this.numDeleted.addAndGet(another.numDeleted);
		this.numFailed.addAndGet(another.numFailed);
		return this;
	}

	public AffectCounter addAndGet(AffectCounter another) {
		if (another == null) {
			return this;
		}
		return addAndGet(another.result());

	}

}