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

package party.threebody.skean.jdbc;

import static party.threebody.skean.jdbc.SqlPrinterConfig.PrintSqlResultStrategy.ALWAYS_TOSTRING;

public class SqlPrinterConfig {

    private boolean printSqlAndResult = true;
    private int maxCharsToPrintSqlResult = 1024;
    private int maxCharsToPrintInOneLine = 200;
    private PrintSqlResultStrategy printSqlResultStrategy = ALWAYS_TOSTRING;


    public enum PrintSqlResultStrategy {
        ALWAYS_TOSTRING, REFLECTION, JACKSON
    }


    public boolean isPrintSqlAndResult() {
        return printSqlAndResult;
    }

    public void setPrintSqlAndResult(boolean printSqlAndResult) {
        this.printSqlAndResult = printSqlAndResult;
    }

    public int getMaxCharsToPrintSqlResult() {
        return maxCharsToPrintSqlResult;
    }

    public void setMaxCharsToPrintSqlResult(int maxCharsToPrintSqlResult) {
        this.maxCharsToPrintSqlResult = maxCharsToPrintSqlResult;
    }

    public int getMaxCharsToPrintInOneLine() {
        return maxCharsToPrintInOneLine;
    }

    public void setMaxCharsToPrintInOneLine(int maxCharsToPrintInOneLine) {
        this.maxCharsToPrintInOneLine = maxCharsToPrintInOneLine;
    }

    public PrintSqlResultStrategy getPrintSqlResultStrategy() {
        return printSqlResultStrategy;
    }

    public void setPrintSqlResultStrategy(PrintSqlResultStrategy printSqlResultStrategy) {
        this.printSqlResultStrategy = printSqlResultStrategy;
    }
}