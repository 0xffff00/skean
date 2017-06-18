package party.threebody.skean.jdbc.phrase;

public class SqlSecurityUtils {
	/**
	 * check column name or table name legality.<br>
	 * Helping prevent persistence layer from SQL injection attack. 
	 * @param name
	 * @return legal or illegal
	 */
	public static boolean checkNameLegality(String name) {
		// TODO let simple function wrapper pass. eg. upper(col1)
		if (name==null) return false;
		return name.matches("(`\\w+`|\\w+)");
	}
	/**
	 * pass 'col1 desc','col1 asc','col1'
	 * @param name
	 * @return
	 */
	public static boolean checkColsOrderByLegality(String name) {
		
		if (name==null) return false;
		return name.matches("(`\\w+`|\\w+)( (desc|asc|DESC|ASC))?");
	}
}
