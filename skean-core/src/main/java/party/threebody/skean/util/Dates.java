package party.threebody.skean.util;

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class Dates {
	private Dates() {
	}

	public static String format() {
		return format(LocalDateTime.now());
	}

	public static String format(LocalDateTime ldt) {
		return ldt.format(DateTimeFormatters.DEFAULT);
	}

	public static java.util.Date toDate(LocalDateTime ldt) {
		if (ldt == null) {
			return null;
		}
		return java.util.Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDateTime toLocalDateTime(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

}