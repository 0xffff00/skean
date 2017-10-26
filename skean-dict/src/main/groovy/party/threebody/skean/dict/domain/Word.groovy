package party.threebody.skean.dict.domain

import party.threebody.skean.data.LastUpdateTime

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import java.time.LocalDateTime

/**
 *
 * @author hzk
 * @since 2017-08-05
 */
@Table(name = 'dct_word')
class Word {

    @Id String text
    @Column String desc
    @LastUpdateTime LocalDateTime updateTime

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


class Rel {
    public static int VNO_BATCH = -1
    @Id String key
    @Id String val
    @Id Integer vno
    @Column String attr
    @Column String adv

    @Override
    public int hashCode() {
        Objects.hashCode(key) ^ Objects.hashCode(val) ^ Objects.hashCode(attr) ^ Objects.hashCode(vno)
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        GeRel o = (GeRel) obj;
        return Objects.equals(key, o.key) && Objects.equals(val, o.val) && Objects.equals(vno, o.vno) && Objects.equals(attr, o.attr)
    }

    @Override
    public String toString() {
        "$key 's ${attr} = $val"
    }
}

class GeRel extends Rel {
    @Column String attrx
    @Column String pred
    @Column Integer time1
    @Column Integer time2

    @Override
    public String toString() {
        "$key 's ${attr} $pred $val"
    }
}


class SpRel extends Rel {
}

class AliasRel extends SpRel {
    @Column String lang
}

@Table(name = 'dct_rel_ge_dat1')
class Ge1Rel extends GeRel {


}

@Table(name = 'dct_rel_ge_dat2')
class Ge2Rel extends GeRel {
    @Column String valstr
    @Column Integer valnum
    @Column String valmu

    String getVal() {
        valstr ?: valnum
    }
}

@Table(name = 'dct_rel_sp_dual')
class DualRel extends SpRel {
}

