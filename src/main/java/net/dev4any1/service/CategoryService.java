package net.dev4any1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.model.Category;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CategoryService {

	@Autowired
	private CategoryDao dao;

	public Category createCategory(String name) {
		Category cat = new Category();
		cat.setName(name);
		return dao.save(cat);
	}

}
