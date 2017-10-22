package party.threebody.skean.web.eg.navyapp.domain;

import javax.persistence.Id;

public class Ship0 {
	private String code, name;
	private Integer weig;
	private Integer birthYear;

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

	public Integer getWeig() {
		return weig;
	}

	public void setWeig(Integer weig) {
		this.weig = weig;
	}

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}
}