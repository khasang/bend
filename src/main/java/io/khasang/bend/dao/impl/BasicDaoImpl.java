package io.khasang.bend.dao.impl;

import io.khasang.bend.dao.BasicDao;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
public class BasicDaoImpl<T> implements BasicDao<T> {
    private final Class<T> entityClass;
    protected SessionFactory sessionFactory;

    public BasicDaoImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T add(T entity) {
        getSession().save(entity);
        return entity;
    }

    @Override
    public T update(T entity) {
        getSession().update(entity);
        return entity;
    }

    @Override
    public T delete(T entity) {
        getSession().delete(entity);
        return entity;
    }

    @Override
    public T getById(long id) {
        return getSession().get(entityClass, id);
    }

    @Override
    public List<T> getAll() {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(root);
        return getSession().createQuery(criteriaQuery).list();
    }

    @Override
    public Set<T> getAllSet() {
        CriteriaBuilder builder = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = builder.createQuery(entityClass);
        Root<T> root = criteriaQuery.from(entityClass);

        criteriaQuery.select(root);
        return new HashSet<>(getSession().createQuery(criteriaQuery).list());
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
