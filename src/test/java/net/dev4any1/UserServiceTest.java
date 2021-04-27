package net.dev4any1;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
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
import net.dev4any1.service.ServiceException;
import net.dev4any1.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class UserServiceTest {
	@Autowired
	private UserService usService;
	@Autowired
	private UserDao usDao;
	private User user; 
	@Autowired
	private CategoryService catService;
    
    @Before
    public void init() {
       user = usService.createSubscriber("login", "password");
    }
 
	@Test
	public void testCreateSubscriber() {
		Assert.assertNotNull(user.getId());
		Assert.assertEquals(user, usDao.findByLogin("login").get());
	}
	
	@Test
	public void testSubscribe() {
		Category cat = catService.createCategory("test");
		Subscription sub = usService.subscribe(user, cat.getId());
		Assert.assertEquals(user, sub.getUser());
		Assert.assertEquals(cat, sub.getCategory());
	}

	@Test(expected = ServiceException.class)
	public void testSubscribeException() {
		usService.subscribe(user, 26l);
	}
	
/*	@Test
	public void testGetSubscription() {
		Category cat1 = catService.createCategory("test1");
		Category cat2 = catService.createCategory("test2");
		int subCount = user.getSubscriptions().size();
		usService.subscribe(user, cat1.getId());
		usService.subscribe(user, cat2.getId());
		Assert.assertTrue(user.getSubscriptions().size() == subCount+2);
	}  
*/
}
