package com.example.todoback.service;

import com.example.todoback.domain.Todo;

import java.util.List;

public interface TodoService {
	List<Todo> getAll();
	Todo plan(String todo);
	Todo update(long todoId);
	Todo delete(long todoId);
}
