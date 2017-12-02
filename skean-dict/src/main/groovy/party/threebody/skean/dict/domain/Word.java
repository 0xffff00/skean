package party.threebody.skean.dict.domain;

import party.threebody.skean.data.LastUpdateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.Set;

@Table(name = "dct_word")
public class Word {

    @Id private String text;
    @Column private String desc;
    @LastUpdateTime private LocalDateTime updateTime;

    //	Set<String> instancesDeep		//recursive
    //	Set<String> definitions			//self <--(INST)-- defs <--(SUBS)-- all super defs

    //	Set<String> supersetsDeep		//recursive
    //	Set<String> subsetsDeep			//recursive
    //	Set<String> aliases				//recursive

    //4 Rels involved. Each rel list represents a DAG which current node can be from or to
    private Set<AliasRel> aliasRels;
    private Set<DualRel> dualRels;
    private Set<Ge1Rel> ge1Rels;
    private Set<Ge2Rel> ge2Rels;

    private String status;

}
