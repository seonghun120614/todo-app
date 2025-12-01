package com.example.todoback.controller;

import com.example.todoback.common.TodoBase;
import com.example.todoback.common.dto.TodoDto;
import com.example.todoback.domain.Todo;
import com.example.todoback.service.TodoService;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(TodoController.class)
class TodoControllerTest extends TodoBase {
	@Mock
	private TodoService todoService;

	@InjectMocks
	private TodoController todoController;

	@ParameterizedTest
	@MethodSource("validTodoArguments")
	void givenValidTodo_whenCreateTodo_thenSuccess(String todoStr) {
		// given
		Todo todo = new Todo(1L, todoStr, false);
		when(todoService.plan(todoStr)).thenReturn(todo);

		// when
		ResponseEntity<TodoDto> response = todoController.createTodo(todoStr);

		// then
		assertNotNull(response);
		TodoDto todoDto = response.getBody();
		assertNotNull(todoDto);
		assertEquals(todo.getId(), todoDto.todoId());
		assertEquals(todo.getTodo(), todoDto.title());
		assertEquals(todo.isCompleted(), todoDto.completed());
	}

	@ParameterizedTest
	@MethodSource("validTodoString")
	void givenInvalidTodo_whenCreateTodo_thenFail(String todoStr) {
		// given
		Todo todo = new Todo(1L, todoStr, false);
		when(todoService.plan(todoStr)).thenReturn(todo);

		// when & then
		assertThrows(ConstraintViolationException.class, () -> {
			todoController.createTodo(todoStr);
		});
	}

	@ParameterizedTest
	@MethodSource("fullFilledValidTodoList")
	void givenTodos_whenGetTodos_thenSuccess(List<Todo> todos) {
		// given
		when(todoService.getAll()).thenReturn(todos);

		// when
		List<TodoDto> lst = todoController.getTodos().getBody();

		// then
		assertNotNull(lst);
		assertFalse(lst.isEmpty());
		assertEquals(todos.size(), lst.size());
		for (int i = 0 ; i < todos.size() ; i++) {
			assertEquals(todos.get(i).getId(),
			             lst.get(i).todoId());
			assertEquals(todos.get(i).getTodo().trim(),
			             lst.get(i).title());
			assertEquals(todos.get(i).isCompleted(),
			             lst.get(i).completed());
		}
	}

	@ParameterizedTest
	@MethodSource("validTodoArguments")
	void givenValidTodo_whenUpdateTodo_thenSuccess(
			Long todoId,
			String todoStr,
			Boolean completed
	) {
		// given
		when(todoService.update(todoId, todoStr.trim(), completed))
				.thenReturn(new Todo(todoId, todoStr.trim(), completed));

		// when
		TodoDto todoDto = todoController.updateTodo(todoId, todoStr, completed)
				.getBody();

		// then
		assertNotNull(todoDto);
		assertEquals(todoId, todoDto.todoId());
		assertEquals(todoStr.trim(), todoDto.title());
		assertEquals(completed, todoDto.completed());
	}

	@ParameterizedTest
	@MethodSource("invalidTodoArguments")
	void givenInvalidTodo_whenUpdateTodo_thenFail(
			Long todoId,
			String todoStr,
			Boolean completed
	) {
		assertThrows(ConstraintViolationException.class, () -> {
			todoController.updateTodo(todoId, todoStr, completed);
		});
	}

	@Test
	void givenTodoId_whenDeleteTodo_thenSuccess() {
		// given
		long todoId = 1L;
		when(todoService.delete(todoId))
				.thenReturn(new Todo(todoId, "existed", false));

		// when
		todoController.deleteTodo(todoId);

		// then
		verify(todoService).delete(todoId);
	}
}