package party.threebody.skean.dict.domain


import java.time.LocalDateTime
class Word{

    Set<Alias> alias
    Set<>
}

class Alias {
    String text;
    String lang;
}
class Topic{
    String text;
    String
}
class Word1 extends WordDO {

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
    LocalDateTime saveTime
}

class Rel {

    public static int VNO_BATCH = -1
    String key
    String val
    String attr
    Integer vno
    String adv


}

class GeRel extends Rel {
    String attrx
    String pred
    Integer time1
    Integer time2

}


class SpRel extends Rel {
}

class AliasRel extends SpRel {
    String lang
}


class Ge1Rel extends GeRel {


}

class Ge2Rel extends GeRel {

    String valstr
    Integer valnum
    String valmu

}

class DualRel extends SpRel {
}

