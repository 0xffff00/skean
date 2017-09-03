package party.threebody.skean.util;

import com.google.common.base.CaseFormat;

public final class StringCases {
	private StringCases() {
	}

	public static String camelToKebab(String text){
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_HYPHEN,text);
	}

	/**
	 *
	 * @param text a camelCase string
	 * @return a snake_case style string(understore)
	 */
	public static String camelToSnake(String text){
		return CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE,text);
	}

}
