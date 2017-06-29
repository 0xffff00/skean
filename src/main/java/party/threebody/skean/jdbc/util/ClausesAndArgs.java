package party.threebody.skean.jdbc.util;

public class ClausesAndArgs {
	private String[] clauses;
	private Object[] args;

	public ClausesAndArgs(String[] clauses, Object[] args) {
		this.clauses = clauses;
		this.args = args;
	}

	public String[] getClauses() {
		return clauses;
	}

	public String getClause(int i) {
		return clauses[i];
	}

	public Object[] getArgs() {
		return args;
	}

}