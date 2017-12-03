package party.threebody.skean.dict.domain;

import party.threebody.skean.data.LastUpdateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * <h2>Basic Conception</h2>
 * Typically, ER Model, DAG, Domain Layer has accurate mappings:<br>
 * <li>Entity = Vertex = Word::getText(String type)</li>
 * <li>Relationship = Edge = Relation class</li>
 * <p>
 * <h2>Coding Naming Conventions</h2>
 * <li>ES0, EntSet0: Entity Set shallow</li>
 * <li>ESR, EntSetR: Entity Set recursive</li>
 * <li>RS0, RelSet0: Relation Set shallow</li>
 * <li>RSR, RelSetR: Relation Set recursive</li>
 * <li>ESA: Entity Set all </li>
 * <p>
 * <h2>Explanation of Fields</h2>
 * <pre>
 * ----------------------------------------------------
 * these fields can be drawn as:
 *  src ==[attr]==> dst
 * so, for example:
 * - BasicRelation：
 * officalName ==[ALIA]==> me ==[ALIA]==> alias
 * subset      ==[SUBS]==> me ==[SUBS]==> subset
 * definition  ==[INST]==> me ==[INST]==> instance
 * supertopic  ==[TOPI]==> me ==[TOPI]==> subtopic
 * - X1Relation:
 * reference ==[*]==> me ==[*]==> attribute
 * ----------------------------------------------------
 * </pre>
 *
 * @since 0.3
 */
@Table(name = "dct_word")
public class Word {

    @Id private String text;
    @Column private String desc;
    @Column private String state;
    @LastUpdateTime private LocalDateTime updateTime;

    private Collection<BasicRelation> aliasRS0;

    private Collection<BasicRelation> subsetRSR;
    private Collection<BasicRelation> supersetRSR;
    private Collection<BasicRelation> instanceRS0;
    private Collection<String> instanceESA;      //all instES=(me+subsetESR)'s instES0
    private Collection<BasicRelation> definitionRS0;
    private Collection<String> definitionESA;         //all defES= defES0's supersetESR + defES0
    private Collection<BasicRelation> subtopicRSR;
    private Collection<BasicRelation> supertopicRSR;

    private Collection<X1Relation> attributeRS0;
    private Collection<X1Relation> referenceRS0;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Collection<BasicRelation> getAliasRS0() {
        return aliasRS0;
    }

    public void setAliasRS0(Collection<BasicRelation> aliasRS0) {
        this.aliasRS0 = aliasRS0;
    }

    public Collection<BasicRelation> getSubsetRSR() {
        return subsetRSR;
    }

    public void setSubsetRSR(Collection<BasicRelation> subsetRSR) {
        this.subsetRSR = subsetRSR;
    }

    public Collection<BasicRelation> getSupersetRSR() {
        return supersetRSR;
    }

    public void setSupersetRSR(Collection<BasicRelation> supersetRSR) {
        this.supersetRSR = supersetRSR;
    }

    public Collection<BasicRelation> getInstanceRS0() {
        return instanceRS0;
    }

    public void setInstanceRS0(Collection<BasicRelation> instanceRS0) {
        this.instanceRS0 = instanceRS0;
    }

    public Collection<String> getInstanceESA() {
        return instanceESA;
    }

    public void setInstanceESA(Collection<String> instanceESA) {
        this.instanceESA = instanceESA;
    }

    public Collection<BasicRelation> getDefinitionRS0() {
        return definitionRS0;
    }

    public void setDefinitionRS0(Collection<BasicRelation> definitionRS0) {
        this.definitionRS0 = definitionRS0;
    }

    public Collection<String> getDefinitionESA() {
        return definitionESA;
    }

    public void setDefinitionESA(Collection<String> definitionESA) {
        this.definitionESA = definitionESA;
    }

    public Collection<BasicRelation> getSubtopicRSR() {
        return subtopicRSR;
    }

    public void setSubtopicRSR(Collection<BasicRelation> subtopicRSR) {
        this.subtopicRSR = subtopicRSR;
    }

    public Collection<BasicRelation> getSupertopicRSR() {
        return supertopicRSR;
    }

    public void setSupertopicRSR(Collection<BasicRelation> supertopicRSR) {
        this.supertopicRSR = supertopicRSR;
    }

    public Collection<X1Relation> getAttributeRS0() {
        return attributeRS0;
    }

    public void setAttributeRS0(Collection<X1Relation> attributeRS0) {
        this.attributeRS0 = attributeRS0;
    }

    public Collection<X1Relation> getReferenceRS0() {
        return referenceRS0;
    }

    public void setReferenceRS0(Collection<X1Relation> referenceRS0) {
        this.referenceRS0 = referenceRS0;
    }
}
