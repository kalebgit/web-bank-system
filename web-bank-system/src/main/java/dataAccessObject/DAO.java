package dataAccessObject;

public interface DAO<E, K> {
	
	public abstract boolean insert(E element);
	public abstract boolean update(E element);
	public abstract boolean delete(E element);
	public abstract E getSingle(K key) throws Exception;
}
