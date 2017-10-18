package party.threebody.skean.jdbc.phrase;

public class SqlBuilderConfig {
	private String subSelectIndent;
	private String dlmt;
	private String clauseDlmt;
	private String ln;
	private boolean enableBackquote;
	private boolean enableModifyAllRows;
	private boolean enableConvertParamNameToSnakeCase;
	private int maxCharsOfInlineSelCols;

	private void init(){
		setClauseDlmt("\n");
		setSubSelectIndent("  ");
		setDlmt("\n");
		setLn("\n");
		setMaxCharsOfInlineSelCols(40);
		setEnableConvertParamNameToSnakeCase(true);
	}
	public SqlBuilderConfig() {
		init();
	}

	public String getClauseDlmt() {
		return clauseDlmt;
	}

	public void setClauseDlmt(String clauseDlmt) {
		this.clauseDlmt = clauseDlmt;
	}

	public String getSubSelectIndent() {
		return subSelectIndent;
	}

	public void setSubSelectIndent(String subSelectIndent) {
		this.subSelectIndent = subSelectIndent;
	}

	public String getDlmt() {
		return dlmt;
	}

	public void setDlmt(String dlmt) {
		this.dlmt = dlmt;
	}

	public String getLn() {
		return ln;
	}

	public void setLn(String ln) {
		this.ln = ln;
	}

	public String getNameQuote(){
		return isBackquoteEnabled()?"`":"";
	}
	public boolean isBackquoteEnabled() {		
		return enableBackquote;
	}

	public boolean isModifyAllRowsEnabled() {
		return enableModifyAllRows;
	}
	public void setEnableBackquote(String enableBackquote) {
		this.enableBackquote = "true".equalsIgnoreCase(enableBackquote);
	}
	public void setEnableBackquote(boolean enableBackquote) {
		this.enableBackquote = enableBackquote;
	}
	public void setEnableModifyAllRows(String enableModifyAllRows) {
		this.enableModifyAllRows ="true".equalsIgnoreCase(enableModifyAllRows); ;
	}
	public void setEnableModifyAllRows(boolean enableModifyAllRows) {
		this.enableModifyAllRows = enableModifyAllRows;
	}
	public int getMaxCharsOfInlineSelCols() {
		return maxCharsOfInlineSelCols;
	}
	public void setMaxCharsOfInlineSelCols(int maxCharsOfInlineSelCols) {
		this.maxCharsOfInlineSelCols = maxCharsOfInlineSelCols;
	}

	public boolean isConvertParamNameToSnakeCaseEnabled() {
		return enableConvertParamNameToSnakeCase;
	}

	public void setEnableConvertParamNameToSnakeCase(boolean enableConvertParamNameToSnakeCase) {
		this.enableConvertParamNameToSnakeCase = enableConvertParamNameToSnakeCase;
	}
	public void setEnableConvertParamNameToSnakeCase(String enableConvertParamNameToSnakeCase) {
		this.enableConvertParamNameToSnakeCase = "true".equalsIgnoreCase(enableConvertParamNameToSnakeCase);
	}
}