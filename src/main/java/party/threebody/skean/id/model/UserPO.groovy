package party.threebody.skean.id.model;

import java.time.LocalTime;


class UserPO {

	String name
	String name_disp
	String name_full
	String psd // Login Password
	Date lop_time
	State state // suspended停用,terminated注销,active活动,inactive未激活的（新的）

//	public void setState(char state) {
//		this.state = State.valueOf(state);
//	}
	
	
}

class User extends UserPO{
	List<Role> roles
}



class Perm {
	String name
	char state
}

