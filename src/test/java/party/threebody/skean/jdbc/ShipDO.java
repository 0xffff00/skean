package party.threebody.skean.jdbc;

public class ShipDO {
	private String code, name;
	private int weig, birth;

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

	public int getWeig() {
		return weig;
	}

	public void setWeig(int weig) {
		this.weig = weig;
	}

	public int getBirth() {
		return birth;
	}

	public void setBirth(int birth) {
		this.birth = birth;
	}

}