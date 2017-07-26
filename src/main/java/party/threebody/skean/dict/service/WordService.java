package party.threebody.skean.dict.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import party.threebody.skean.dict.dao.DictDao;
import party.threebody.skean.dict.model.DualRelation;
import party.threebody.skean.dict.model.DualType;
import party.threebody.skean.dict.model.GenericNonRefRelation;
import party.threebody.skean.dict.model.GenericRelation;
import party.threebody.skean.dict.model.Relation;
import party.threebody.skean.dict.model.Word;
import party.threebody.skean.util.TreeNode;
import party.threebody.skean.util.Trees;

@Service
public class WordService {
	@Autowired
	DictDao dictDao;

	Word getWord(String text) {
		Word w = new Word();
		w.setText(text);
		w.setInstanceRelations(listInstanceRelations(text));
		w.setDefinitionRelations(listDefinitionRelations(text));
		w.setSubsetRelations(listSubsetRelations(text));
		w.setSupersetRelations(listSupersetRelations(text));
		w.setChildRelations(listChildrenRelations(text));
		w.setParentRelations(listParentRelations(text));
		w.setAttributeRelations(listAttributeRelations(text));
		w.setAttributeNonRefRelations(listAttributeNonRefRelations(text));
		w.setReferenceRelations(listReferenceRelations(text));

		w.setSubsetTree(buildSubsetTree(text));
		w.setSupersetTree(buildSupersetTree(text));
		w.setChildTree(buildChildTree(text));
		w.setParentTree(buildParentTree(text));

		w.setSubsets(Trees.flattenDistinct(w.getSubsetTree().getSons()));
		w.setSupersets(Trees.flattenDistinct(w.getSupersetTree().getSons()));

		w.setInstances(listInstances(w.getSubsets()));
		w.setDefinitions0(listDefinitions0(text));

		w.setDescendants(Trees.flattenDistinct(w.getChildTree().getSons()));
		w.setAncestors(Trees.flattenDistinct(w.getParentTree().getSons()));

		w.setDefinitionTrees(buildSupersetTrees(w.getDefinitions0()));
		w.setDefinitions(Trees.flattenDistinct(w.getDefinitionTrees()));

		w.setAttributeVals(listAttributeVals(text));
		w.setReferenceKeys(listReferenceKeys(text));
		
		return w;

	}

	/**
	 * list all instances of all subsets in deep
	 * 
	 * @param text
	 * @return
	 */
	public List<String> listInstances(List<String> allDefinitions) {
		return allDefinitions.stream().flatMap(def -> listInstances(def).stream()).distinct()
				.collect(Collectors.toList());
	}

	// --------- trees building
	public TreeNode<String> buildSubsetTree(String text) {
		return Trees.buildTreeRecursively(text, this::listSubsets);
	}

	public TreeNode<String> buildSupersetTree(String text) {
		return Trees.buildTreeRecursively(text, this::listSupersets);
	}

	public TreeNode<String> buildChildTree(String text) {
		return Trees.buildTreeRecursively(text, this::listChildren);
	}

	public TreeNode<String> buildParentTree(String text) {
		return Trees.buildTreeRecursively(text, this::listParents);
	}

	public List<TreeNode<String>> buildSupersetTrees(List<String> texts) {
		return texts.stream().map(this::buildSupersetTree).collect(Collectors.toList());
	}

	// --------- fetching with no depth

	private static List<String> listVals(List<? extends Relation> relations) {
		return relations.stream().map(r -> r.getVal()).collect(Collectors.toList());
	}

	private static List<String> listKeys(List<? extends Relation> relations) {
		return relations.stream().map(r -> r.getKey()).collect(Collectors.toList());
	}

	public List<String> listInstances(String text) {
		return listVals(listInstanceRelations(text));
	}

	public List<String> listDefinitions0(String text) {
		return listKeys(listDefinitionRelations(text));
	}

	public List<String> listSubsets(String text) {
		return listVals(listSubsetRelations(text));
	}

	public List<String> listSupersets(String text) {
		return listKeys(listSupersetRelations(text));
	}

	public List<String> listChildren(String text) {
		return listVals(listChildrenRelations(text));
	}

	public List<String> listParents(String text) {
		return listKeys(listParentRelations(text));
	}

	public List<String> listAttributeVals(String text) {
		return listVals(listAttributeRelations(text));
	}

	public List<String> listReferenceKeys(String text) {
		return listKeys(listReferenceRelations(text));
	}


	// --------- raw data fetching
	public List<DualRelation> listInstanceRelations(String text) {
		return dictDao.listDualRelationsByKeyAndAttr(text, DualType.INST.toString());
	}

	public List<DualRelation> listDefinitionRelations(String text) {
		return dictDao.listDualRelationsByValAndAttr(text, DualType.INST.toString());
	}

	public List<DualRelation> listSubsetRelations(String text) {
		return dictDao.listDualRelationsByKeyAndAttr(text, DualType.SUBS.toString());
	}

	public List<DualRelation> listSupersetRelations(String text) {
		return dictDao.listDualRelationsByValAndAttr(text, DualType.SUBS.toString());
	}

	public List<DualRelation> listChildrenRelations(String text) {
		return dictDao.listDualRelationsByKeyAndAttr(text, DualType.GECH.toString());
	}

	public List<DualRelation> listParentRelations(String text) {
		return dictDao.listDualRelationsByValAndAttr(text, DualType.GECH.toString());
	}

	public List<GenericRelation> listAttributeRelations(String text) {
		return dictDao.listGenericRelationsByKey(text);
	}

	public List<GenericNonRefRelation> listAttributeNonRefRelations(String text) {
		return dictDao.listGenericNonRefRelationsByKey(text);
	}

	public List<GenericRelation> listReferenceRelations(String text) {
		return dictDao.listGenericRelationsByVal(text);
	}
}
