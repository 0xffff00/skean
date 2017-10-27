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

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum State {
	SUSPENDED('S'), TERMINATED('T'), ACTIVE('A'), INACTIVE('I');
	private char code;
	private static final Map<Character, State> codeMap = Arrays.stream(State.values())
			.collect(Collectors.toMap(x -> x.code, x -> x));

	private State(char code) {
		this.code = code;
	}

	public static State valueOf(char code){
		return codeMap.get(code);
	}
}