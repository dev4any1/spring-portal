package net.dev4any1;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.model.CategoryModel;
import net.dev4any1.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class CategoryServiceTest {
    @Resource
	private CategoryService service;
	
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
