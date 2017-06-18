package tmp.experiment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class Log4j2Learn {

	public static void main(String[] args) {
		Marker fatal = MarkerFactory.getMarker("FATAL");

		Logger logA = LoggerFactory.getLogger("A");
		logA.trace("tttrrrr");
		logA.debug("ddddd");
		logA.warn("wwwwwwa");
		logA.info("iiiiiii");
		logA.error("eeerrrr");
		logA.error(fatal, "Failed to obtain qqqqq connection");

		Logger logHa1 = LoggerFactory.getLogger("party.threebody.webs4g.tmp.HAHA");
		logHa1.info("ssdlsdfweioioiweoweoie");
		// failMeth1();
		FailMeths.s1();
		FailMeths.m2a();
		
	}

}

class FailMeths {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	static void s1() {
		System.out.println(ANSI_YELLOW_BACKGROUND + "This text has a green background but default text!" + ANSI_RESET);
		System.out.println(ANSI_RED + "This text has red text but a default background!" + ANSI_RESET);
		System.out.println(ANSI_BLUE_BACKGROUND + ANSI_WHITE + "This text has a green back and red text!" + ANSI_RESET);
	}

	static int failMeth1() {
		int x = 4;
		int y = x / 0;
		return x + y;
	}

	static void m2a() {
		m2b();
	}

	static void m2b() {
		m2c();
	}

	static void m2c() {
		m2d();
	}

	static void m2d() {
		m2e();
	}

	static void m2e() {
		m2f();
	}

	static int m2f() {
		throw new RuntimeException("gffdeeeeeeeeeeexxxxxx");
	}
}
