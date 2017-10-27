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

package party.threebody.skean.lang;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class Strings {
	private Strings() {
	}

	public static String repeat(String src, int times) {
		return new String(new char[times]).replace("\0", src);
	}

	public static String joinIgnoreEmpty(String delimiter,String... clauses){
		List<String> filtered=Stream.of(clauses).filter(StringUtils::isNotEmpty).collect(Collectors.toList());

		return String.join(delimiter,filtered);
	}

	private static final Pattern RE_LEGAL_ID_NAME=Pattern.compile("^[A-Za-z_][A-Za-z0-9_]*$");
	private static boolean isLegalIdentifierName(String name){
		return RE_LEGAL_ID_NAME.matcher(name).find();
	}
}
