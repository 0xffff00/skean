package party.threebody.skean.web.eg.navyapp.domain;

import party.threebody.skean.data.CreateTime;
import party.threebody.skean.data.LastUpdateTime;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;

@Table(name="navy_fleet")
public class Fleet {

    @Id String countryCode;
    @Id String code;
    @Column String name;
    @Column String leaderName;

    @LastUpdateTime LocalDateTime updateTime;

    List<Ship2> ships;

}
