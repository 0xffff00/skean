package party.threebody.skean.core;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 
 * @author hzk
 * @since 2017-07-09
 * 
 */
public class VagueTime {

	private final LocalDateTime begin; // included
	private final LocalDateTime end; // excluded
	private final TimeGranularity timeGranularity;

	static final String RE_YYYY = "\\d{4}";
	static final String RE_YYYY_QQ = "\\d{4}Q\\d";
	static final String RE_YYYY_MM = "\\d{4}-\\d{2}";
	static final String RE_YYYY_MM_DD = "\\d{4}-\\d{2}-\\d{2}";

	static final DateTimeFormatter DATETIME_FORMAT_CH19 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	/**
	 * Invalid value
	 */
	public static VagueTime NA = of(null, null);

	public static VagueTime of(int year) {
		LocalDateTime dt = LocalDateTime.of(year, 1, 1, 0, 0, 0);
		return new VagueTime(dt, TimeGranularity.YEAR);
	}

	public static VagueTime of(int year, int month) {
		LocalDateTime dt = LocalDateTime.of(year, month, 1, 0, 0, 0);
		return new VagueTime(dt, TimeGranularity.MONTH);
	}

	public static VagueTime of(int year, int month, int dayOfMonth) {
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, 0, 0, 0);
		return new VagueTime(dt, TimeGranularity.DAY);
	}

	public static VagueTime of(int year, int month, int dayOfMonth, int hour, int minute, int second) {
		LocalDateTime dt = LocalDateTime.of(year, month, dayOfMonth, hour, minute, second);
		return new VagueTime(dt, TimeGranularity.SECOND);
	}

	public static VagueTime of(String text) {
		int len = text.length();
		if (len == 4) {
			if (text.matches(RE_YYYY)) {
				int y = Integer.valueOf(text.substring(0, 4));
				return new VagueTime(LocalDateTime.of(y, 1, 1, 0, 0, 0), TimeGranularity.YEAR);
			}
		} else if (len == 6) {
			if (text.matches(RE_YYYY_QQ)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int q = Integer.valueOf(text.substring(5, 6));
				return new VagueTime(LocalDateTime.of(y, q * 3 - 2, 1, 0, 0, 0), TimeGranularity.QUARTER);
			}
		} else if (len == 7) {
			if (text.matches(RE_YYYY_MM)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int m = Integer.valueOf(text.substring(5, 7));
				return new VagueTime(LocalDateTime.of(y, m, 1, 0, 0, 0), TimeGranularity.MONTH);
			}
		} else if (len == 10) {
			if (text.matches(RE_YYYY_MM_DD)) {
				int y = Integer.valueOf(text.substring(0, 4));
				int m = Integer.valueOf(text.substring(5, 7));
				int d = Integer.valueOf(text.substring(8, 10));
				return new VagueTime(LocalDateTime.of(y, m, d, 0, 0, 0), TimeGranularity.DAY);
			}
		} else if (len == 19) {
			try {
				LocalDateTime t = LocalDateTime.parse(text, DATETIME_FORMAT_CH19);
				return new VagueTime(t, TimeGranularity.SECOND);
			} catch (DateTimeParseException e) {
				return VagueTime.NA;

			}
		}
		return VagueTime.NA;
	}

	public static VagueTime of(LocalDateTime begin, LocalDateTime end) {
		return new VagueTime(begin, end, TimeGranularity.OTHER);
	}

	private VagueTime(LocalDateTime begin, LocalDateTime end, TimeGranularity timeGran) {
		this.begin = begin;
		this.end = end;
		this.timeGranularity = timeGran;
	}

	private VagueTime(LocalDateTime _begin, TimeGranularity _timeGran) {
		switch (_timeGran) {

		case YEAR:
			begin = LocalDateTime.of(_begin.getYear(), 1, 1, 0, 0, 0);
			end = LocalDateTime.of(_begin.getYear() + 1, 1, 1, 0, 0, 0);
			break;
		case QUARTER:
			begin = LocalDateTime.of(_begin.getYear(), _begin.getMonth(), 1, 0, 0, 0);
			end = begin.plusMonths(3);
			break;

		case MONTH:
			begin = LocalDateTime.of(_begin.getYear(), _begin.getMonth(), 1, 0, 0, 0);
			end = begin.plusMonths(1);
			break;
		case DAY:
			begin = LocalDateTime.of(_begin.toLocalDate(), LocalTime.MIN);
			end = begin.plusDays(1);
			break;
		case SECOND:
			begin = _begin;
			end = _begin.plusSeconds(1);
			break;
		default:
			throw new RuntimeException("not support yet.");
		}

		timeGranularity = _timeGran;
	}

	public LocalDateTime getBegin() {
		return begin;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public TimeGranularity getTimeGranularity() {
		return timeGranularity;
	}

	enum TimeGranularity {

		CENTURY(3652400), DECADE(365200), YEAR(36500), QUARTER(9100), MONTH(3000), XUN(1000), // 上中下旬
		WEEK(700), DAY(100), HOUR(9), MINITE(8), SECOND(7), NANO(5), BELOW_NANO(2), OTHER(-1);
		private int seq;

		private TimeGranularity(int seq) {
			this.setSeq(seq);
		}

		public int getSeq() {
			return seq;
		}

		public void setSeq(int seq) {
			this.seq = seq;
		}
	}
}
