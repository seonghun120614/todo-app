package com.example.todoback.service;

import com.example.todoback.common.TodoBase;
import com.example.todoback.domain.Todo;
import com.example.todoback.repository.TodoRepository;
import com.example.todoback.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest extends TodoBase {
	@Mock
	private TodoRepository todoRepository;

	@InjectMocks
	private TodoServiceImpl todoService;

	@Test
	void givenTodos_whenGetAll_thenSuccess() {
		// given
		List<Todo> todoList = List.of(
				new Todo("test1"),
				new Todo("test2")
		);
		when(todoRepository.findAll()).thenReturn(todoList);

		// when
		List<Todo> result = todoService.getAll();

		// then
		assertFalse(result.isEmpty());
		assertEquals(2, result.size());
		assertEquals("test1", result.getFirst().getTodo());
	}

	@ParameterizedTest
	@MethodSource(value = "invalidTodoString")
	void givenValidTodo_whenPlan_thenSuccess(String todoStr) {
		// given
		Todo todo = new Todo(todoStr);
		when(todoRepository.save(todo)).thenReturn(todo);

		// when
		Todo savedTodo = todoService.plan(todoStr);

		// then
		assertNotNull(savedTodo);
		assertEquals(todoStr, savedTodo.getTodo());
		assertFalse(savedTodo.isCompleted());
	}

	@ParameterizedTest
	@MethodSource(value = "invalidTodoString")
	void givenInvalidTodo_whenPlan_thenFailure(String invalidTodoStr) {
		// given
		Todo todo = new Todo(invalidTodoStr);
		when(todoRepository.save(todo)).thenReturn(todo);

		// when & then
		assertThrows(IllegalArgumentException.class, () -> {
			todoService.plan(invalidTodoStr);
		});
	}

	@ParameterizedTest
	@MethodSource(value = "validTodoString")
	void givenValidTodo_whenUpdate_thenSuccess(String todoStr) {
		// given
		Todo todo = new Todo("another text");
		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

		// when
		Todo updatedTodo = todoService.update(1L, todoStr, true);

		// then
		assertNotNull(updatedTodo);
		assertTrue(updatedTodo.isCompleted());
		assertEquals(todoStr, updatedTodo.getTodo());
	}

	@Test
	void givenExistedTodoIds_whenDelete_thenSuccess() {
		// given
		Todo todo = new Todo("test todo");
		when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

		// when
		Todo deletedTodo = todoService.delete(1L);

		// then
		assertNotNull(deletedTodo);
		assertEquals("test todo", deletedTodo.getTodo());
		verify(todoRepository).delete(todo);
	}
}