package com.example.todoback.service.impl;

import com.example.todoback.domain.Todo;
import com.example.todoback.service.TodoService;

import java.util.List;

public class TodoServiceImpl implements TodoService {
	@Override
	public List<Todo> getAll() {
		return List.of();
	}

	@Override
	public Todo plan(String todo) {
		return null;
	}

	@Override
	public Todo update(long todoId, String todo, boolean completed) {
		return null;
	}

	@Override
	public Todo delete(long todoId) {
		return null;
	}
}