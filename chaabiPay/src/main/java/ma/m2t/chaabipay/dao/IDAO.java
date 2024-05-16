package ma.m2t.chaabipay.dao;

import java.util.List;

public interface IDAO<T> {
    T save(T o);
    List<T> findAll();
    T findById(int id);
    void update(T o);
    void delete(T o);

}

