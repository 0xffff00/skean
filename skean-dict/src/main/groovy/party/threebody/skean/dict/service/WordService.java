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

package party.threebody.skean.dict.service;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.SetUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.BasicRelation;
import party.threebody.skean.dict.domain.Word;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class WordService {
    static final Logger logger = LoggerFactory.getLogger(WordService.class);
    @Autowired
    WordDao wordDao;
    @Autowired
    BasicRelationDao basicRelationDao;
    @Autowired
    X1RelationDao x1RelationDao;

    public Word createWord(Word w) {
        return wordDao.createAndGet(w);
    }

    public int countWords(Criteria criteria) {
        return wordDao.readCount(criteria);
    }

    public Word getWord(String text) {
        return wordDao.readOne(text);

    }

    public Word getWordWithRels(String text) {
        Word w = wordDao.readOne(text);
        if (w == null) {
            w = new Word();
            w.setText(text);
            w.setState("t");
        }
        w.setAliasRS0(basicRelationDao.listBySAD(text, "ALIA", null));

        w.setSubsetRSR(listRecursively(text, "SUBS", true));
        w.setSupersetRSR(listRecursively(text, "SUBS", false));

        w.setSubtopicRSR(listRecursively(text, "TOPI", true));
        w.setSupertopicRSR(listRecursively(text, "TOPI", false));

        // calc instanceESA: = (me + subsetESR)'s instES0
        List<String> subsetAndMeESR = ListUtils.union(
                w.getSubsetRSR().stream().map(BasicRelation::getDst).collect(Collectors.toList()),
                Arrays.asList(text));
        Set<String> instanceESA = subsetAndMeESR.stream()
                .flatMap(vex -> basicRelationDao.listBySAD(vex, "INST", null).stream())
                .map(BasicRelation::getDst)
                .collect(Collectors.toSet());

        // calc definitionESA: = defES0's supersetESR + defES0
        Set<String> defES0 = basicRelationDao.listBySAD(null, "INST", text).stream()
                .map(BasicRelation::getSrc).collect(Collectors.toSet());
        Set<String> defES0_supersetESR = defES0.stream()
                .flatMap(def -> listRecursively(def, "SUBS", false).stream())
                .map(BasicRelation::getSrc)
                .collect(Collectors.toSet());
        Set<String> definitionESA = SetUtils.union(defES0_supersetESR, defES0);

        w.setInstanceRS0(basicRelationDao.listBySAD(text, "INST", null));
        w.setInstanceESA(instanceESA);
        w.setDefinitionRS0(basicRelationDao.listBySAD(null, "INST", text));
        w.setDefinitionESA(definitionESA);


        w.setAttributeRS0(x1RelationDao.listBySrc(text));
        w.setReferenceRS0(x1RelationDao.listByDst(text));

        return w;
    }

    /**
     * use DAGVisitor, fetch all lines at first.
     * TODO perf problem to fix: maybe too large result set
     *
     * @param me      the start vertex
     * @param forward forward(src->dst) or backward(dst->src)
     */
    private Set<BasicRelation> listRecursively(String me, String attr, boolean forward) {
        DAGVisitor<String, BasicRelation> dagv;
        if (forward) {
            dagv = new DAGVisitor<>(
                    () -> basicRelationDao.listBySAD(null, attr, null),
                    BasicRelation::getSrc,
                    BasicRelation::getDst
            );
        } else {
            dagv = new DAGVisitor<>(
                    () -> basicRelationDao.listBySAD(null, attr, null),
                    BasicRelation::getDst,
                    BasicRelation::getSrc
            );
        }
        dagv.visitFrom(me);
        return dagv.getEdgesVisited();
    }

    private <E> List<E> toMerged(List<E> first, E last) {
        List<E> res = new ArrayList(first);
        res.add(last);
        return res;
    }


}