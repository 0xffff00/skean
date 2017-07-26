package party.threebody.skean.util;

public final class Strings {
	private Strings() {
	}

	public static String repeat(String src, int times) {
		return new String(new char[times]).replace("\0", src);
	}
}
