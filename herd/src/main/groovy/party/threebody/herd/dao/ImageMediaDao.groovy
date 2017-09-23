package party.threebody.herd.dao

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Repository
import party.threebody.herd.domain.ImageMedia
import party.threebody.skean.core.query.QueryParamsSuite
import party.threebody.skean.jdbc.ChainedJdbcTemplate
import party.threebody.skean.jdbc.rs.DualColsBean
import party.threebody.skean.jdbc.rs.TripleColsBean
import party.threebody.skean.mvc.dao.SinglePKCrudDAO

import java.time.LocalDate

@Repository
class ImageMediaDao extends SinglePKCrudDAO<ImageMedia, String> {

    @Autowired
    ChainedJdbcTemplate cjt

    @Override
    protected String getTable() {
        'hd_media_image'
    }

    @Override
    protected Class<ImageMedia> getBeanClass() {
        ImageMedia.class
    }

    @Override
    protected String getPrimaryKeyColumn() {
        'hash'
    }

    @Override
    protected List<String> getAffectedColumns() {
        null
    }

    List<ImageMedia> listByHashs(Collection<String> hashs) {
        fromTable().by('hash').val([hashs]).list(ImageMedia.class)
    }

    List<ImageMedia> list(QueryParamsSuite qps) {
        def sql = '''
SELECT *,(SELECT size FROM hd_media m WHERE m.hash=mi.hash)
FROM hd_media_image mi
'''
        cjt.fromSql(sql).suite(qps).list(ImageMedia.class)
    }

    List<DualColsBean<LocalDate, Integer>> countByDate() {
        def sql = '''
  SELECT DATE(exif_date_time),COUNT(*) 
  FROM hd_media_image 
  GROUP BY DATE(exif_date_time) 
  ORDER BY DATE(exif_date_time) DESC
'''
        cjt.sql(sql).listOfDualCols(LocalDate.class, Integer.class)
    }

    List<TripleColsBean<Integer, Integer, Integer>> countByMonth() {
        def sql = '''
  SELECT YEAR(exif_date_time),MONTH(exif_date_time),COUNT(*) 
  FROM hd_media_image 
  GROUP BY YEAR(exif_date_time),MONTH(exif_date_time) 
  ORDER BY YEAR(exif_date_time) DESC,MONTH(exif_date_time) DESC
'''
        cjt.sql(sql).listOfTripleCols(Integer.class, Integer.class, Integer.class)
    }

    List<DualColsBean<Integer, Integer>> countByYear() {
        def sql = '''  
  SELECT YEAR(exif_date_time),COUNT(*) 
  FROM hd_media_image 
  GROUP BY YEAR(exif_date_time)
  ORDER BY YEAR(exif_date_time) DESC
'''
        cjt.sql(sql).listOfDualCols(Integer.class, Integer.class)
    }
}
