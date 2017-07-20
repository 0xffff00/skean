package party.threebody.skean.core;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * 
 * A vague time which granularity varies from <b>second</b> to <b>year</b><br>
 * Mostly, VagueTime represents a range of [begin,end).<br>
 * Here is examples:<br>
 * <code>
 * <li>VagueTime.parse("2017Q3").begin().equals(LocalDateTime.parse("2017-07-01T00:00:00"));</li>
 * <li>VagueTime.parse("2017Q3").end().equals(LocalDateTime.parse("2017-10-01T00:00:00"));</li>
 * </code>
 * 
 * @implSpec This is a final, immutable class, an extension of
 *           <code>java.time</code> package<br>
 * 
 * @author hzk
 * @since 2017-07-09
 * 
 */
public class VagueTime {

	private final LocalDateTime begin; // included
	private final LocalDateTime end; // excluded
	private final TemporalUnit granularity;

	private final String parsedText;

	static final String RE_YYYY = "\\d{4}";
	static final String RE_YYYY_QQ = "\\d{4}Q\\d";
	static final String RE_YYYY_MM = "\\d{4}-\\d{2}";
	static final String RE_YYYY_MM_DD = "\\d{4}-\\d{2}-\\d{2}";

	static final DateTimeFormatter DATETIME_FORMAT_YM = DateTimeFormatter.ofPattern("yyyy-MM");
	static final DateTimeFormatter DATETIME_FORMAT_YMD = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	static final DateTimeFormatter DATETIME_FORMAT_CH19 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	static final DateTimeFormatter DATETIME_FORMAT_CH23 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

	/**
	 * Invalid value
	 */
	public final static VagueTime NA = of(null, null);

	public static VagueTime of(int year) {
		LocalDateTime dt = LocalDateTime.of(year, 1, 1, 0, 0, 0);
		return new VagueTime(dt, ChronoUnit.YEARS);
	}

	public static VagueTime of(int year, int month) {
		LocalDateTime dt = LocalDateTime.of(year, month, 1, 0, 0, 0);
		return new VagueTime(dt, ChronoUnit.MONTHS);
	}

	public static VagueTime of(int year, int month, int dayOfMonth) {
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
		return new VagueTime(dt, ChronoUnit.DAYS);
	}

	public static VagueTime of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
		return new VagueTime(dt, ChronoUnit.SECONDS);
	}

	public static VagueTime of(LocalDateTime begin, LocalDateTime end) {
		return new VagueTime(begin, end, null, null);
	}

	public static VagueTime parse(String text) {
		int len = text.length();
		if (len == 4) {
			if (text.matches(RE_YYYY)) {
				int y = Integer.valueOf(text.substring(0, 4));
				return new VagueTime(LocalDateTime.of(y, 1, 1, 0, 0, 0), ChronoUnit.YEARS);
			}
		} else if (len == 6) {
			if (text.matches(RE_YYYY_QQ)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int q = Integer.valueOf(text.substring(5, 6));
				return new VagueTime(LocalDateTime.of(y, q * 3 - 2, 1, 0, 0, 0), ExtChronoUnit.QUARTERS);
			}
		} else if (len == 7) {
			if (text.matches(RE_YYYY_MM)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int m = Integer.valueOf(text.substring(5, 7));
				return new VagueTime(LocalDateTime.of(y, m, 1, 0, 0, 0), ChronoUnit.MONTHS);
			}
		} else if (len == 10) {
			if (text.matches(RE_YYYY_MM_DD)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int m = Integer.valueOf(text.substring(5, 7));
				int d = Integer.valueOf(text.substring(8, 10));
				return new VagueTime(LocalDateTime.of(y, m, d, 0, 0, 0), ChronoUnit.DAYS);
			}
		} else if (len == 19) {
			try {
				LocalDateTime t = LocalDateTime.parse(text, DATETIME_FORMAT_CH19);
				return new VagueTime(t, ChronoUnit.SECONDS);
			} catch (DateTimeParseException e) {
				return VagueTime.NA;

			}
		}
		return VagueTime.NA;
	}

	private VagueTime(LocalDateTime begin, LocalDateTime end, TemporalUnit granularity, String text) {
		this.begin = begin;
		this.end = end;
		this.granularity = granularity;
		this.parsedText = text;
	}

	private VagueTime(LocalDateTime _begin, TemporalUnit granularity) {
		this(_begin, granularity, null);
	}

	private VagueTime(LocalDateTime _begin, TemporalUnit granularity, String text) {
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
		} else if (granularity instanceof ExtChronoUnit) {
			switch ((ExtChronoUnit) granularity) {
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
				return begin.format(DATETIME_FORMAT_YM);
			case DAYS:
				return begin.format(DATETIME_FORMAT_YMD);
			case SECONDS:
				return begin.format(DATETIME_FORMAT_CH19);
			default:
				// NO-OP
			}
		} else if (granularity instanceof ExtChronoUnit) {
			switch ((ExtChronoUnit) granularity) {
			case QUARTERS:
				int q = (begin.getMonthValue() - 1) / 3 + 1;
				return begin.getYear() + "Q" + q;
			default:
				// NO-OP
			}
		}
		return begin.format(DATETIME_FORMAT_CH23) + "~" + end.format(DATETIME_FORMAT_CH23);
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
		if (obj instanceof VagueTime) {
			VagueTime other = (VagueTime) obj;
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
	enum ExtChronoUnit implements TemporalUnit {

		QUARTERS("Quarters", Duration.ofSeconds(31556952L / 4));

		private final String name;
		private final Duration duration;

		private ExtChronoUnit(String name, Duration estimatedDuration) {
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
