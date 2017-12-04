package party.threebody.skean.dict.domain

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import java.time.LocalDateTime

/**
 * A Relation is like a arrowed edge between 2 vertice in DAG<br>
 * Typically, the very mapped database table should have:
 * <ol>
 * <li> composed primary keys: ('src','attr','no');</li>
 * <li> indexes:  ('src','attr','no'), ('dst');</li>
 * </ol>
 *
 * Relations can be read as a complete sentence like natural language:<br>
 * <ol>
 *  <li> {src}'s {attr} = {dst} </li>
 *  <li> {src}'s {attrx}{attr}{pred} [{dst}/{dstTxt}/{dstNum}{dstMu}] from {time1} to {time2}, {adv} </li>
 * </ol>
 * @since 0.3
 * */
abstract class AttrBasedRelation {
    @Id String src          // the source vertex
    @Id String attr         // attribute of 'src'
    @Id Integer no          // ordinal number to display
    @Column String dst      // the destination vertex
    @Column String memo

    @Override
    int hashCode() {
        Objects.hashCode(src) ^ Objects.hashCode(attr) ^ Objects.hashCode(no)
    }

    @Override
    boolean equals(Object obj) {
        if (this == obj) {
            return true
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false
        }
        AttrBasedRelation o = (AttrBasedRelation) obj
        return Objects.equals(src, o.src) && Objects.equals(no, o.no) && Objects.equals(attr, o.attr)
    }

    @Override
    String toString() {
        "$src 's $attr = $dst"
    }
}

/**
 * <h1>Basic Relation</h1>
 *
 * 'attr' has only these legal values:
 * <ol>
 * <li>ALIA - alias</li>
 * <li>SUBS - subset</li>
 * <li>SUBT - topic</li>
 * <li>INST - instance</li>
 * </ol>
 */
@Table(name = "dct_rel_b")
class BasicRelation extends AttrBasedRelation {

}
/**
 * <h1>Relation Extended 1</h1>
 * 'pred' has only 3 legal values: 'IS','ARE','HAS'
 * <ol>
 * <li>IS - equals the very 'dst'</li>
 * <li>ARE - equals a list of 'dst's ordered by 'no'</li>
 * <li>HAS - just has or contains, but maybe miss some 'dst's in record.</li>
 * </ol>
 */
@Table(name = "dct_rel_x1")
class X1Relation extends AttrBasedRelation {
    @Column String attrx            // adj of 'attr', complement of 'attr'
    @Column String pred             // verb, can only be: 'IS','ARE','HAS'
    @Column String adv              // adverb, complement of the whole sentence
    @Column LocalDateTime time1     // usually as startTime
    @Column LocalDateTime time2     // usually as endTime

    // dstTxt,dstNum,dstMu are used to store unlinked data
    @Column String dstTxt           //text value of 'dst', may be large, mshould not be indexed
    @Column BigDecimal dstNum       //numeric value of 'dst'
    @Column String dstMu            //measure unit of 'dst'

    @Override
    String toString() {
        def s0 = dst ? dst : dstTxt
        s0 = s0 ? s0 : "$dstNum$dstMu"
        def s1 = time1 ? "from $time1 " : ''
        def s2 = time2 ? "to $time2" : ''
        def s3 = adv ? ", $adv" : ''
        "$src's $attrx $attr $pred $s0 $s1$s2$s3"
    }
}