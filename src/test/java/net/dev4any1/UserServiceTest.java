package net.dev4any1;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import net.dev4any1.dao.BaseDao;
import net.dev4any1.dao.CategoryDao;
import net.dev4any1.model.CategoryModel;
import net.dev4any1.model.SubscriptionModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.service.CategoryServiceImpl;
import net.dev4any1.service.UserServiceImpl;

public class UserServiceTest {
    @Autowired
	private UserServiceImpl usService = new UserServiceImpl();
    @Autowired
	private UserModel user = new UserModel(); 
    @Autowired
	private CategoryServiceImpl catService = new CategoryServiceImpl();
   
  //  @Before
  //  public void init() {
  //  	user = usService.createSubscriber("login1", "password1");
  //  	CategoryModel cat = catService.createCategory("test");
  //  }
	@Test
	public void testCreateSubscriber() {
		user = usService.createSubscriber("login1", "password1");
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user, usService.getByLogin("login1").get());
	}
	
	@Test
	public void testGetByLoginError() {
		Assert.assertTrue(!usService.getByLogin("login4").isPresent());
	}
	
	@Test
	public void testSubscribe() {
		user = usService.createSubscriber("login1", "password1");
		CategoryModel cat = catService.createCategory("test");
		System.out.println(cat.toString());
		System.out.println(cat.getId());
		SubscriptionModel sub = usService.subscribe(user, cat);
		Assert.assertEquals(user, sub.getUser());
		Assert.assertEquals(cat, sub.getCategory());
	}

	@Test(expected = Error.class)
	public void testSubscribeException() {
		usService.subscribe(user, null);
	}
	
	@Test
	public void testGetSubscription() {
		CategoryModel cat1 = catService.createCategory("test1");
		CategoryModel cat2 = catService.createCategory("test2");
		usService.subscribe(user, cat1);
		usService.subscribe(user, cat2);
		List<SubscriptionModel> subList = usService.getSubscription(user);
		for (SubscriptionModel sub : subList) {
			Assert.assertEquals(user, sub.getUser());
			Assert.assertTrue(usService.getSubscription(user).contains(sub));
		}
	}
}
