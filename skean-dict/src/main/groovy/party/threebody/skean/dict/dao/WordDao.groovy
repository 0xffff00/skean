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

package party.threebody.skean.dict.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.skean.data.query.Criteria
import party.threebody.skean.dict.domain.Word
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO

@Repository
class WordDao extends SinglePKJpaCrudDAO<Word, String> {

    @Autowired ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        cjt
    }

    def sql_1='''
          SELECT src FROM dct_rel_b
    UNION SELECT dst FROM dct_rel_b
    UNION SELECT src FROM dct_rel_x1
    UNION SELECT dst FROM dct_rel_x1'''


    List<String> listAllWordsMentioned(Criteria criteria){
        cjt.fromSql(sql_1).criteria(criteria).listOfSingleColumn(String.class)
    }
    int countAllWordsMentioned(Criteria criteria){
        cjt.fromSql(sql_1).criteria(criteria).count()
    }

}
