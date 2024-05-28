package com.evaluation.service;

//Not supported exception is for simplicity
public interface DtoCrudService<T, DTO, ID> {

    default DTO save(DTO object) {
        throw new RuntimeException("Not supported");
    }

    default DTO getById(ID id) {
        throw new RuntimeException("Not supported");
    }

    default DTO update(DTO object) {
        throw new RuntimeException("Not supported");
    }

    default void delete(ID id) {
        throw new RuntimeException("Not supported");
    }

}
