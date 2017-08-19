package party.threebody.skean.dict.model

import java.time.LocalDateTime

import party.threebody.skean.util.Dates

/**
 * 
 * @author hzk
 * @since 2017-08-05
 */
class Word extends WordDO{


	//	Set<String> instancesDeep		//recursive
	//	Set<String> definitions			//self <--(INST)-- defs <--(SUBS)-- all super defs

	//	Set<String> supersetsDeep		//recursive
	//	Set<String> subsetsDeep			//recursive
	//	Set<String> aliases				//recursive

	//4 Rels involved. Each rel list represents a DAG which current node can be from or to
	Set<AliasRel> aliasRels
	Set<DualRel> dualRels
	Set<Ge1Rel> ge1Rels
	Set<Ge2Rel> ge2Rels

	boolean isTemp

}

class WordDO {
	String text
	String desc
	Date lopTime

	public LocalDateTime getLastOptTime(){
		return Dates.toLocalDateTime(lopTime);
	}

	public void setLocalDateTime(LocalDateTime ldt){
		lopTime=Dates.toDate(ldt)
	}
}
class Rel{
	
	public static int VNO_BATCH = -1
	String key
	String val
	String attr
	Integer vno
	String adv

	@Override
	public int hashCode() {
		Objects.hashCode(key) ^ Objects.hashCode(val) ^ Objects.hashCode(attr) ^ Objects.hashCode(vno)
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj){
			return true;
		}
		if (obj == null || getClass() != obj.getClass()){
			return false;
		}
		GeRel o = (GeRel) obj;
		return Objects.equals(key,o.key	) && Objects.equals(val,o.val)&& Objects.equals(vno,o.vno) && Objects.equals(attr,o.attr)
	}

	@Override
	public String toString() {
		"$key 's ${attr} = $val"
	}
}

class GeRel extends Rel{
	String attrx
	String pred
	Integer time1
	Integer time2

	@Override
	public String toString() {
		"$key 's ${attr} $pred $val"
	}
}



class SpRel extends Rel{
}

class AliasRel extends SpRel{
	String lang
}


class Ge1Rel extends GeRel{

	
}

class Ge2Rel extends GeRel {

	String valstr
	Integer valnum
	String valmu

	String getVal(){
		valstr?:valnum
	}
}

class DualRel extends SpRel{
}

