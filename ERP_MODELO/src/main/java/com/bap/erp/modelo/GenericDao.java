package com.bap.erp.modelo;

import com.iknow.utils.UtilsService;

public interface GenericDao<T> {

    T persist(T t);

    T merge(T t);

    void remove(T t);
    
    void delete(T t);
//    
//    Timestamp getDate();
    T find(Class clazz, Long id);

    Object find(Class clazz, String id);

    UtilsService getUtilsService();
}
