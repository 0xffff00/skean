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
import party.threebody.skean.collections.Maps
import party.threebody.skean.dict.domain.X1Relation
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.TriplePKsJpaCrudDAO

@Repository
class X1RelationDao extends TriplePKsJpaCrudDAO<X1Relation, String, String, Integer> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        cjt
    }

    List<X1Relation> listBySrc(String src) {
        fromTable().by('src').val(src).list(X1Relation.class)
    }

    List<X1Relation> listByDst(String dst) {
        fromTable().by('dst').val(dst).list(X1Relation.class)
    }

    int getMaxNo(String src, String attr) {
        def sql = "SELECT MAX(`NO`) FROM dct_rel_x1 WHERE src=? AND attr=?"
        Integer res = cjt.sql(sql).arg(src, attr).firstCell()
        res == null ? -1 : res
    }

    List<X1Relation> listBySAD(String src, String attr, String dst) {
        def valMap = Maps.filterNullValues([src: src, attr: attr, dst: dst])
        fromTable().by(valMap).list(X1Relation.class)
    }
}
