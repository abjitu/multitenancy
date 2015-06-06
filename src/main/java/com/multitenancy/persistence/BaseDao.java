package com.multitenancy.persistence;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseDao<T, PK extends Serializable> {
    protected static final Logger log = LoggerFactory.getLogger(BaseDao.class);

    protected Class<T> persistentClass;
    protected @PersistenceContext EntityManager entityManager;

    public BaseDao(final Class<T> persistentClass) {
        this.persistentClass = persistentClass;
    }

    public T find(PK id) {
        return entityManager.find(persistentClass, id);
    }

    public void persist(T entity) {
        entityManager.persist(entity);
    }

    public T update(T entity) {
        return entityManager.merge(entity);
    }

    public void remove(T entity) {
        entityManager.remove(entity);
    }
    
    public void remove(PK id) {
        entityManager.remove(find(id));
    }
    
    public List<T> findByProperty(String propertyName, Object value) {
        log.debug("finding {} instance with property: {}, value : {}", this.persistentClass, propertyName, value);

        String sql = "from " + this.persistentClass.getName() + " model where model." + propertyName + " = :"
                + propertyName;

        Query query = entityManager.createQuery(sql, persistentClass);
        query.setParameter(propertyName, value);

        @SuppressWarnings("unchecked")
        List<T> list = query.getResultList();

        return list;
    }

    public T findOneByProperty(String propertyName, Object value) {
        log.debug("finding {} instance with property: {}, value : {}", this.persistentClass, propertyName, value);
        T result = null;
        String sql = "from " + this.persistentClass.getName() + " model where model." + propertyName + " = :"
                + propertyName;

        Query query = entityManager.createQuery(sql, persistentClass);
        query.setParameter(propertyName, value);

        @SuppressWarnings("unchecked")
        List<T> list = query.getResultList();
        if (null != list && list.size() == 1) {
            result = list.get(0);
        }

        return result;
    }

    public List<T> findByPropertyAndSortedById(String propertyName, Object value) {
        log.debug("finding {} instance with property: {}, value : {}", this.persistentClass, propertyName, value);

        String sql = "from " + this.persistentClass.getName() + " model where model." + propertyName + " = :"
                + propertyName + " order by model.id desc";

        Query query = entityManager.createQuery(sql, persistentClass);
        query.setParameter(propertyName, value);

        @SuppressWarnings("unchecked")
        List<T> list = query.getResultList();

        return list;
    }

    public List<T> findAll() {
        log.debug("finding all {} instances", this.persistentClass);

        String sql = "from " + this.persistentClass.getName() + " model order by model.id desc";

        Query query = entityManager.createQuery(sql, persistentClass);

        @SuppressWarnings("unchecked")
        List<T> list = query.getResultList();

        return list;
    }
}
