package com.bap.erp.modelo;

import com.iknow.utils.UtilsService;
import com.iknow.utils.ValidationUtils;
import java.sql.Timestamp;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateTemplate;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    @Autowired
    protected HibernateTemplate hibernateTemplate;
    protected UtilsService utilsService = new ValidationUtils();


    public T persist(T t) {        
        hibernateTemplate.persist(t);
        return t;
    }

    public T merge(T t) {
        hibernateTemplate.merge(t);
        return t;
    }

    public void remove(T t) {
        hibernateTemplate.merge(t);
    }
    
    public void delete(T t) {
        hibernateTemplate.delete(t);
    }

    public Timestamp getDate() {
        return new Timestamp(new Date().getTime());
    }

    @Override
    public T find(Class clazz, Long id) {
        return (T) hibernateTemplate.get(clazz, id);
    }

    @Override
    public Object find(Class clazz, String id) {
        return hibernateTemplate.get(clazz, id);
    }

    @Override
    public UtilsService getUtilsService() {
        return utilsService;
    }
}
