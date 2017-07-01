package party.threebody.skean.jdbc.util;

import java.util.Arrays;

public class ClauseAndArgs {
	private String clause;
	private Object[] args;

	public ClauseAndArgs(String clause, Object[] args) {
		this.clause = clause;
		this.args = args;
	}

	@Override
	public String toString() {
		return "{clause:" + clause + ", args:" + Arrays.toString(args) + "}";
	}

	public String getClause() {
		return clause;
	}

	public Object[] getArgs() {
		return args;
	}
	
	

}