package repositories;

public interface Repository<T, ID> {
    T findById(ID id);
}
