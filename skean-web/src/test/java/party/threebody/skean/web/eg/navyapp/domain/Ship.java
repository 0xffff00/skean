package party.threebody.skean.web.eg.navyapp.domain;

import party.threebody.skean.data.CreateTime;
import party.threebody.skean.data.LastUpdateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Table(name = "navy_ship")
public class Ship {

    @Id private String code;
    @Column private String name;
    @Column private Integer weight;
    @Column private Integer birthYear;
    @CreateTime private LocalDateTime createTime;
    @LastUpdateTime private LocalDateTime updateTime;

    public Ship() {
    }

    public Ship(String code, String name, Integer weight, Integer birthYear) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.birthYear = birthYear;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(Integer birthYear) {
        this.birthYear = birthYear;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Ship{" +
                "code='" + code + '\'' +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", birthYear=" + birthYear +
                '}';
    }
}
