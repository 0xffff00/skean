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

import java.time.format.DateTimeFormatter;

public class DateTimeFormatters {
	private DateTimeFormatters() {
	}

	public static final DateTimeFormatter DASHED_YEAR2MON = DateTimeFormatter.ofPattern("yyyy-MM");
	public static final DateTimeFormatter DASHED_YEAR2DAY = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	public static final DateTimeFormatter DASHED_YEAR2SEC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
	public static final DateTimeFormatter DASHED_YEAR2MILLIS = DateTimeFormatter
			.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
	public static final DateTimeFormatter SLASHED_YEAR2MON = DateTimeFormatter.ofPattern("yyyy/MM");
	public static final DateTimeFormatter SLASHED_YEAR2DAY = DateTimeFormatter.ofPattern("yyyy/MM/dd");
	public static final DateTimeFormatter SLASHED_YEAR2SEC = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
	public static final DateTimeFormatter PURENUM_YEAR2SEC = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

	public static final DateTimeFormatter DEFAULT = DASHED_YEAR2SEC;
}
