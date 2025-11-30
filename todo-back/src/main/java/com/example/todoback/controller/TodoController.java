package com.example.todoback.controller;

import com.example.todoback.common.dto.TodoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

	@PostMapping(consumes={ "plain/text" })
	public ResponseEntity<TodoDto> createTodo(
			@RequestBody String todo) {
		return null;
	}

	@GetMapping
	public ResponseEntity<List<TodoDto>> getTodos() {
		return null;
	}

	@PutMapping(consumes={ "plain/text" })
	public ResponseEntity<TodoDto> updateTodo(
			@RequestParam long todoId,
			@RequestBody String title) {
		return null;
	}

	@DeleteMapping
	public ResponseEntity<Void> deleteTodo(
			@RequestParam long todoId) {
		return null;
	}
}