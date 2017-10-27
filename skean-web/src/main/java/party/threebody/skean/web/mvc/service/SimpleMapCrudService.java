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

package party.threebody.skean.web.mvc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.CriteriaAndSortingAndPaging;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;

import java.util.List;
import java.util.Map;

/**
 * not so useful
 */
@Service
public class SimpleMapCrudService {

    @Autowired
    ChainedJdbcTemplate cjt;
    String table;
    String[] afCols;
    String[] byCols;

    public int create(String table, String[] afCols, Map<String, Object> entity) {
        return cjt.from(table).affect(afCols).valMap(entity).insert();
    }

    public int readCount(String table, CriteriaAndSortingAndPaging qps) {
        return cjt.from(table).criteria(qps.getCriteria()).count();
    }

    public List<Map<String, Object>> readListOfMap(String table, CriteriaAndSortingAndPaging qps) {
        return cjt.from(table).criteriaAndSortAndPage(qps).list();
    }

    public Map<String, Object> readOneOfMap(String table, Map<String, Object> byWhat) {
        return cjt.from(table).by(byWhat).single();
    }

    public Map<String, Object> readOneOfMap(String table, String[] byCols, Object[] byWhat) {
        return cjt.from(table).by(byCols).valArr(byWhat).single();
    }

    public int update(Map<String, Object> afWhat, Map<String, Object> byWhat) {
        return cjt.from(table).affect(afCols).valMap(afWhat).by(byCols).valMap(byWhat).update();
    }

    public int update(String[] afWhat, String[] byWhat) {
        return cjt.from(table).affect(afCols).valArr(afWhat).by(byCols).valArr(byWhat).update();
    }

    public int delete(Map<String, Object> byWhat) {
        return cjt.from(table).by(byCols).valMap(byWhat).delete();
    }

    public int delete(String[] byWhat) {
        return cjt.from(table).by(byCols).valArr(byWhat).delete();
    }

}
