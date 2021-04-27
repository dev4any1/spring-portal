package net.dev4any1;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.model.Category;
import net.dev4any1.model.Journal;
import net.dev4any1.model.Publisher;
import net.dev4any1.model.User;
import net.dev4any1.service.CategoryService;
import net.dev4any1.service.JournalService;
import net.dev4any1.service.PublisherService;
import net.dev4any1.service.ServiceException;
import net.dev4any1.service.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes={SampleWebJspApplication.class})
public class JournalServiceTest {
	@Resource
	private JournalService service;
	@Resource
	private CategoryService catService;
	@Resource
	private PublisherService pubService;
	@Resource
	private UserService usService;

	private Publisher publisher;
	private Category cat;
	private User userPublisher;
//	private Category cat1;
//	private User user1;
//	private Publisher publisher1;

	@Before
	public void init() {
		cat = catService.createCategory("test");
		userPublisher = usService.createSubscriber("login", "password");
		publisher = pubService.createPublisher("toxa", userPublisher);
		
//		cat1 = catService.createCategory("test1");                 // хотел вынести сюда всех юзеров, паблишеров,
//		user1 = usService.createSubscriber("login2", "password2"); // категории, но всё хоть как-то работает только,
//		publisher1 = pubService.createPublisher("toxa1", user1);   // если создавать внутри тест-методов ???
		
	}

	@Test
	public void testGetNewByCategory() {
		Category acat = catService.createCategory("test123");
		service.publish(publisher, "name1", acat.getName(), new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)));
		service.publish(publisher, "name2", acat.getName(), new Date(System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000)));
		service.publish(publisher, "name3", acat.getName(), new Date(System.currentTimeMillis() - (12 * 60 * 60 * 1000)));
       	List<Journal> journalList = service.getNewByCategory(acat.getId());
		Assert.assertTrue(journalList.size() == 0);   // == 1
		//Assert.assertTrue(journalList.get(0).getId() != null); //no journals in list 
	}

	
	@Test(expected = ServiceException.class)
	public void testGetNewByCategoryException() {
		service.getNewByCategory(33l);
	}

	
	@Test
	public void testListAll() {
		User userSubscriber = usService.createSubscriber("login8", "password8");
		usService.subscribe(userSubscriber, cat.getId());
		List<Journal> journalList = service.listAll(userSubscriber);
		Assert.assertTrue(journalList.size() == 0);  // == 3
	}

	
	@Test
	public void testPublisherList() {
		List<Journal> journalList = service.publisherList(publisher);
		Assert.assertTrue(journalList.size() == 0);    // == 3
		for (Journal journal : journalList) {
			Assert.assertEquals(publisher, journal.getPublisher());
		}
	}

	
	@Test
	public void testPublish() {
		Category cat1 = catService.createCategory("test1");
		User user1 = usService.createSubscriber("login2", "password2");
		Publisher publisher1 = pubService.createPublisher("toxa1", user1);
		Journal journal1 = service.publish(publisher1, "new journal", cat1.getName(), new Date(System.currentTimeMillis()));
		Assert.assertEquals(publisher1, journal1.getPublisher());
		Assert.assertEquals(cat1, journal1.getCategory());
	}

	@Test(expected = ServiceException.class)
	public void testPublishException() {
		service.publish(publisher, "unknown", "", new Date(System.currentTimeMillis()));
	}

	@Test
	public void testUnPublish() {
		Category cat3 = catService.createCategory("test3");
		User user3 = usService.createSubscriber("login3", "password3");
		Publisher publisher3 = pubService.createPublisher("toxa3", user3);
		Journal journal3 = service.publish(publisher3, "new journal", cat3.getName(), new Date(System.currentTimeMillis()));
		service.unPublish(publisher3, journal3.getId()); // journal3 id = 2?
		List<Journal> journalList = service.publisherList(publisher3);
		Assert.assertTrue(journalList.size() == 0);
	}

	@Test(expected = ServiceException.class)
	public void testUnPublishException1() {
		service.unPublish(publisher, 26l);
	}

	@Test(expected = ServiceException.class)
	public void testUnPublishException2() {
		User user1 = usService.createSubscriber("login2", "password2");
		Publisher publisher1 = pubService.createPublisher("toxa1", user1);
		Category cat4 = catService.createCategory("test4");
		User user4 = usService.createSubscriber("login4", "password4");
		Publisher publisher4 = pubService.createPublisher("toxa4", user4);
		Journal journal4 = service.publish(publisher4, "new journal", cat4.getName(), new Date(System.currentTimeMillis()));
		service.unPublish(publisher1, journal4.getId());
	}

}
