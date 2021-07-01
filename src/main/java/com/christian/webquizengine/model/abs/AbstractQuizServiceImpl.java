package com.christian.webquizengine.model.abs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Service
public class AbstractQuizServiceImpl<T extends AbstractQuizEntity, ID extends Serializable>
        implements AbstractQuizService<T, ID> {

    protected final AbstractQuizRepository<T, ID> abstractQuizRepository;

    @Autowired
    public AbstractQuizServiceImpl(AbstractQuizRepository<T, ID> abstractQuizRepository) {
        this.abstractQuizRepository = abstractQuizRepository;
    }

    @Override
    public T save(T entity) {
        return abstractQuizRepository.save(entity);
    }

    @Override
    public Page<T> findAll(Integer pageNo, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return abstractQuizRepository.findAll(pageable);
    }

    @Override
    public List<T> findAll() {
        return (List<T>) abstractQuizRepository.findAll();
    }

    @Override
    public Optional<T> findById(ID entityId) {
        return abstractQuizRepository.findById(entityId);
    }

    @Override
    public void deleteById(ID entityId) {
        abstractQuizRepository.deleteById(entityId);
    }

    @Override
    public void deleteAll() {
        abstractQuizRepository.deleteAll();
    }
}
