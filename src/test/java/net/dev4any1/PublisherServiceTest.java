package net.dev4any1;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.model.PublisherModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.pojo.Role;
import net.dev4any1.service.PublisherService;
import net.dev4any1.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class PublisherServiceTest {

	@Autowired
    private PublisherService pubService = new PublisherService();
	@Autowired
	private UserService usService = new UserService();
     
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
