package ma.m2t.chaabipay.services;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.m2t.chaabipay.Iservices.IMetier;
import ma.m2t.chaabipay.metier.IAbstract;
import ma.m2t.chaabipay.repository.GenericRepository;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Transactional
@AllArgsConstructor
public class GenericServices <T extends IAbstract> implements IMetier<T> {
private GenericRepository<T> genericRepository;

    @Override
    public T save(T object) {
        return genericRepository.save(object);
    }

    @Override
    public T update(T object) {
        if(!Objects.isNull(object.getId())){
            T subObject = genericRepository.save(object);
            subObject.setUpdateDate(new Date());
            return subObject;
        }

        return null;
    }

    @Override
    public void delete(T object) {
        T subObject= this.findById(object.getId());
        subObject.setDeleteDate(new Date());
    }

    @Override
    public List<T> findAll() {
        return genericRepository.findAll().stream().filter(t-> t.getDeleteDate() ==null).toList();
    }

    @Override
    public T findById(Long id) {
        return genericRepository.findById(id).orElse(null);
    }
/*
    public void deleteById(Long aLong) {
        genericRepository.deleteById(aLong);
    }*/
}