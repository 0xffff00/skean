package party.threebody.skean.dict.domain;

import javax.persistence.Column;
import javax.persistence.Id
import javax.persistence.Table;

/**
 * A Relation is like a arrowed line between 2 vertice.
 * Typically, the very mapped database table should have:
 * 1. composed primary keys: ('key','val','vno');
 * 2. indexes: ('key','val','vno'), ('val');
 */
class Relation {
    @Id String key         // key, the source vertex
    @Id String attr    // attribute of 'key'
    @Id Integer vno        // ordinal number to display
    @Column String val         // value, the target vertex
    @Column String adv     // adverb, complement of 'attr'

    @Override
    int hashCode() {
        Objects.hashCode(key) ^ Objects.hashCode(val) ^ Objects.hashCode(attr) ^ Objects.hashCode(vno)
    }

    @Override
    boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        Relation o = (Relation) obj;
        return Objects.equals(key, o.key) && Objects.equals(val, o.val) && Objects.equals(vno, o.vno) && Objects.equals(attr, o.attr)
    }
    @Override
    String toString() {
        "$key 's ${attr} = $val"
    }
}

/**
 * <h1>Basic Relation</h1>
 * 'attr' has only 4 legal values:
 * ALIA - alias
 * SUBS - subset
 * GECH - generic child
 * INST - instance
 */
@Table(name="dct_rel_b")
class BasicRelation extends Relation{

}
/**
 * <h1>Relation Extended 1</h1>
 * 'pred' has only 3 legal values: 'IS','ARE','HAS'
 * IS - equals the very 'val'
 * ARE - equals a list of 'val's ordered by 'vno'
 * HAS - just has or contains, but maybe miss some 'val's in record.
 */
@Table(name="dct_rel_x1")
class X1Relation extends Relation{
    @Column String attrx
    @Column String pred     //predicate, can only be: 'IS','ARE','HAS'
    @Column Integer time1   // usually as startTime
    @Column Integer time2   // usually as endTime
    // v2 columns are used to store unlinked data
    @Column String v2txt      //text value, may be large, mshould not be indexed
    @Column BigDecimal v2num      //numeric value
    @Column String v2mu        //measure unit
}