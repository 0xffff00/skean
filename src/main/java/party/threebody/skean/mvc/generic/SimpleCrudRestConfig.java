package party.threebody.skean.mvc.generic;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import party.threebody.skean.core.query.QueryParamsSuite;

public class SimpleCrudRestConfig<T, PK> {

	private Function<PK, T> oneReader;
	private Function<QueryParamsSuite, List<T>> listReader;
	private Function<QueryParamsSuite, Integer> countReader;
	private Function<T, T> creator;
	private BiFunction<T, PK, Integer> updater;
	private Function<PK, Integer> deleter;
	private GenericCrudDAO<T, PK> crudDao;

	public SimpleCrudRestConfig(Function<PK, T> oneReader, Function<QueryParamsSuite, List<T>> listReader,
			Function<QueryParamsSuite, Integer> countReader, Function<T, T> creator, BiFunction<T, PK, Integer> updater,
			Function<PK, Integer> deleter) {
		this.oneReader = oneReader;
		this.listReader = listReader;
		this.countReader = countReader;
		this.creator = creator;
		this.updater = updater;
		this.deleter = deleter;
	}

	public SimpleCrudRestConfig(GenericCrudDAO<T, PK> crudDao) {
		this.crudDao = crudDao;
		this.oneReader = crudDao::readOne;
		this.listReader = crudDao::readList;
		this.countReader = crudDao::readCount;
		this.creator = crudDao::create;
		this.updater = crudDao::update;
		this.deleter = crudDao::delete;

	}

	public SimpleCrudRestConfig(GenericCrudDAO<T, PK> crudDao, GenericCrudDaoTemplateService s) {
		this.crudDao = crudDao;
		if (crudDao != null) {
			this.oneReader = pk -> s.readOne(crudDao, pk);
			this.listReader = qps -> s.readList(crudDao, qps);
			this.countReader = qps -> s.readCount(crudDao, qps);
			this.creator = e -> s.create(crudDao, e);
			this.updater = (e, pk) -> s.update(crudDao, e, pk);
			this.deleter = pk -> s.delete(crudDao, pk);
		}
	}

	public SimpleCrudRestConfig(String table, String[] pkCols, Class<T> beanClass) {
		AbstractCrudDAO<T, PK> dao = new AbstractCrudDAO<T, PK>() {

			@Override
			protected String getTable() {
				return table;
			}

			@Override
			protected Class<T> getBeanClass() {
				return beanClass;
			}

			@Override
			protected String[] getPkCol() {
				return pkCols;
			}

		};
		this.crudDao = dao;
	}

	public Function<PK, T> getOneReader() {
		return oneReader;
	}

	public Function<QueryParamsSuite, List<T>> getListReader() {
		return listReader;
	}

	public Function<QueryParamsSuite, Integer> getCountReader() {
		return countReader;
	}

	public Function<T, T> getCreator() {
		return creator;
	}

	public BiFunction<T, PK, Integer> getUpdater() {
		return updater;
	}

	public Function<PK, Integer> getDeleter() {
		return deleter;
	}

	public GenericCrudDAO<T, PK> getCrudDao() {
		return crudDao;
	}

}
