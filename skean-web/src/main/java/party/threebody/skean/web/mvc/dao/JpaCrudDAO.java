package party.threebody.skean.web.mvc.dao;

import org.springframework.core.GenericTypeResolver;
import org.springframework.core.annotation.AnnotationUtils;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JpaCrudDAO<E> extends AbstractCrudDAO<E> {

    @Override
    protected String getTable() {
        Table annoTable = getEntityClass().getAnnotation(Table.class);
        if (annoTable != null) {
            return annoTable.name();
        }
        return null;
    }

    @Override
    protected Class<E> getEntityClass() {
        return (Class<E>) GenericTypeResolver.resolveTypeArgument(getClass(), JpaCrudDAO.class);
    }



    @Override
    protected List<String> getPrimaryKeyColumns() {
        return Stream.of(getEntityClass().getDeclaredFields())
                .filter(f -> f.getAnnotation(Id.class) != null)
                .map(Field::getName)
                .collect(Collectors.toList());
    }

    @Override
    protected List<String> getAffectedColumns() {
        return Stream.of(getEntityClass().getDeclaredFields())
                .filter(JpaCrudDAO::canBeAffected)
                .map(Field::getName)
                .collect(Collectors.toList());

    }

    static boolean canBeAffected(Field field) {
        return AnnotationUtils.findAnnotation(field, Id.class) != null
                || AnnotationUtils.findAnnotation(field, Column.class) != null;
    }
}
