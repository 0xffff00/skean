package party.threebody.skean.dict.model
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
	//trees composed of recursive collections above
	TreeNode<String> childTree, parentTree
	TreeNode<String> subsetTree, supersetTree

	//---- computed results -----
	//real key of collections
	List<String> instances			//all instances of subsets
	List<String> definitions		//shallow nodes
	List<String> descendants		//all nodes of childTree
	List<String> ancestors			//all nodes of parentTree
	List<String> subsets			//all nodes in subsetTree
	List<String> supersets			//all nodes in supersetTree
	List<String> attributes			//shallow nodes
	List<String> attributesNonRef	//shallow nodes
	List<String> references			//shallow nodes

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
	List<GenericRelation> attributeNonRefRelations	// key --> val, 'val' cannot be referenced

	List<GenericRelation> referenceRelations		//reverse of attributes




}

class AliasRelation{
	String key
	AliasType attr
	String lang
	String val

	void setAttr(String attr) {
		this.attr = AliasType.valueOf(attr)
	}
}
class GenericRelation{
	String key
	String attr
	String attrx
	String val

}
class DualRelation{
	String key
	private DualType attr
	String val

	void setAttr(String attr) {
		this.attr = DualType.valueOf(attr)
	}
}

enum AliasType{
	ABBR,FULL
}

enum DualType{
	INST,	//INST: a instance node, definition-instance relationship
	GECH,	//GECH: a generic child node, composition relationship
	SUBS	//SUBS: a subset node, aggregation relationship
}

class TreeNode<T>{
	T self
	List<TreeNode<T>> sons

	TreeNode(T self){
		this.self = self
	}
	TreeNode(T self,List<TreeNode<T>> sons){
		this.self = self
		this.sons = sons
	}
}