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

public class SqlBuilderConfig {

    /**
     * select indent string, default to be 2 spaces
     */
    private String subSelectIndent = "  ";
    /**
     * general delimitter
     */
    private String dlmt = "\n";
    /**
     * clause delimitter
     */
    private String clauseDlmt = "\n";

    /**
     * should quote all objects' names (table, schemas), default to be true
     */
    private boolean nameQuoteEnabled = true;
    /**
     * allow delete/update all rows without WHERE clause
     */
    private boolean modifyAllRowsEnabled = true;
    /**
     *
     */
    private boolean convertParamNameToSnakeCaseEnabled = true;
    /**
     * max chars of column names selected to print
     */
    private int maxCharsOfInlineSelCols = 40;


    public String getSubSelectIndent() {
        return subSelectIndent;
    }

    public void setSubSelectIndent(String subSelectIndent) {
        this.subSelectIndent = subSelectIndent;
    }

    public String getDlmt() {
        return dlmt;
    }

    public void setDlmt(String dlmt) {
        this.dlmt = dlmt;
    }

    public String getClauseDlmt() {
        return clauseDlmt;
    }

    public void setClauseDlmt(String clauseDlmt) {
        this.clauseDlmt = clauseDlmt;
    }

    public boolean isModifyAllRowsEnabled() {
        return modifyAllRowsEnabled;
    }

    public void setModifyAllRowsEnabled(boolean modifyAllRowsEnabled) {
        this.modifyAllRowsEnabled = modifyAllRowsEnabled;
    }

    public boolean isConvertParamNameToSnakeCaseEnabled() {
        return convertParamNameToSnakeCaseEnabled;
    }

    public void setConvertParamNameToSnakeCaseEnabled(boolean convertParamNameToSnakeCaseEnabled) {
        this.convertParamNameToSnakeCaseEnabled = convertParamNameToSnakeCaseEnabled;
    }

    public int getMaxCharsOfInlineSelCols() {
        return maxCharsOfInlineSelCols;
    }

    public void setMaxCharsOfInlineSelCols(int maxCharsOfInlineSelCols) {
        this.maxCharsOfInlineSelCols = maxCharsOfInlineSelCols;
    }

    public boolean isNameQuoteEnabled() {
        return nameQuoteEnabled;
    }

    public void setNameQuoteEnabled(boolean nameQuoteEnabled) {
        this.nameQuoteEnabled = nameQuoteEnabled;
    }
}