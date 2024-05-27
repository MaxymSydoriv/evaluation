package com.evaluation.service;

//Not supported exception is for simplicity
public interface DtoCrudService<T, DTO, ID> {

    default T save(DTO object) {
        throw new RuntimeException("Not supported");
    }
    default T getById(ID id) {
        throw new RuntimeException("Not supported");
    }
    default T update(DTO object) {
        throw new RuntimeException("Not supported");
    }
    default T delete(ID id) {
        throw new RuntimeException("Not supported");
    }

}
