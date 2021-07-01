package com.christian.webquizengine.model.abs;

import org.springframework.data.domain.Page;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface AbstractQuizService<T extends AbstractQuizEntity, ID extends Serializable> {

    T save(T entity);
    Page<T> findAll(Integer pageNo, Integer pageSize);
    List<T> findAll();
    Optional<T> findById(ID entityId);
    void deleteById(ID entityId);
    void deleteAll();
}
