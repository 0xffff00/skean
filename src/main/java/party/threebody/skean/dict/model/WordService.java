package party.threebody.skean.dict.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WordService {
	@Autowired
	DictDao dictDao;

	TreeNode<String> t2(String text) {
		Word w = new Word();
		w.setText(text);
		TreeNode<String> subsetTree=dfs(text,this::listSubsets);
		w.setAliases(null);
		return subsetTree;
	}

	TreeNode<String> dfs_subs(String text) {
		TreeNode<String> res = new TreeNode<>(text);
		List<String> subsets = listSubsets(text);
		res.setSons(subsets.stream().map(s -> dfs_subs(s)).collect(Collectors.toList()));
		return res;
	}

	TreeNode<String> dfs(String val, Function<String, List<String>> listSonsFunc) {
		TreeNode<String> res = new TreeNode<>(val);
		List<String> sonsVal = listSonsFunc.apply(val);
		List<TreeNode<String>> sons=sonsVal.stream().map(s -> dfs(s, listSonsFunc)).collect(Collectors.toList());
		res.setSons(sons);
		return res;
	}

	List<String> listSubsets(String text) {
		return listSubsetRelations(text).stream().map(r -> r.getVal()).collect(Collectors.toList());
	}

	List<String> listSupersets(String text) {
		return listSupersetRelations(text).stream().map(r -> r.getKey()).collect(Collectors.toList());
	}

	List<DualRelation> listInstanceRelations(String text) {
		return dictDao.listDualRelationsByKeyAndAttr(text, DualType.INST.toString());
	}

	List<DualRelation> listDefinitionRelations(String text) {
		return dictDao.listDualRelationsByValAndAttr(text, DualType.INST.toString());
	}

	List<DualRelation> listSubsetRelations(String text) {
		return dictDao.listDualRelationsByKeyAndAttr(text, DualType.SUBS.toString());
	}

	List<DualRelation> listSupersetRelations(String text) {
		return dictDao.listDualRelationsByValAndAttr(text, DualType.SUBS.toString());
	}

	List<DualRelation> listChildren(String text) {
		return dictDao.listDualRelationsByKeyAndAttr(text, DualType.GECH.toString());
	}

	List<DualRelation> listParentRelations(String text) {
		return dictDao.listDualRelationsByValAndAttr(text, DualType.GECH.toString());
	}

	List<GenericRelation> listAttributeRelations(String text) {
		return dictDao.listGenericRelationsByKey(text);
	}

	List<GenericRelation> listReferenceRelations(String text) {
		return dictDao.listGenericRelationsByVal(text);
	}
}
