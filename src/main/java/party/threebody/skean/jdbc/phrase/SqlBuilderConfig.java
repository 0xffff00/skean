package party.threebody.skean.jdbc.phrase;

public class SqlBuilderConfig {
	private String subSelectIndent;
	private String dlmt;
	private String clauseDlmt;
	private String ln;
	private String enableBackquote;
	private String enableModifyAllRows;

	private void init(){
		setClauseDlmt(" \n");
		setSubSelectIndent("  ");
		setDlmt(" \n");
		setLn("\n");
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
		if (enableBackquote!=null && enableBackquote.equalsIgnoreCase("true")){
			return true;
		}
		return false;
	}

	public boolean isModifyAllRowsEnabled() {
		if (enableModifyAllRows!=null && enableModifyAllRows.equalsIgnoreCase("true")){
			return true;
		}
		return false;
	}
	
	public void setEnableBackquote(String enableBackquote) {
		this.enableBackquote = enableBackquote;
	}
	
	public void setEnableModifyAllRows(String enableModifyAllRows) {
		this.enableModifyAllRows = enableModifyAllRows;
	}
	

}