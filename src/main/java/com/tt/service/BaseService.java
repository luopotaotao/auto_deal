package com.tt.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by tt on 2016/10/17.
 */
public interface BaseService<T> {
    default T get(Serializable id){
        throw new  UnsupportedOperationException();
    }
    default List<T> list(Map<String, Object> params, Integer page, Integer pageSize){
        throw new  UnsupportedOperationException();
    }
    default List<T> list(Map<String, Object> params){
        throw new  UnsupportedOperationException();
    }
    default long count(Map<String, Object> params){
        throw new  UnsupportedOperationException();
    }
    default T add(T instance){
        throw new  UnsupportedOperationException();
    }
    default int del(List<Integer> ids){
        throw new  UnsupportedOperationException();
    }
    default T update(T instance){
        throw new  UnsupportedOperationException();
    }
}
