package party.threebody.skean.dict.model
/**
 * for example :<br>
 * suppose there is a Word texted as 'China'<br>
 * query algorithms of the following collections are:<br>
 * <pre>
 * instances: 	key='China',attr='INST' -> val 
 * definitions:	val='China',attr='INST' -> key 
 * children:	key='China',attr='GECH' -> val 
 * parents:		val='China',attr='GECH' -> key 
 * subsets:		key='China',attr='SUBS' -> val 
 * supersets:	val='China',attr='SUBS' -> key 
 * attributes:	key='China' -> attr,attrx,val 
 * references:	val='China' -> attr,attrx,key 
 * aliases:		key='China' -> val  
 * </pre>
 * 
 * 
 * @author hzk
 * @since 2017-07-23
 */
class Word {
	String text
	List<AliasRelation> aliases
	List<DualRelation> instances, definitions
	List<DualRelation> children, parents	//generic children & parents
	List<DualRelation> subsets, supersets
	
	
	List<GenericRelation> attributes		// key <-> val
	List<GenericRelation> attributesNonRef	// key --> val, 'val' cannot be referenced
	
	List<GenericRelation> references		//reverse of attributes
	
	List<String> getChildrenTexts(){
		children.collect{ it.val }
	}
	List<String> getParentsTexts(){
		children.collect{ it.key }
	}
	
	List<String> getDefinitionsTexts(){
		children.collect{ it.val }
	}
	List<String> getAliasesTexts(){
		children.collect{ it.val }
	}
	
	
}
class AliasRelation{
	String key
	AliasType attr
	String lang
	String val
}
class GenericRelation{
	String key
	String attr
	String attrx
	String val
}
class DualRelation{
	String key
	DualType attr
	String val
}

enum AliasType{
	ABBR,FULL
}
enum DualType{
	INSTANCE,	//a instance node, definition-instance relationship
	CHILD,		//a generic child node, composition relationship
	SUBSET		//a subset node, aggregation relationship
}