package com.podj.itamar;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {
	
	private final Context context = new Context();
	
	@Test
	void get_stringWithStringClassObject_returnString() {
		// arrange
		String expectedFirstName = "John Doe";
		context.put("firstName", expectedFirstName, String.class);
		
		// act
		String firstName = context.get("firstName", String.class);
		
		// assert
		assertEquals(expectedFirstName, firstName);
	}
	
	@Test
	void getList_numbersList_returnNumberList() {
		// arrange
		List<Number> expectedNumbers = List.of(45, 78L, 0.5f);
		context.put("numbers", expectedNumbers, Number.class);
		
		// act
		List<Number> numbers = context.getList("numbers", Number.class);
		
		// assert
		assertArrayEquals(expectedNumbers.toArray(), numbers.toArray());
	}
	
	@Test
	void getList_inheritedList_returnNumberList() {
		// arrange
		List<Integer> expectedNumbers = List.of(45, 78, 0);
		context.put("numbers", expectedNumbers, Number.class);
		
		// act
		List<Number> numbers = context.getList("numbers", Number.class);
		
		// assert
		assertArrayEquals(expectedNumbers.toArray(), numbers.toArray());
	}
	
	@Test
	void get_correctIdentifierWrongClassObject_returnNull() {
		// arrange
		context.put("firstName", "John Doe", String.class);
		
		// act
		Integer firstName = context.get("firstName", Integer.class);
		
		// assert
		assertNull(firstName);
	}
	
	@Test
	void getList_correctIdentifierWrongClassObject_returnNull() {
		// arrange
		List<Number> expectedNumbers = List.of(45, 78L, 0.5f);
		context.put("numbers", expectedNumbers, Number.class);
		
		// act
		List<String> numbers = context.getList("numbers", String.class);
		
		// assert
		assertNull(numbers);
	}
	
	@Test
	void getList_notPuttingAListButJustString_doesNotThrowClassCastException() {
		// arrange
		String firstName = "John Doe";
		context.put("firstName", firstName, String.class);
		
		// act & assert
		assertDoesNotThrow(() -> context.getList("firstName", String.class));
	}
	
	@Test
	void getList_notPuttingAListButJustString_returnNull() {
		// arrange
		String firstName = "John Doe";
		context.put("firstName", firstName, String.class);
		
		// act
		List<String> names = context.getList("firstName", String.class);
		
		// assert
		assertNull(names);
	}
	
	@Test
	void getList_storingSubtypeUsingSupertypeAsKey_doesNotThrowClassCastException() {
		// arrange
		List<Long> longs = List.of(0L, 5L, 16L);
		context.put("nums", longs, Number.class);
		
		// act & assert
		assertDoesNotThrow(() -> {
			List<Number> numbers = context.getList("nums", Number.class);
		});
	}
}