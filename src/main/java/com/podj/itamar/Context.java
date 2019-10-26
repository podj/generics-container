package com.podj.itamar;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class Context {
	
	private final Map<Key<?>, Object> values = new HashMap<>();
	
	/**
	 * Puts an object, using {@code identifier} and {@code type} both for building the key used in the get* methods.
	 * @param identifier The key that identifies the value
	 * @param value The value to be stored
	 * @param type The Class (or super class) object of {@code value}
	 * @param <T> The specific type of {@code value}
	 */
	public <T> void put(@NonNull String identifier, T value, @NonNull Class<T> type) {
		insert(identifier, value, type);
	}
	
	/**
	 * Puts a generic list of type {@code type}.
	 * Used alongside {@code getList(...)}.
	 * @param identifier The key that identifies the list
	 * @param list The list to be stored
	 * @param type The Class (or super class) object of objects that are held inside {@code list}
	 * @param <T> The type of the objects inside {@code list}
	 */
	public <T> void put(@NonNull String identifier, List<? extends T> list, @NonNull Class<T> type) {
		insert(identifier, list, type);
	}
	
	/**
	 * Returns an object of type {@code <T>}.
	 * The {@code type} parameter has to be the exact one that was used when calling the {@code put(...)} method.
	 * @param identifier The key that identifies the value
	 * @param type The Class (or super class) object of {@code value}
	 * @param <T> The type of the returned object
	 * @return A value of type {@code <T>}
	 */
	public <T> T get(@NonNull String identifier, @NonNull Class<T> type) {
		Key<T> key = new Key<>(identifier, type);
		
		@SuppressWarnings("unchecked")
		T value = (T) values.get(key);
		
		return value;
	}
	
	/**
	 * Returns a list of {@code <T>} that is matching to the {@code identifier} and {@code type} parameters.
	 * @param identifier The key that identifies the list
	 * @param type The object class of the objects inside the list
	 * @param <T> The type of the object that are held within the returned list
	 * @return A list that matches the identifier and the list's objects' type.
	 */
	public <T> List<T> getList(@NonNull String identifier, @NonNull Class<T> type) {
		Key<T> key = new Key<>(identifier, type);
		
		@SuppressWarnings("unchecked")
		List<T> list = (List<T>) values.get(key);
		
		return list;
	}
	
	private <T> void insert(@NonNull String identifier, Object value, @NonNull Class<T> type) {
		Key<T> key = new Key<>(identifier, type);
		values.put(key, value);
	}
	
	@Data
	private static class Key<T> {
		private final String identifier;
		private final Class<T> type;
	}
	
}

