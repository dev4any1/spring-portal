package net.dev4any1.dao;

import org.springframework.stereotype.Component;

import net.dev4any1.model.CategoryModel;

@Component
public class CategoryDao extends BaseDao<CategoryModel> {

	public CategoryModel getByName(String name) {
		for (CategoryModel cat : getAll()) {
			if (cat.getName().equals(name)) {
				return cat;
			}
		}
		return null;
	}  
}
