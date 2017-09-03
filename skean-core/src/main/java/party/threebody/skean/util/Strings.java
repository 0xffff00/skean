package party.threebody.skean.util;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Strings {
	private Strings() {
	}

	public static String repeat(String src, int times) {
		return new String(new char[times]).replace("\0", src);
	}

	public static String joinIgnoreEmpty(String delimiter,String... clauses){
		List<String> filtered=Stream.of(clauses).filter(StringUtils::isNotEmpty).collect(Collectors.toList());

		return String.join(delimiter,filtered);
	}
}
