package net.dev4any1;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.model.Publisher;
import net.dev4any1.model.Role;
import net.dev4any1.model.User;
import net.dev4any1.service.PublisherService;
import net.dev4any1.service.ServiceException;
import net.dev4any1.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class PublisherServiceTest {

	@Resource
    private PublisherService pubService = new PublisherService();
	@Resource
	private UserService usService = new UserService();
	
	private User user;
	private Publisher publisher;
    
	 @Before
	    public void init() {
	       user = usService.createSubscriber("login", "password");
	       publisher = pubService.createPublisher("toxa", user);
	    }
	 
	@Test
	public void testCreatePublisher() {
		//User user = usService.createSubscriber("login", "password");
		//Publisher publisher = pubService.createPublisher("toxa", user);
		Assert.assertEquals(user.getRole().toString(), Role.PUBLISHER.name()); // toString added
		Assert.assertEquals("toxa", publisher.getName());
		Assert.assertEquals(user, publisher.getUser());;
	}
	
	@Test
	public void testGetPublisher() {
		//User user = usService.createSubscriber("login", "password");
		//Publisher publisher = pubService.createPublisher("toxa", user);
		System.out.println(publisher);    // role=publisher ??
		System.out.println(pubService.getPublisher(user));  // role=subscriber  ??
		Assert.assertEquals(pubService.getPublisher(user), publisher);
	}
	
	@Test(expected = ServiceException.class)
	public void testGetPublisherException() {
		//User user = usService.createSubscriber("login", "password");
		User user2 = usService.createSubscriber("login2", "password2");
		//Publisher publisher = pubService.createPublisher("toxa", user);
		Assert.assertEquals(pubService.getPublisher(user2), publisher);
	}
}
