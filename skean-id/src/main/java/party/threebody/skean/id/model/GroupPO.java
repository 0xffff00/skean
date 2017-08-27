package party.threebody.skean.id.model;

import java.time.LocalTime;

public class GroupPO {
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public char getState() {
		return state;
	}

	public void setState(char state) {
		this.state = state;
	}

	public LocalTime getLop_time() {
		return lop_time;
	}

	public void setLop_time(LocalTime lop_time) {
		this.lop_time = lop_time;
	}

	private String code;
	private String name;
	private String abbr;
	private String type;
	private char state;
	private LocalTime lop_time;
}
