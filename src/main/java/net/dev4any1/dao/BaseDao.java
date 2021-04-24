package net.dev4any1.dao;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import net.dev4any1.model.DbObject;

public class BaseDao<T extends DbObject> {

	private Map<Long, T> repository = new HashMap<Long, T>();
    
	public T createAndGet(T object) {
		object.setId(repository.size() + 1l);
		repository.put(object.getId(), object);
		return object;
	}

	public Collection<T> getAll() {
		return repository.values();
	}

	public T get(Long id) {
		return repository.get(id);
	}
	
	public void delete(Long id) {
		repository.remove(id);
	}

	public T update(T object) {
		if (repository.containsKey(object.getId())) {
			repository.replace(object.getId(), object);
		} else {
			throw new RuntimeException("failed to update, object not exist");
		}
		return object;
	}

	public T upsert(T object) {
		if (repository.containsKey(object.getId())) {
			repository.replace(object.getId(), object);
		} else {
			return createAndGet(object);
		}
		return object;
	}
}
