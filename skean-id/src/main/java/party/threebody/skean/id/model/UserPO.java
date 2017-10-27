/*
 * Copyright 2017-present  Skean Project Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

