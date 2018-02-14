package party.threebody.skean.samples.navyapp.domain;

import party.threebody.skean.data.LastUpdateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Table(name = "navy_fleet")
public class Fleet {

    @Id String countryCode;
    @Id String code;
    @Column String name;
    @Column String leaderName;

    @LastUpdateTime LocalDateTime updateTime;

    List<Ship> ships;

    public Fleet() {
    }

    public Fleet(String countryCode, String code, String name, String leaderName, List<Ship> ships) {
        this.countryCode = countryCode;
        this.code = code;
        this.name = name;
        this.leaderName = leaderName;
        this.ships = ships;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
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

    public String getLeaderName() {
        return leaderName;
    }

    public void setLeaderName(String leaderName) {
        this.leaderName = leaderName;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public List<Ship> getShips() {
        return ships;
    }

    public void setShips(List<Ship> ships) {
        this.ships = ships;
    }
}
