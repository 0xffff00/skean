package party.threebody.skean.lang.groovy

import party.threebody.skean.data.CreateTime
import party.threebody.skean.data.LastUpdateTime
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.web.mvc.dao.SinglePKJpaCrudDAO
import party.threebody.skean.web.mvc.dao.legacy.LegacySinglePKJpaCrudDAO

import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.Table
import java.time.LocalDateTime

class GroovyLegacyCrudDaoTests {
}

class ShipCrudDao extends SinglePKJpaCrudDAO<Ship,String> {

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return null
    }
}

class LegacyShipCrudDao extends LegacySinglePKJpaCrudDAO<Ship,String>{

    @Override
    ChainedJdbcTemplate getChainedJdbcTemplate() {
        return null
    }
}


@Table(name = "navy_ship")
class Ship {

    @Id private String code;
    @Column private String name;
    @Column private Integer weight;
    @Column private Integer birthYear;
    @CreateTime private LocalDateTime createTime;
    @LastUpdateTime private LocalDateTime updateTime;
}
