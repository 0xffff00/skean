package party.threebody.skean.id;

import java.time.LocalTime;

public class UserPO {

	String name;
	String name_disp;
	String name_full;
	String psd; // Login Password
	LocalTime lop_time;
	char state; // suspended停用,terminated注销,active活动,inactive未激活的（新的）

}
