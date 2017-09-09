package party.threebody.skean.util;

import java.time.format.DateTimeFormatter;

public class DateTimeFormatters {
	private DateTimeFormatters() {
	}

	public static final DateTimeFormatter DASHED_YEAR2MON = DateTimeFormatter.ofPattern("yyyy-MM");
	public static final DateTimeFormatter DASHED_YEAR2DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DASHED_YEAR2SEC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DASHED_YEAR2MILLIS = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	public static final DateTimeFormatter SLASHED_YEAR2MON = DateTimeFormatter.ofPattern("yyyy/MM");
	public static final DateTimeFormatter SLASHED_YEAR2DAY = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	public static final DateTimeFormatter SLASHED_YEAR2SEC = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	public static final DateTimeFormatter PURENUM_YEAR2SEC = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public static final DateTimeFormatter DEFAULT = DASHED_YEAR2SEC;
}
