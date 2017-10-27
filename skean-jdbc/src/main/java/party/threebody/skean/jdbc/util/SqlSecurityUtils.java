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

import party.threebody.skean.jdbc.SkeanSqlSecurityException;

public class SqlSecurityUtils {
    /**
     * check table name legality.<br>
     * Helping prevent persistence layer from SQL injection attack.
     *
     * @param name
     * @throws SkeanSqlSecurityException
     */
    public static void checkTableNameLegality(String name) {
        if (name == null) {
            throw new SkeanSqlSecurityException("illegal table name: null");
        }
        if (!name.matches("\\w+")) {
            throw new SkeanSqlSecurityException("illegal table name: \"" + name + "\"");
        }
    }

    /**
     * check column name or table name legality.<br>
     * Helping prevent persistence layer from SQL injection attack.
     *
     * @param name
     * @throws SkeanSqlSecurityException
     */
    public static void checkColumnNameLegality(String name) {
        // TODO let simple function wrapper pass. eg. upper(col1)
        if (name == null) {
            throw new SkeanSqlSecurityException("illegal column name: null");
        }
        if (!name.matches("\\w+")) {
            throw new SkeanSqlSecurityException("illegal column name: \"" + name + "\"");
        }
    }


}