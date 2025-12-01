package com.example.todoback.repository;

import com.example.todoback.common.TodoBase;
import com.example.todoback.domain.Todo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.stream.LongStream;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TodoRepositoryTest extends TodoBase {
	@Autowired
	private TodoRepository todoRepository;
	private long existedTodoIdThreshold = 0;

	@BeforeAll
	void setUp() {
		validTodoArguments().forEach(args -> {
			Object[] objects = args.get();
			Todo todo = new Todo(null,
			                     (String) objects[1],
			                     (boolean) objects[2]);
			todoRepository.save(todo);
			existedTodoIdThreshold++;
		});
	}

	@Order(1)
	@ParameterizedTest
	@MethodSource("validTodoString")
	void givenValidTodos_whenSave_thenSaveComplete(String todoStr) {
		// given
		Todo todo = new Todo(todoStr);

		// when
		Todo savedTodo = todoRepository.save(todo);

		// then
		assertNotNull(savedTodo.getId());
		assertFalse(savedTodo.isCompleted());
	}

	@Order(2)
	@ParameterizedTest
	@NullAndEmptySource
	void givenInvalidTodos_whenSave_thenSaveFail(String todoStr) {
		// given
		Todo todo = new Todo(todoStr);

		// when
		assertThrows(DataIntegrityViolationException.class, () -> {
			todoRepository.save(todo);
		});
	}

	@Order(3)
	@Test
	void givenExistedTodoIds_whenFindById_thenSuccess() {
		assertAll(LongStream.rangeClosed(1, existedTodoIdThreshold)
				.mapToObj(i -> () -> assertTrue(todoRepository.findById(i).isPresent())));
	}

	@Order(4)
	@ParameterizedTest
	@MethodSource("invalidTodoId")
	void givenInvalidTodoId_whenFindById_thenFail(Long id) {
		// when
		Optional<Todo> todo = todoRepository.findById(id);

		// then
		assertTrue(todo.isEmpty());
	}

	@Order(5)
	@Test
	void givenExistedTodoId_whenDeleteById_thenDelete() {
		// when
		LongStream.rangeClosed(1, existedTodoIdThreshold)
		          .forEach(todoRepository::deleteById);

		// then
		assertAll(
				LongStream.rangeClosed(1, existedTodoIdThreshold)
				          .mapToObj(i -> () -> assertTrue(todoRepository.findById(i).isEmpty()))
		);
	}
}