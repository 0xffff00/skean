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

    private Collection<BasicRelation> aliasRelSet0;

    private Collection<BasicRelation> subsetRelSetR;
    private Collection<BasicRelation> supersetRelSetR;
    private Collection<BasicRelation> instancesRelSetR;
    private Collection<BasicRelation> subtopicRelSetR;
    private Collection<BasicRelation> supertopicRelSetR;
    private Collection<BasicRelation> definitionsRelSetR;

    private Collection<X1Relation> attributeRelSet0;
    private Collection<X1Relation> referenceRelSet0;

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

    public Collection<BasicRelation> getAliasRelSet0() {
        return aliasRelSet0;
    }

    public void setAliasRelSet0(Collection<BasicRelation> aliasRelSet0) {
        this.aliasRelSet0 = aliasRelSet0;
    }

    public Collection<BasicRelation> getSubsetRelSetR() {
        return subsetRelSetR;
    }

    public void setSubsetRelSetR(Collection<BasicRelation> subsetRelSetR) {
        this.subsetRelSetR = subsetRelSetR;
    }

    public Collection<BasicRelation> getSupersetRelSetR() {
        return supersetRelSetR;
    }

    public void setSupersetRelSetR(Collection<BasicRelation> supersetRelSetR) {
        this.supersetRelSetR = supersetRelSetR;
    }

    public Collection<BasicRelation> getInstancesRelSetR() {
        return instancesRelSetR;
    }

    public void setInstancesRelSetR(Collection<BasicRelation> instancesRelSetR) {
        this.instancesRelSetR = instancesRelSetR;
    }

    public Collection<BasicRelation> getSubtopicRelSetR() {
        return subtopicRelSetR;
    }

    public void setSubtopicRelSetR(Collection<BasicRelation> subtopicRelSetR) {
        this.subtopicRelSetR = subtopicRelSetR;
    }

    public Collection<BasicRelation> getSupertopicRelSetR() {
        return supertopicRelSetR;
    }

    public void setSupertopicRelSetR(Collection<BasicRelation> supertopicRelSetR) {
        this.supertopicRelSetR = supertopicRelSetR;
    }

    public Collection<BasicRelation> getDefinitionsRelSetR() {
        return definitionsRelSetR;
    }

    public void setDefinitionsRelSetR(Collection<BasicRelation> definitionsRelSetR) {
        this.definitionsRelSetR = definitionsRelSetR;
    }

    public Collection<X1Relation> getAttributeRelSet0() {
        return attributeRelSet0;
    }

    public void setAttributeRelSet0(Collection<X1Relation> attributeRelSet0) {
        this.attributeRelSet0 = attributeRelSet0;
    }

    public Collection<X1Relation> getReferenceRelSet0() {
        return referenceRelSet0;
    }

    public void setReferenceRelSet0(Collection<X1Relation> referenceRelSet0) {
        this.referenceRelSet0 = referenceRelSet0;
    }
}
