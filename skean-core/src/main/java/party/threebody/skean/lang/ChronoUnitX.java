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

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalUnit;

/**
 * extended ChronoUnit
 * 
 * @author hzk
 * @since 2017-07-20
 * @see java.time.temporal.ChronoUnit
 */
public enum ChronoUnitX implements TemporalUnit {

	QUARTERS("Quarters", Duration.ofSeconds(31556952L / 4));

	private final String name;
	private final Duration duration;

	private ChronoUnitX(String name, Duration estimatedDuration) {
		this.name = name;
		this.duration = estimatedDuration;
	}

	@Override
	public Duration getDuration() {
		return duration;
	}

	@Override
	public boolean isDurationEstimated() {
		return duration.compareTo(ChronoUnit.DAYS.getDuration()) >= 0;
	}

	@Override
	public boolean isDateBased() {
		return duration.compareTo(ChronoUnit.DAYS.getDuration()) >= 0;
	}

	@Override
	public boolean isTimeBased() {
		return duration.compareTo(ChronoUnit.DAYS.getDuration()) < 0;
	}

	@Override
	public boolean isSupportedBy(Temporal temporal) {
		return temporal.isSupported(this);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <R extends Temporal> R addTo(R temporal, long amount) {
		return (R) temporal.plus(amount, this);
	}

	@Override
	public long between(Temporal temporal1Inclusive, Temporal temporal2Exclusive) {
		return temporal1Inclusive.until(temporal2Exclusive, this);
	}

	@Override
	public String toString() {
		return name;
	}

}