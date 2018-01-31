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

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import party.threebody.skean.data.query.Criteria;
import party.threebody.skean.dict.dao.BasicRelationDao;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.dao.X1RelationDao;
import party.threebody.skean.dict.domain.BasicRelation;
import party.threebody.skean.dict.domain.Word;
import party.threebody.skean.dict.domain.X1Relation;
import party.threebody.skean.lang.Beans;
import party.threebody.skean.misc.SkeanInvalidArgumentException;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

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
        w.setAliasRS0(listBasicRelsBySAD(text, "ALIA", null));

        w.setSubsetRSR(fetchSubsetRSR(text));
        w.setSupersetRSR(fetchSupersetRSR(text));

        w.setSubtopicRSR(fetchSubtopicRSR(text));
        w.setSupertopicRSR(fetchSupertopicRSR(text));

        Set<String> meAndMySubsetESR = w.getSubsetRSR().stream().map(BasicRelation::getDst).collect(toSet());
        Set<String> instanceESA = fetchInstanceESA(text, meAndMySubsetESR);
        Set<String> definitionESA = fetchDefinitionESA(text);

        w.setInstanceRS0(listBasicRelsBySAD(text, "INST", null));
        w.setInstanceESA(instanceESA);
        w.setDefinitionRS0(listBasicRelsBySAD(null, "INST", text));
        w.setDefinitionESA(definitionESA);

        w.setAttributeRS0(x1RelationDao.listBySrc(text));
        w.setReferenceRS0(x1RelationDao.listByDst(text));

        return w;
    }

    public Set<String> fetchInstanceESA(String me) {
        Set<String> mySubsetESR = fetchSubsetESR(me);
        return fetchInstanceESA(me, mySubsetESR);
    }

    /**
     * calc instanceESA = (me + subsetESR)'s instES0
     * 所有实例子孙=所有孩子+所有子集子孙的所有孩子
     */
    private Set<String> fetchInstanceESA(String me, Set<String> mySubsetESR) {

        Set<String> meAndMySubsetESR = SetUtils.union(mySubsetESR, Collections.singleton(me));
        Set<String> instanceESA = meAndMySubsetESR.stream()
                .flatMap(vex -> listBasicRelsBySAD(vex, "INST", null).stream())
                .map(BasicRelation::getDst)
                .collect(toSet());
        return instanceESA;
    }

    int countInstanceES0(String me){
        return countBasicRelsBySAD(me,"INST",null);
    }

    /**
     * calc definitionESA = defES0's supersetESR + defES0
     * 所有超类祖先=所有超类父亲的所有超集+所有超类父亲
     */
    public Set<String> fetchDefinitionESA(String me) {
        // 获取所有超类父亲defES0
        Set<String> myDefES0 = listBasicRelsBySAD(null, "INST", me).stream()
                .map(BasicRelation::getSrc).collect(toSet());
        Set<String> supersetESRofMyDefES0 = myDefES0.stream()
                .flatMap(def -> listRecursively(def, "SUBS", false).stream())
                .map(BasicRelation::getSrc)
                .collect(toSet());
        Set<String> definitionESA = SetUtils.union(supersetESRofMyDefES0, myDefES0);
        return definitionESA;
    }


    public Set<String> fetchSubsetESR(String me) {
        return fetchSubsetRSR(me).stream().map(BasicRelation::getDst).collect(toSet());
    }

    public Set<String> fetchSupersetESR(String me) {
        return fetchSubsetRSR(me).stream().map(BasicRelation::getSrc).collect(toSet());
    }
    public Set<String> fetchSubtopicESR(String me) {
        return fetchSubtopicRSR(me).stream().map(BasicRelation::getDst).collect(toSet());
    }
    public Set<String> fetchSupertopicESR(String me) {
        return fetchSubtopicRSR(me).stream().map(BasicRelation::getSrc).collect(toSet());
    }


    public Set<BasicRelation> fetchSubsetRSR(String me) {
        return listRecursively(me, "SUBS", true);
    }

    public Set<BasicRelation> fetchSupersetRSR(String me) {
        return listRecursively(me, "SUBS", false);
    }

    public Set<BasicRelation> fetchSubtopicRSR(String me) {
        return listRecursively(me, "SUBT", true);
    }

    public Set<BasicRelation> fetchSupertopicRSR(String me) {
        return listRecursively(me, "SUBT", false);
    }

    public Set<String> fetchSubESR(String me) {
        DAGVisitor<String, BasicRelation> dagVisitor = new DAGVisitor<>(
                this::listBasicRelsNonAlias,
                BasicRelation::getSrc,
                BasicRelation::getDst
        );
        dagVisitor.visitFrom(me);
        return dagVisitor.getVerticesVisitedExceptSource();
    }

    public Set<String> fetchSuperESR(String me) {
        DAGVisitor<String, BasicRelation> dagVisitor = new DAGVisitor<>(
                this::listBasicRelsNonAlias,
                BasicRelation::getDst,
                BasicRelation::getSrc
        );
        dagVisitor.visitFrom(me);
        return dagVisitor.getVerticesVisitedExceptSource();
    }
    /**
     * TODO cache this
     *
     * @param src  if null, ignore this filter
     * @param attr if null, ignore this filter
     * @param dst  if null, ignore this filter
     */
    protected int countBasicRelsBySAD(String src, String attr, String dst) {
        return 0;
    }

    /**
     * TODO cache this
     *
     * @param src  if null, ignore this filter
     * @param attr if null, ignore this filter
     * @param dst  if null, ignore this filter
     */
    protected List<BasicRelation> listBasicRelsBySAD(String src, String attr, String dst) {
        return basicRelationDao.listBySAD(src, attr, dst);
    }

    protected List<BasicRelation> listBasicRelsNonAlias() {
        return basicRelationDao.listNonAlias();
    }
    /**
     * TODO cache this
     *
     * @param src  if null, ignore this filter
     * @param attr if null, ignore this filter
     * @param dst  if null, ignore this filter
     */
    protected List<X1Relation> listX1RelsBySAD(String src, String attr, String dst) {
        return x1RelationDao.listBySAD(src, attr, dst);
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
                    () -> listBasicRelsBySAD(null, attr, null),
                    BasicRelation::getSrc,
                    BasicRelation::getDst
            );
        } else {
            dagv = new DAGVisitor<>(
                    () -> listBasicRelsBySAD(null, attr, null),
                    BasicRelation::getDst,
                    BasicRelation::getSrc
            );
        }
        dagv.visitFrom(me);
        return dagv.getEdgesVisited();
    }

    @Transactional
    public int createBasicRelation(BasicRelation br) {
        if (br.getNo() == null) {
            int maxNo = basicRelationDao.getMaxNo(br.getSrc(), br.getAttr());
            br.setNo(maxNo + 1);
        }
        return basicRelationDao.create(br);
    }


    @Transactional
    public int createX1Relation(X1Relation x1r) {
        if (x1r.getNo() == null) {
            int maxNo = x1RelationDao.getMaxNo(x1r.getSrc(), x1r.getAttr());
            x1r.setNo(maxNo + 1);
        }
        return x1RelationDao.create(x1r);
    }

    /**
     * batch create, splitting src/dst to array
     *
     * @param x1r
     * @param delimitter eg. "\\s+"
     * @return cnt(src)*cnt(dst)
     */
    @Transactional
    public int createX1Relations(X1Relation x1r, String delimitter) {
        int no = x1RelationDao.getMaxNo(x1r.getSrc(), x1r.getAttr());
        String rawSrc = x1r.getSrc();
        String rawDst = x1r.getDst();
        if (StringUtils.isBlank(rawSrc)) {
            throw new SkeanInvalidArgumentException("missing argument: 'src'");
        }
        if (StringUtils.isBlank(rawDst)) {
            throw new SkeanInvalidArgumentException("missing argument: 'dst'");
        }
        X1Relation r = Beans.clone(x1r);
        String[] srcArr = rawSrc.split(delimitter);
        String[] dstArr = rawDst.split(delimitter);
        int rna = 0;
        for (String src : srcArr) {
            for (String dst : dstArr) {
                r.setSrc(src);
                r.setDst(dst);
                r.setNo(++no);
                rna += x1RelationDao.create(r);
            }
        }
        return rna;

    }


}