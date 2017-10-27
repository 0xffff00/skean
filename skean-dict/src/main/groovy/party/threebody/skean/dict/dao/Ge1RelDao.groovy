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
import party.threebody.skean.dict.domain.Ge1Rel
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO

@Repository
class Ge1RelDao extends TriplePKsJpaCrudDAO<Ge1Rel, String, String, Integer> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return cjt
    }

//    int delete(String key, String attr, Integer vno) {
//        cjt.from(getTable()).by('key', 'attr', 'vno').val(key, attr, vno).delete()
//    }

    int delete(String key, String attr) {
        cjt.from(getTable()).by('key', 'attr').val(key, attr).delete()
    }

    int maxVno(String key, String attr) {
        def sql = 'SELECT max(vno) FROM dct_rel_ge_dat1 WHERE `key`=? AND attr=?'
        (int) cjt.sql(sql).arg(key, attr).firstCell() ?: 0
    }

}
