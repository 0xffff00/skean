package party.threebody.skean.dict.service;

import static java.util.stream.Collectors.toList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.google.common.collect.Sets;

import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.dict.dao.DualRelDao;
import party.threebody.skean.dict.dao.Ge1RelDao;
import party.threebody.skean.dict.dao.Ge2RelDao;
import party.threebody.skean.dict.dao.WordDao;
import party.threebody.skean.dict.model.AliasRel;
import party.threebody.skean.dict.model.DualRel;
import party.threebody.skean.dict.model.Ge1Rel;
import party.threebody.skean.dict.model.Ge2Rel;
import party.threebody.skean.dict.model.Word;

@Service
public class WordService {
	static final Logger logger = LoggerFactory.getLogger(WordService.class);
	@Autowired
	WordDao wordDao;
	@Autowired
	DualRelDao dualRelDao;
	@Autowired
	Ge1RelDao ge1RelDao;
	@Autowired
	Ge2RelDao ge2RelDao;
	public Word createWord(Word w) {
		return wordDao.create(w);
	}

	public int updateWord(Word w, String text) {
		return wordDao.update(w, text);
	}

	public int deleteWord(String text) {
		return wordDao.delete(text);
	}

	public List<Word> listWords(QueryParamsSuite qps) {
		return wordDao.readList(qps);
	}
	
	public List<String> listTemporaryTexts(){
		return wordDao.listTemporaryTexts();
	}
	
	
	public int countWords(QueryParamsSuite qps) {
		return wordDao.readCount(qps);
	}

	public Word getWord(String text) {
		return wordDao.readOne(text);

	}

	public Word getWordWithRels(String text) {
		Word w = wordDao.readOne(text);
		if (w == null) {
			w = new Word();
			w.setText(text);
			// return w;
		}
		DAGVisitor<String, AliasRel> dagv1 = new DAGVisitor<>(this::listAliasRels, AliasRel::getKey, AliasRel::getVal);
		dagv1.visitFrom(text);
		w.setAliases(dagv1.getVerticesVisited());
		w.setAliasRels(dagv1.getEdgesVisited());
		// calc DualRel
		DAGVisitor<String, DualRel> drDVp = new DAGVisitor<>(this::listDualRels, DualRel::getKey, DualRel::getVal);
		drDVp.visitFrom(text);

		DAGVisitor<String, DualRel> drDVn = new DAGVisitor<>(this::listDualRels, DualRel::getVal, DualRel::getKey);
		drDVn.visitFrom(text);
		w.setDualRels(Sets.union(drDVp.getEdgesVisited(), drDVn.getEdgesVisited()));

		// calc Ge1Rel
		DAGVisitor<String, Ge1Rel> g1DVp = new DAGVisitor<>(this::listGe1Rels, Ge1Rel::getKey, Ge1Rel::getVal);
		g1DVp.visitFrom(text);

		DAGVisitor<String, Ge1Rel> g1DVn = new DAGVisitor<>(this::listGe1Rels, Ge1Rel::getVal, Ge1Rel::getKey);
		g1DVn.visitFrom(text);
		w.setGe1Rels(Sets.union(g1DVp.getEdgesVisited(), g1DVn.getEdgesVisited()));

		// calc Ge2Rel
		DAGVisitor<String, Ge2Rel> g2DVp = new DAGVisitor<>(this::listGe2Rels, Ge2Rel::getKey, Ge2Rel::getVal);
		g2DVp.visitFrom(text);
		w.setGe2Rels(g2DVp.getEdgesVisited());

		return w;
	}

	// --------- pure DAO CRUDs ------------

	@Cacheable(value = "aliasRels")
	List<AliasRel> listAliasRels() {
		return wordDao.listAliasRels();
	}

	@CacheEvict(value = "aliasRels")
	int createAliasRel(AliasRel rel) {
		return wordDao.createAliasRel(rel);
	}

	@CacheEvict(value = "aliasRels")
	int updateAliasRelByKV(AliasRel rel) {
		return wordDao.updateAliasRelByKV(rel);
	}

	@CacheEvict(value = "aliasRels")
	int deleteAliasRelsByKV(String key, String val) {
		return wordDao.deleteAliasRelsByKV(key, val);
	}

	@Cacheable(value = "dualRels")
	List<DualRel> listDualRels() {
		return dualRelDao.readList(null);
	}

	@Cacheable(value = "Ge1Rels")
	List<Ge1Rel> listGe1Rels() {
		return ge1RelDao.readList(null);
	}

	@Cacheable(value = "Ge2Rels")
	List<Ge2Rel> listGe2Rels() {
		return ge2RelDao.readList(null);
	}

	
	public DualRel createDualRel(DualRel rel){
		return dualRelDao.create(rel);
	}
	
	public int deleteDualRel(DualRel rel){
		return dualRelDao.delete(rel);
	}

	public int deleteDualRel(String key, String attr, Integer vno, String val){
		return dualRelDao.delete(key,attr,vno,val);
	}
	/**
	 * 
	 * @author hzk
	 * @since 2017-08-05
	 * @param <V>
	 * @param <E>
	 */
	static class DAGVisitor<V, E> {
		private Function<V, List<E>> edgeVisitor;
		private Function<E, V> destVertexMapper;
		private Set<V> verticesVisited;
		private Set<E> edgesVisited;

		public DAGVisitor(Supplier<List<E>> allEdgesSupplier, Function<E, V> srcVertexMapper,
				Function<E, V> destVertexMapper) {
			this.destVertexMapper = destVertexMapper;
			this.edgeVisitor = v -> allEdgesSupplier.get().stream().filter(e -> v.equals(srcVertexMapper.apply(e)))
					.collect(toList());
		}

		public DAGVisitor(Function<V, List<E>> edgeVisitor, Function<E, V> destVertexMapper) {
			this.destVertexMapper = destVertexMapper;
			this.edgeVisitor = edgeVisitor;
		}

		public void visitFrom(V root) {
			verticesVisited = new HashSet<>();
			edgesVisited = new HashSet<>();
			dfs(root);
		}

		public Set<V> getVerticesVisited() {
			return verticesVisited;
		}

		public Set<E> getEdgesVisited() {
			return edgesVisited;
		}

		void dfs(V v) {
			if (v == null || verticesVisited.contains(v)) {
				return;
			}
			verticesVisited.add(v);
			List<E> edges = edgeVisitor.apply(v);
			edgesVisited.addAll(edges);
			logger.debug("dfs({}) -> edges{}", v, edges);
			if (edges.size() == 0)
				return;
			edges.forEach(edge -> dfs(destVertexMapper.apply(edge)));
		}
	}
}