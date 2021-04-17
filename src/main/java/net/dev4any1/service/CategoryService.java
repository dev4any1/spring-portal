package net.dev4any1.service;

import java.util.Collection;

import net.dev4any1.model.CategoryModel;

public interface CategoryService {

	public CategoryModel createCategory(String name);
	public Collection<CategoryModel> getAll();
	public CategoryModel getByName(String name);
}
