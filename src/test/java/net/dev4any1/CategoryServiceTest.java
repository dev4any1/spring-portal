package net.dev4any1;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.dev4any1.model.CategoryModel;
import net.dev4any1.service.CategoryService;
import net.dev4any1.service.CategoryServiceImpl;


public class CategoryServiceTest {
    @Autowired
	private CategoryService service = new CategoryServiceImpl();
	
	@Test
	public void testCreateCategory() {
		CategoryModel cat = service.createCategory("name");
		Assert.assertNotNull(cat.getId());
		Assert.assertTrue(service.getAll().contains(cat));
	}

	@Test
	public void testGetByName() {
		CategoryModel cat1 = service.createCategory("name1");
		Assert.assertEquals(service.getByName("name1"), cat1);
		Assert.assertNotEquals(service.getByName("name4"), cat1);
	}
}
