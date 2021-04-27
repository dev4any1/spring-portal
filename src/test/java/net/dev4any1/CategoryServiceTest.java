package net.dev4any1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.model.Category;
import net.dev4any1.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class CategoryServiceTest {
    @Autowired
	private CategoryService service;
    @Autowired
	private CategoryDao dao;
    
	@Test
	public void testCreateCategory() {
		Category cat = service.createCategory("name");
		Assert.assertNotNull(cat.getId());
		Assert.assertTrue(dao.findByName("name").isPresent());
	}

}
