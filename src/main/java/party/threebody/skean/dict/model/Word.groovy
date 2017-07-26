package party.threebody.skean.dict.model

import party.threebody.skean.util.TreeNode

/**
 * for example :<br>
 * suppose there is a Word texted as 'China'<br>
 * query algorithms of the following collections are:<br>
 * <pre>
 * <i>|collection|		criteria     | -> |result|</i>
 * instances: 	key='China',attr='INST' -> val 
 * definitions:	val='China',attr='INST' -> key 
 * children:	key='China',attr='GECH' -> val 
 * parents:		val='China',attr='GECH' -> key 
 * subsets:		key='China',attr='SUBS' -> val 
 * supersets:	val='China',attr='SUBS' -> key 
 * attributes:	key='China' -> attr,attrx,val 
 * references:	val='China' -> attr,attrx,key 
 * aliases:		key='China' -> val  
 * --------
 * the following 4 pairs are inverse conception to each other: 
 * 1. instances - definitions
 * 2. children - parents
 * 3. subsets - supersets
 * 4. attributes - references
 * </pre>
 * @author hzk
 * @since 2017-07-23
 */
class Word extends WordDO {
	//------ tree values ----
	// trees composed of recursive collections above
	TreeNode<String> childTree, parentTree
	TreeNode<String> subsetTree, supersetTree
	
	// tree composed of recursive supersets of definitions shallow
	List<TreeNode<String>> definitionTrees	
	
	//------ flatten values ----
	
	List<String> instances			// all instances of subsets deep
	List<String> definitions0		// shallow nodes
	List<String> definitions		// all nodes in all definitionTrees
	List<String> descendants		// all nodes in childTree
	List<String> ancestors			// all nodes in parentTree
	List<String> subsets			// all nodes in subsetTree
	List<String> supersets			// all nodes in supersetTree
	List<String> attributeVals			// shallow nodes
	List<String> attributes				// shallow nodes
	List<String> attributesNonRef		// shallow nodes
	List<String> referenceKeys			// shallow nodes
	

	
	void init(){

	}

}

class WordDO {
	String text
	List<AliasRelation> aliases
	List<DualRelation> instanceRelations
	List<DualRelation> definitionRelations
	List<DualRelation> childRelations, parentRelations	//generic children & parents
	List<DualRelation> subsetRelations, supersetRelations


	List<GenericRelation> attributeRelations		// key <-> val
	List<GenericNonRefRelation> attributeNonRefRelations	// key --> val, 'val' cannot be referenced

	List<GenericRelation> referenceRelations		//reverse of attributes




}
class Relation{
	String key
	String val
}
class AliasRelation extends Relation{
	String key
	AliasType attr
	String lang
	String val

	void setAttr(String attr) {
		this.attr = AliasType.valueOf(attr)
	}
}
class GenericRelation extends Relation{
	String key
	String attr
	String attrx
	String pred
	Integer vno
	String val

}

class GenericNonRefRelation {
	String key
	String attr
	String attrx
	String pred
	Integer vno
	String valstr
	Integer valnum
	String adv
	Integer time1
	Integer time2
	
	String getVal(){
		valstr?:valnum
	}

}
class GenericRelationDTO{
	String adv
	String pred
	String val
}
class DualRelation extends Relation{
	String key
	DualType attr
	String val

	void setAttr(String attr) {
		this.attr = DualType.valueOf(attr)
	}

	@Override
	public String toString() {
		"$key 's $attr = $val"
	}
	
	
}
class AttrMap{
	String key
}
enum AliasType{
	ABBR,FULL
}

enum DualType{
	INST,	//INST: a instance node, definition-instance relationship
	GECH,	//GECH: a generic child node, composition relationship
	SUBS	//SUBS: a subset node, aggregation relationship
}
