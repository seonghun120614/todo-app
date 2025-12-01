package com.example.todoback.common;

import com.example.todoback.domain.Todo;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TodoBase {

	public Stream<Long> invalidTodoId() {
		return Stream.of(Long.MIN_VALUE, -1L, -192837L);
	}

	public Stream<String> invalidTodoString() {
		return Stream.of("",
		                 null,
		                 "This is a very very long text todo for testing maximum length."
				                 .repeat(15)
				                 .substring(0, 256));
	}

	public Stream<String> validTodoString() {
		return Stream.of("apple",
		                 "Hello",
		                 "한글",
		                 "👾 안녕",
		                 "こんにちは",
		                 "!@#^& !@#%   ",
		                 "This is a very very long text todo for testing maximum length.".repeat(10)
		                                                                                 .substring(0, 255));
	}

	public Stream<Arguments> validTodoArguments() {
		return Stream.of(
				Arguments.of(10L, "abc", false),
				Arguments.of(Long.MAX_VALUE, "chocolate", true),
				Arguments.of(20L, "   !", false),
				Arguments.of(30L, "こんにちは!!!!>\\", false),
				Arguments.of(40L, "👾     ", false)
		);
	}

	public Stream<Arguments> invalidTodoArguments() {
		return Stream.of(
				Arguments.of(1L, "", true),
				Arguments.of(-1L, "abc", true),
				Arguments.of(null, "abc", true),
				Arguments.of(1L, "", false),
				Arguments.of(1L, null, true),
				Arguments.of(1L, "      ", true),
				Arguments.of(1L, "hello", null)
		);
	}

	public Stream<List<Todo>> fullFilledValidTodoList() {
		AtomicLong seq = new AtomicLong(1L);
		return Stream.of(
				validTodoString()
						.map(str -> new Todo(seq.getAndIncrement(), str, false))
						.toList()
		);
	}
}