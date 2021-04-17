package net.dev4any1.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.model.CategoryModel;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoryServiceImpl implements CategoryService {
	@Autowired
	private CategoryDao dao;
	
	public CategoryModel createCategory(String name) {
		CategoryModel cat = new CategoryModel();
		cat.setName(name);
		return dao.createAndGet(cat);
		}

	public Collection<CategoryModel> getAll() {
		return dao.getAll();
	}

	@Override
	public CategoryModel getByName (String name) {
		return dao.getByName(name);
		}
	
	
}
