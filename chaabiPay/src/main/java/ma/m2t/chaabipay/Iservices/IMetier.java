package ma.m2t.chaabipay.Iservices;

import ma.m2t.chaabipay.metier.IAbstract;

import java.util.List;

public interface IMetier <T extends IAbstract>{
    T save(T object);

    T update(T object);

    void delete(T object);

    List<T> findAll();

    T findById(Long id);
}
