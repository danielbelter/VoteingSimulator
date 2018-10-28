package com.example.repository.generic;

import com.example.exceptions.MyException;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public abstract class AbstractGenericRepository<T> implements GenericRepository<T> {

    @PersistenceContext
    private EntityManager entityManager;

    private Class<T> entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    @Override
    public void addOrUpdate(T t) {
        try {
            entityManager.merge(t);
        } catch (Exception e) {
            throw new MyException("REPOSITORY, ADD OR UPDATE, " + entityClass.getCanonicalName(), LocalDateTime.now());
        }
    }

    @Override
    public void delete(Long id) {
        try {
            T t = entityManager.find(entityClass, id);
            entityManager.remove(t);
        } catch (Exception e) {
            throw new MyException("REPOSITORY, DELETE, " + entityClass.getCanonicalName(), LocalDateTime.now());
        }
    }

    @Override
    public Optional<T> findById(Long id) {
        try {
            return Optional.of(entityManager.find(entityClass, id));
        } catch (Exception e) {
            throw new MyException("REPOSITORY, FIND BY ID, " + entityClass.getCanonicalName(), LocalDateTime.now());
        }
    }

    @Override
    public List<T> findAll() {
        try {
            Query query = entityManager.createQuery("select t from " + entityClass.getCanonicalName() + " t", entityClass);
            return query.getResultList();
        } catch (Exception e) {
            throw new MyException("REPOSITORY, FIND ALL, " + entityClass.getCanonicalName(), LocalDateTime.now());
        }
    }
}