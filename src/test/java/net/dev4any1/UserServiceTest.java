package net.dev4any1;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.model.CategoryModel;
import net.dev4any1.model.SubscriptionModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.service.CategoryService;
import net.dev4any1.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class UserServiceTest {
	@Resource
	private UserService usService;
	@Resource
	private CategoryService catService;
    
	private UserModel user;

    @Before
    public void init() {
    	user = usService.createSubscriber("login1", "password1");
    }
 
	@Test
	public void testCreateSubscriber() {
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user, usService.getByLogin("login1").get());
	}
	
	@Test
	public void testGetByLogin() {
		Assert.assertTrue(usService.getByLogin("login1").isPresent());
	}
		
	@Test
	public void testGetByLoginError() {
		Assert.assertTrue(!usService.getByLogin("login4").isPresent());
	}
	
	@Test
	public void testSubscribe() {
		CategoryModel cat = catService.createCategory("test");
		SubscriptionModel sub = usService.subscribe(user, cat.getId());
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
		usService.subscribe(user, cat1.getId());
		usService.subscribe(user, cat2.getId());
		List<SubscriptionModel> subList = usService.getSubscription(user);
		for (SubscriptionModel sub : subList) {
			Assert.assertEquals(user, sub.getUser());
			Assert.assertTrue(usService.getSubscription(user).contains(sub));
		}
	}
}
