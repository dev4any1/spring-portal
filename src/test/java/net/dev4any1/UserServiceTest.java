package net.dev4any1;

import java.util.Optional;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.dao.UserDao;
import net.dev4any1.model.Category;
import net.dev4any1.model.Subscription;
import net.dev4any1.model.User;
import net.dev4any1.service.CategoryService;
import net.dev4any1.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class UserServiceTest {
	@Resource
	private UserService usService;
	@Autowired
	private UserDao usDao;
	@Resource
	private CategoryService catService;
 
	@Test
	public void testCreateSubscriber() {
		User user = usService.createSubscriber("createUser", "createUser");
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user, usDao.findByLogin("createUser").get());
	}
	
	@Test
	public void testGetByLogin() {
		usService.createSubscriber("getByName", "getByName");
		Assert.assertTrue(usDao.findByLogin("getByName").isPresent());
	}
	
	@Test
	public void testSubscribe() {
		User user = usService.createSubscriber("subscribe", "subscribe");
		Category cat = catService.createCategory("subscribe");
		Subscription sub = usService.subscribe(user, cat);
		Assert.assertEquals(user, sub.getUser());
		Assert.assertEquals(cat, sub.getCategory());
	}

	
	@Test
	public void testGetSubscription() {
		User user = usService.createSubscriber("listSubscribtions", "listSubscribtions");
		Category cat1 = catService.createCategory("listSubscribtions1");
		Category cat2 = catService.createCategory("listSubscribtions2");
		usService.subscribe(user, cat1);
		usService.subscribe(user, cat2);
		Optional<User> updated = usDao.findByLogin("listSubscribtions");
		Assert.assertTrue(updated.isPresent());
		Assert.assertTrue(updated.get().getSubscriptions().size() == 2);
	}
}
