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

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;

/**
 * <p>
 * A vague date which granularity varies from <b>date</b> to <b>years</b>
 * <p>
 * <p>
 * Mostly, VagueDate represents a time point that cannot be remembered precisely
 * but only approximated into a range of [begin,end).
 * </p>
 * <p>
 * VagueDate is built based on <code>java.time</code> Classes.
 * </p>
 * <p>
 * Here is examples:<br>
 * <p>
 * <pre>
 * </pre>
 *
 * @author hzk
 * @implSpec This is a final, immutable class, an extension of
 * <code>java.time</code> package<br>
 * @since 2017-07-26
 */
public class VagueDate {

    // a range [begin,end)
    private final LocalDate begin; // included
    private final LocalDate end; // excluded
    private final TemporalUnit granularity;

    private final String parsedText;

    // RegExps
    private static final String RE_YYYY = "\\d{4}";
    private static final String RE_YYYY_QQ = "\\d{4}Q\\d";
    private static final String RE_YYYY_MM = "\\d{4}-\\d{2}";
    private static final String RE_YYYY_MM_DD = "\\d{4}-\\d{2}-\\d{2}";

    // Invalid value
    public final static VagueDate NA = of(null, null);

    /**
     * <h4>Use Cases:</h4>
     * <p>
     * <pre>
     * of(20170000
     * dsf
     * dsfds
     *
     * </pre>
     *
     * @param num
     * @return
     */
    public static VagueDate of(long num) {

        return null;

    }

    public static VagueDate ofYear(int year) {
        LocalDate dt = LocalDate.of(year, 1, 1);
        return new VagueDate(dt, ChronoUnit.YEARS);
    }

    public static VagueDate of(int year, int month) {
        LocalDate dt = LocalDate.of(year, month, 1);
        return new VagueDate(dt, ChronoUnit.MONTHS);
    }

    public static VagueDate of(int year, int month, int dayOfMonth) {
        LocalDate dt = LocalDate.of(year, month, dayOfMonth);
        return new VagueDate(dt, ChronoUnit.DAYS);
    }


    public static VagueDate of(LocalDate begin, LocalDate end) {
        return new VagueDate(begin, end, null, null);
    }

    public static VagueDate parse(String text) {
        int len = text.length();
        if (len == 4) {
            if (text.matches(RE_YYYY)) {
                int y = Integer.valueOf(text.substring(0, 4));
                return new VagueDate(LocalDate.of(y, 1, 1), ChronoUnit.YEARS);
            }
        } else if (len == 6) {
            if (text.matches(RE_YYYY_QQ)) {
                int y = Integer.valueOf(text.substring(0, 4));
                int q = Integer.valueOf(text.substring(5, 6));
                return new VagueDate(LocalDate.of(y, q * 3 - 2, 1), ChronoUnitX.QUARTERS);
            }
        } else if (len == 7) {
            if (text.matches(RE_YYYY_MM)) {
                int y = Integer.valueOf(text.substring(0, 4));
                int m = Integer.valueOf(text.substring(5, 7));
                return new VagueDate(LocalDate.of(y, m, 1), ChronoUnit.MONTHS);
            }
        } else if (len == 10) {
            if (text.matches(RE_YYYY_MM_DD)) {
                int y = Integer.valueOf(text.substring(0, 4));
                int m = Integer.valueOf(text.substring(5, 7));
                int d = Integer.valueOf(text.substring(8, 10));
                return new VagueDate(LocalDate.of(y, m, d), ChronoUnit.DAYS);
            }
        } else if (len == 19) {
            try {
                LocalDate t = LocalDate.parse(text, DateTimeFormatters.DASHED_YEAR2DAY);
                return new VagueDate(t, ChronoUnit.SECONDS);
            } catch (DateTimeParseException e) {
                // skip
            }
        }
        return VagueDate.NA;
    }

    private VagueDate(LocalDate begin, LocalDate end, TemporalUnit granularity, String text) {
        this.begin = begin;
        this.end = end;
        this.granularity = granularity;
        this.parsedText = text;
    }

    private VagueDate(LocalDate _begin, TemporalUnit granularity) {
        this(_begin, granularity, null);
    }

    private VagueDate(LocalDate _begin, TemporalUnit granularity, String text) {
        if (granularity instanceof ChronoUnit) {
            switch ((ChronoUnit) granularity) {

                case YEARS:
                    begin = LocalDate.of(_begin.getYear(), 1, 1);
                    end = LocalDate.of(_begin.getYear() + 1, 1, 1);
                    break;
                case MONTHS:
                    begin = LocalDate.of(_begin.getYear(), _begin.getMonth(), 1);
                    end = begin.plusMonths(1);
                    break;
                case DAYS:
                    begin = _begin;
                    end = begin.plusDays(1);
                    break;

                default:
                    throw new RuntimeException("not support yet.");
            }
        } else if (granularity instanceof ChronoUnitX) {
            switch ((ChronoUnitX) granularity) {
                case QUARTERS:
                    begin = LocalDate.of(_begin.getYear(), _begin.getMonth(), 1);
                    end = begin.plusMonths(3);
                    break;
                default:
                    throw new RuntimeException("not support yet.");
            }
        } else {
            throw new RuntimeException("not support yet.");
        }
        this.granularity = granularity;
        this.parsedText = text;
    }

    /**
     * get range lower bound included
     *
     * @return
     */
    public LocalDate begin() {
        return begin;
    }

    /**
     * get range upper bound excluded
     *
     * @return
     */
    public LocalDate end() {
        return end;
    }

    public TemporalUnit getTimeGranularity() {
        return granularity;
    }

    public boolean contains(LocalDate date) {
        return !date.isBefore(begin) && date.isBefore(end);
    }

    public long difference(TemporalUnit cu) {
        return begin.until(end, cu);
    }

    public LocalDate average() {
        long diff = difference(ChronoUnit.DAYS);
        return begin.plusDays(diff / 2);
    }

    @Override
    public String toString() {
        if (parsedText != null) {
            return parsedText;
        }
        if (granularity instanceof ChronoUnit) {
            switch ((ChronoUnit) granularity) {
                case YEARS:
                    return String.valueOf(begin.getYear());
                case MONTHS:
                    return begin.format(DateTimeFormatters.DASHED_YEAR2MON);
                case DAYS:
                    return begin.format(DateTimeFormatters.DASHED_YEAR2DAY);
                case SECONDS:
                    return begin.format(DateTimeFormatters.DASHED_YEAR2SEC);
                default:
                    // NO-OP
            }
        } else if (granularity instanceof ChronoUnitX) {
            switch ((ChronoUnitX) granularity) {
                case QUARTERS:
                    int q = (begin.getMonthValue() - 1) / 3 + 1;
                    return begin.getYear() + "Q" + q;
                default:
                    // NO-OP
            }
        }
        return begin.format(DateTimeFormatters.DEFAULT) + "~" + end.format(DateTimeFormatters.DEFAULT);
    }

    @Override
    public int hashCode() {
        return begin.hashCode() ^ end.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof VagueDate) {
            VagueDate other = (VagueDate) obj;
            return begin.equals(other.begin) && end.equals(other.end);
        }
        return false;
    }

}
