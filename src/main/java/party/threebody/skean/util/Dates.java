package party.threebody.skean.util;

import java.time.LocalDateTime;

public final class Dates {
	private Dates() {
	}

	public String format() {
		return format(LocalDateTime.now());
	}

	public String format(LocalDateTime ldt) {
		return ldt.format(DateTimeFormatters.DEFAULT);
	}
}