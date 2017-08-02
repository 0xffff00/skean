package party.threebody.skean.util;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * extended ChronoUnit
 * 
 * @author hzk
 * @since 2017-07-20
 * @see java.time.temporal.ChronoUnit
 */
enum ChronoUnitX implements TemporalUnit {

	QUARTERS("Quarters", Duration.ofSeconds(31556952L / 4));

	private final String name;
	private final Duration duration;

	private ChronoUnitX(String name, Duration estimatedDuration) {
		this.name = name;
		this.duration = estimatedDuration;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public boolean isDurationEstimated() {
		return duration.compareTo(ChronoUnit.DAYS.getDuration()) >= 0;
	}

	@Override
	public boolean isDateBased() {
		return duration.compareTo(ChronoUnit.DAYS.getDuration()) >= 0;
	}

	@Override
	public boolean isTimeBased() {
		return duration.compareTo(ChronoUnit.DAYS.getDuration()) < 0;
	}

	@Override
	public boolean isSupportedBy(Temporal temporal) {
		return temporal.isSupported(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends Temporal> R addTo(R temporal, long amount) {
		return (R) temporal.plus(amount, this);
	}

	@Override
	public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
		return temporal1Inclusive.until(temporal2Exclusive, this);
	}

	@Override
	public String toString() {
		return name;
	}

}