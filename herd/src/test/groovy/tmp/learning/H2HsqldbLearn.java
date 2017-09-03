package tmp.learning;

import java.sql.SQLException;

public class H2HsqldbLearn {

	public static void main(String[] args) {
		//org.hsqldb.util.DatabaseManagerSwing.main(new String[] { "--url", "jdbc:hsqldb:mem:testdb", "--noexit" });
		try {
			org.h2.tools.Server.createWebServer("-web").start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
