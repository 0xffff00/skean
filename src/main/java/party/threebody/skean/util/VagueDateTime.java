package party.threebody.skean.util;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * 
 * A vague date-time which granularity varies from <b>second</b> to
 * <b>year</b><br>
 * Mostly, VagueDateTime represents a range of [begin,end).<br>
 * Here is examples:<br>
 * <code>
 * <li>VagueDateTime.parse("2017Q3").begin().equals(LocalDateTime.parse("2017-07-01T00:00:00"));</li>
 * <li>VagueDateTime.parse("2017Q3").end().equals(LocalDateTime.parse("2017-10-01T00:00:00"));</li>
 * </code>
 * 
 * @see java.time.LocalDateTime
 * @implSpec This is a final, immutable class, an extension of
 *           <code>java.time</code> package<br>
 * 
 * @author hzk
 * @since 2017-07-09
 * 
 */
public class VagueDateTime {

	// a range [begin,end)
	private final LocalDateTime begin; // included
	private final LocalDateTime end; // excluded
	private final TemporalUnit granularity;

	private final String parsedText;

	// RegExps
	private static final String RE_YYYY = "\\d{4}";
	private static final String RE_YYYY_QQ = "\\d{4}Q\\d";
	private static final String RE_YYYY_MM = "\\d{4}-\\d{2}";
	private static final String RE_YYYY_MM_DD = "\\d{4}-\\d{2}-\\d{2}";

	// Invalid value
	public final static VagueDateTime NA = of(null, null);

	public static VagueDateTime of(long num) {

		return null;

	}

	public static VagueDateTime ofYear(int year) {
		LocalDateTime dt = LocalDateTime.of(year, 1, 1, 0, 0, 0);
		return new VagueDateTime(dt, ChronoUnit.YEARS);
	}

	public static VagueDateTime of(int year, int month) {
		LocalDateTime dt = LocalDateTime.of(year, month, 1, 0, 0, 0);
		return new VagueDateTime(dt, ChronoUnit.MONTHS);
	}

	public static VagueDateTime of(int year, int month, int dayOfMonth) {
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
		return new VagueDateTime(dt, ChronoUnit.DAYS);
	}

	public static VagueDateTime of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
		return new VagueDateTime(dt, ChronoUnit.SECONDS);
	}

	public static VagueDateTime of(LocalDateTime begin, LocalDateTime end) {
		return new VagueDateTime(begin, end, null, null);
	}

	public static VagueDateTime parse(String text) {
		int len = text.length();
		if (len == 4) {
			if (text.matches(RE_YYYY)) {
				int y = Integer.valueOf(text.substring(0, 4));
				return new VagueDateTime(LocalDateTime.of(y, 1, 1, 0, 0, 0), ChronoUnit.YEARS);
			}
		} else if (len == 6) {
			if (text.matches(RE_YYYY_QQ)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int q = Integer.valueOf(text.substring(5, 6));
				return new VagueDateTime(LocalDateTime.of(y, q * 3 - 2, 1, 0, 0, 0), ChronoUnitX.QUARTERS);
			}
		} else if (len == 7) {
			if (text.matches(RE_YYYY_MM)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int m = Integer.valueOf(text.substring(5, 7));
				return new VagueDateTime(LocalDateTime.of(y, m, 1, 0, 0, 0), ChronoUnit.MONTHS);
			}
		} else if (len == 10) {
			if (text.matches(RE_YYYY_MM_DD)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int m = Integer.valueOf(text.substring(5, 7));
				int d = Integer.valueOf(text.substring(8, 10));
				return new VagueDateTime(LocalDateTime.of(y, m, d, 0, 0, 0), ChronoUnit.DAYS);
			}
		} else if (len == 19) {
			try {
				LocalDateTime t = LocalDateTime.parse(text, DateTimeFormatters.DASHED_YEAR2SEC);
				return new VagueDateTime(t, ChronoUnit.SECONDS);
			} catch (DateTimeParseException e) {
				return VagueDateTime.NA;

			}
		}
		return VagueDateTime.NA;
	}

	private VagueDateTime(LocalDateTime begin, LocalDateTime end, TemporalUnit granularity, String text) {
		this.begin = begin;
		this.end = end;
		this.granularity = granularity;
		this.parsedText = text;
	}

	private VagueDateTime(LocalDateTime _begin, TemporalUnit granularity) {
		this(_begin, granularity, null);
	}

	private VagueDateTime(LocalDateTime _begin, TemporalUnit granularity, String text) {
		if (granularity instanceof ChronoUnit) {
			switch ((ChronoUnit) granularity) {

			case YEARS:
				begin = LocalDateTime.of(_begin.getYear(), 1, 1, 0, 0, 0);
				end = LocalDateTime.of(_begin.getYear() + 1, 1, 1, 0, 0, 0);
				break;
			case MONTHS:
				begin = LocalDateTime.of(_begin.getYear(), _begin.getMonth(), 1, 0, 0, 0);
				end = begin.plusMonths(1);
				break;
			case DAYS:
				begin = LocalDateTime.of(_begin.toLocalDate(), LocalTime.MIN);
				end = begin.plusDays(1);
				break;
			case SECONDS:
				begin = _begin;
				end = _begin.plusSeconds(1);
				break;
			default:
				throw new RuntimeException("not support yet.");
			}
		} else if (granularity instanceof ChronoUnitX) {
			switch ((ChronoUnitX) granularity) {
			case QUARTERS:
				begin = LocalDateTime.of(_begin.getYear(), _begin.getMonth(), 1, 0, 0, 0);
				end = begin.plusMonths(3);
				break;
			default:
				throw new RuntimeException("not support yet.");
			}
		} else {
			throw new RuntimeException("not support yet.");
		}
		this.granularity = granularity;
		this.parsedText = text;
	}

	/**
	 * get range lower bound included
	 * 
	 * @return
	 */
	public LocalDateTime begin() {
		return begin;
	}

	/**
	 * get range upper bound excluded
	 * 
	 * @return
	 */
	public LocalDateTime end() {
		return end;
	}

	public TemporalUnit getTimeGranularity() {
		return granularity;
	}

	public boolean contains(LocalDateTime dateTime) {
		return !dateTime.isBefore(begin) && dateTime.isBefore(end);
	}

	public long difference(TemporalUnit cu) {
		return begin.until(end, cu);
	}

	public LocalDateTime average() {
		long nanos = difference(ChronoUnit.NANOS);
		return begin.plusNanos(nanos / 2);
	}

	@Override
	public String toString() {
		if (parsedText != null) {
			return parsedText;
		}
		if (granularity instanceof ChronoUnit) {
			switch ((ChronoUnit) granularity) {
			case YEARS:
				return String.valueOf(begin.getYear());
			case MONTHS:
				return begin.format(DateTimeFormatters.DASHED_YEAR2MON);
			case DAYS:
				return begin.format(DateTimeFormatters.DASHED_YEAR2DAY);
			case SECONDS:
				return begin.format(DateTimeFormatters.DASHED_YEAR2SEC);
			default:
				// NO-OP
			}
		} else if (granularity instanceof ChronoUnitX) {
			switch ((ChronoUnitX) granularity) {
			case QUARTERS:
				int q = (begin.getMonthValue() - 1) / 3 + 1;
				return begin.getYear() + "Q" + q;
			default:
				// NO-OP
			}
		}
		return begin.format(DateTimeFormatters.DEFAULT) + "~" + end.format(DateTimeFormatters.DEFAULT);
	}

	@Override
	public int hashCode() {
		return begin.hashCode() ^ end.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof VagueDateTime) {
			VagueDateTime other = (VagueDateTime) obj;
			return begin.equals(other.begin) && end.equals(other.end);
		}
		return false;
	}

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

}
