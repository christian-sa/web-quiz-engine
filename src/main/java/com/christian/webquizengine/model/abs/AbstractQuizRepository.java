package com.christian.webquizengine.model.abs;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

public interface AbstractQuizRepository<T extends AbstractQuizEntity, ID extends Serializable>
        extends PagingAndSortingRepository<T, ID> {

}
