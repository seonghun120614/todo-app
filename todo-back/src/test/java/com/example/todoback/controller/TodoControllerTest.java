package com.example.todoback.controller;

import com.example.todoback.service.TodoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(TodoController.class)
@ActiveProfiles("test")
class TodoControllerTest {

	@Test
	@DisplayName("Todo 생성 단위 테스트")
	void given_whenCreateTodo_thenSuccess() {
		// given
		// when
		// then
	}
}