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

import java.time.LocalDateTime;
import java.time.ZoneId;

public final class Dates {
	private Dates() {
	}

	public static String format() {
		return format(LocalDateTime.now());
	}

	public static String format(LocalDateTime ldt) {
		return ldt.format(DateTimeFormatters.DEFAULT);
	}

	public static java.util.Date toDate(LocalDateTime ldt) {
		if (ldt == null) {
			return null;
		}
		return java.util.Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	public static LocalDateTime toLocalDateTime(java.util.Date date) {
		if (date == null) {
			return null;
		}
		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}

}