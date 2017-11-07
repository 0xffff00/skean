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

package party.threebody.skean.web.data;

import party.threebody.skean.data.query.Operator;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SkeanWebConfig {


    private CriteriaVarNameConfig criteriaVarName = new CriteriaVarNameConfig();
    private HeaderNameConfig headerName = new HeaderNameConfig();

    public CriteriaVarNameConfig getCriteriaVarName() {
        return criteriaVarName;
    }

    public void setCriteriaVarName(CriteriaVarNameConfig criteriaVarName) {
        this.criteriaVarName = criteriaVarName;
    }

    public HeaderNameConfig getHeaderName() {
        return headerName;
    }

    public void setHeaderName(HeaderNameConfig headerName) {
        this.headerName = headerName;
    }

    public static class HeaderNameConfig {
        private String totalCount = "X-Total-Count";
        private String totalAffected = "X-Total-Affected";

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }

        public String getTotalAffected() {
            return totalAffected;
        }

        public void setTotalAffected(String totalAffected) {
            this.totalAffected = totalAffected;
        }
    }


    public static class CriteriaVarNameConfig {

        private List<String> pageIndex = Arrays.asList("p", "page");
        private List<String> pageOffset = Arrays.asList("f", "offset");
        private List<String> pageLimit = Arrays.asList("l", "limit", "per_page");
        private List<String> orders = Arrays.asList("o", "orders");
        private List<String> ordersAscPrefixes = Arrays.asList("+");
        private List<String> ordersDescPrefixes = Arrays.asList("!", "-");
        private String extesibleVarSuffixTemplate = "_{x}";
        // operator displayed name -> operator real name
        private Map<String, String> operators = Stream.of(Operator.values()).collect(
                Collectors.toMap(Operator::name, Operator::name));

        public List<String> getPageIndex() {
            return pageIndex;
        }

        public void setPageIndex(List<String> pageIndex) {
            this.pageIndex = pageIndex;
        }

        public List<String> getPageOffset() {
            return pageOffset;
        }

        public void setPageOffset(List<String> pageOffset) {
            this.pageOffset = pageOffset;
        }

        public List<String> getPageLimit() {
            return pageLimit;
        }

        public void setPageLimit(List<String> pageLimit) {
            this.pageLimit = pageLimit;
        }

        public List<String> getOrders() {
            return orders;
        }

        public void setOrders(List<String> orders) {
            this.orders = orders;
        }

        public List<String> getOrdersAscPrefixes() {
            return ordersAscPrefixes;
        }

        public void setOrdersAscPrefixes(List<String> ordersAscPrefixes) {
            this.ordersAscPrefixes = ordersAscPrefixes;
        }

        public List<String> getOrdersDescPrefixes() {
            return ordersDescPrefixes;
        }

        public void setOrdersDescPrefixes(List<String> ordersDescPrefixes) {
            this.ordersDescPrefixes = ordersDescPrefixes;
        }

        public String getExtesibleVarSuffixTemplate() {
            return extesibleVarSuffixTemplate;
        }

        public void setExtesibleVarSuffixTemplate(String extesibleVarSuffixTemplate) {
            this.extesibleVarSuffixTemplate = extesibleVarSuffixTemplate;
        }

        public Map<String, String> getOperators() {
            return operators;
        }

        public void setOperators(Map<String, String> operators) {
            this.operators = operators;
        }
    }


}
