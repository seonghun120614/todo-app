package com.example.todoback.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, ID> extends CrudRepository<T, ID> {
	@Override Iterable<T> findAll();
	@Override <S extends T> S save(S entity);
	@Override void delete(T entity);
}
