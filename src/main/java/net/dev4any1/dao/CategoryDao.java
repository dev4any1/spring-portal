package net.dev4any1.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.dev4any1.model.Category;

public interface CategoryDao extends CrudRepository<Category, Long> {
	Optional<Category> findByName(String name);
}
