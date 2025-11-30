package com.example.todoback.common.dto;

public record TodoDto(
		long todoId,
		String title,
		boolean isCompleted
) { }