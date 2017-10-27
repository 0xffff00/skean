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