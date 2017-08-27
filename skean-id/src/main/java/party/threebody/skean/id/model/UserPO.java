package party.threebody.skean.id.model;

import java.util.Date;
import java.util.List;

public class UserPO{
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName_disp() {
		return name_disp;
	}

	public void setName_disp(String name_disp) {
		this.name_disp = name_disp;
	}

	public String getName_full() {
		return name_full;
	}

	public void setName_full(String name_full) {
		this.name_full = name_full;
	}

	public String getPsd() {
		return psd;
	}

	public void setPsd(String psd) {
		this.psd = psd;
	}

	public Date getLop_time() {
		return lop_time;
	}

	public void setLop_time(Date lop_time) {
		this.lop_time = lop_time;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	private String name;
	private String name_disp;
	private String name_full;
	private String psd;
	private Date lop_time;
	private State state;
}

