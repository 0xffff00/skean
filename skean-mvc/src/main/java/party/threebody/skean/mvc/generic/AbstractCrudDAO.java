package party.threebody.skean.mvc.generic;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import party.threebody.skean.SkeanException;
import party.threebody.skean.core.query.QueryParamsSuite;
import party.threebody.skean.jdbc.ChainedJdbcTemplate;
import party.threebody.skean.jdbc.phrase.FromPhrase;
import party.threebody.skean.util.Maps;
import party.threebody.skean.util.ObjectMappers;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param <T>  type of the entity bean
 * @param <PK> type of the primary key(s), usually a String, String[] or Map
 * @author hzk
 * @since 2017-08-01
 */
public abstract class AbstractCrudDAO<T, PK> implements GenericCrudDAO<T, PK> {

    @Autowired
    ChainedJdbcTemplate cjt;

    protected abstract String getTable();

    protected abstract Class<T> getBeanClass();

    protected abstract List<String> getPrimaryKeyColumns();

    /**
     * if return null, build actual AffectedColumns by properties of the bean
     *
     * @return
     */
    protected abstract List<String> getAffectedColumns();

    private FromPhrase fromTable() {
        return cjt.from(getTable());
    }

    @SuppressWarnings("unchecked")
    protected Map<String, Object> convertBeanToMap(T bean) {
        if (bean == null) {
            return null;
        }
        Map<String, Object> objectAsMap = new HashMap<String, Object>();
        BeanInfo info = null;
        try {
            info = Introspector.getBeanInfo(bean.getClass());

            for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
                Method reader = pd.getReadMethod();
                if (reader != null){
                    objectAsMap.put(pd.getName(), reader.invoke(bean));
                }
            }
            return objectAsMap;
        } catch (IllegalAccessException|InvocationTargetException |IntrospectionException e) {
           throw new SkeanException("convertBeanToMap failed.",e);
        }
    }

    @Override
    public T create(T entity) {
        Map<String, Object> propsMap = convertBeanToMap(entity);
        fromTable().affect(propsMap).insert();
        return fromTable().by(getPKColsArr()).val(propsMap).limit(1).first(getBeanClass());

    }

    @Override
    public List<T> readList(QueryParamsSuite qps) {
        return fromTable().suite(qps).list(getBeanClass());
    }

    @Override
    public int readCount(QueryParamsSuite qps) {
        return fromTable().suite(qps).count();
    }

    @Override
    public T readOne(PK pk) {
        return fromTable().by(getPKColsArr()).val(pk).limit(1).first(getBeanClass());
    }

    @Override
    public int update(T entity, PK pk) {
        Map<String, Object> propsMap = convertBeanToMap(entity);
        return fromTable().affect(propsMap).by(getPKColsArr()).val(pk).update();
    }

    @Override
    public int delete(PK pk) {
        return fromTable().by(getPKColsArr()).val(pk).delete();
    }

    private String[] getPKColsArr() {
        return getPrimaryKeyColumns().toArray(new String[0]);
    }
}
