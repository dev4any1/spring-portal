package net.dev4any1;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import net.dev4any1.model.PublisherModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.pojo.Role;
import net.dev4any1.service.PublisherServiceImpl;
import net.dev4any1.service.UserServiceImpl;


public class PublisherServiceTest {
	@Autowired
    private PublisherServiceImpl pubService = new PublisherServiceImpl();
	@Autowired
	private UserServiceImpl usService = new UserServiceImpl();
     
	@Test
	public void testCreatePublisher() {
		UserModel user = usService.createSubscriber("login", "password");
		PublisherModel publisher = pubService.createPublisher("toxa", user);
		Assert.assertEquals(user.getRole(), Role.PUBLISHER.name());
		Assert.assertEquals("toxa", publisher.getName());
		Assert.assertEquals(user, publisher.getUser());;
	}
	
	@Test
	public void testGetPublisher() {
		UserModel user = usService.createSubscriber("login", "password");
		PublisherModel publisher = pubService.createPublisher("toxa", user);
		Assert.assertEquals(pubService.getPublisher(user), publisher);
	}
	
	@Test(expected = Error.class)
	public void testGetPublisherException() {
		UserModel user = usService.createSubscriber("login", "password");
		PublisherModel publisher = pubService.createPublisher("toxa", user);
		Assert.assertEquals(pubService.getPublisher(null), publisher);
	}
}
