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

import net.dev4any1.model.CategoryModel;
import net.dev4any1.model.JournalModel;
import net.dev4any1.model.PublisherModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.service.CategoryService;
import net.dev4any1.service.JournalService;
import net.dev4any1.service.PublisherService;
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

	private PublisherModel publisher;
	private CategoryModel cat;
	private UserModel userPublisher;

	@Before
	public void init() {

		cat = catService.createCategory("test");
		userPublisher = usService.createSubscriber("login", "password");
		publisher = pubService.createPublisher("toxa", userPublisher);
	}

	@Test
	public void testGetNewByCategory() {
		CategoryModel acat = catService.createCategory("test123");
		service.publish(publisher, "name1", acat.getId(), new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)));
		service.publish(publisher, "name2", acat.getId(), new Date(System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000)));
		service.publish(publisher, "name3", acat.getId(), new Date(System.currentTimeMillis() - (12 * 60 * 60 * 1000)));

		List<JournalModel> journalList = service.getNewByCategory(acat.getId());
		Assert.assertTrue(journalList.size() == 1);
		Assert.assertTrue(journalList.get(0).getId() != null);
	}

	@Test(expected = Error.class)
	public void testGetNewByCategoryException() {
		service.getNewByCategory(null);
	}

	@Test
	public void testListAll() {
		UserModel userSubscriber = usService.createSubscriber("login", "password");
		usService.subscribe(userSubscriber, cat.getId());
		List<JournalModel> journalList = service.listAll(userSubscriber);
		Assert.assertTrue(journalList.size() == 3);

	}

	@Test
	public void testPublisherList() {
		List<JournalModel> journalList = service.publisherList(publisher);
		Assert.assertTrue(journalList.size() == 3);
		for (JournalModel journal : journalList) {
			Assert.assertEquals(publisher, journal.getPublisher());
		}
	}

	@Test
	public void testPublish() {
		CategoryModel cat1 = catService.createCategory("test1");
		UserModel user1 = usService.createSubscriber("login2", "password2");
		PublisherModel publisher1 = pubService.createPublisher("toxa1", user1);
		JournalModel journal1 = service.publish(publisher1, "new journal", cat1.getId(), new Date(System.currentTimeMillis()));
		Assert.assertEquals(publisher1, journal1.getPublisher());
		Assert.assertEquals(cat1, journal1.getCategory());
	}

	@Test(expected = Error.class)
	public void testPublishException() {
		service.publish(publisher, "unknown", 24l, new Date(System.currentTimeMillis()));
	}

	@Test
	public void testUnPublish() {
		service.unPublish(publisher, 1l);
		List<JournalModel> journalList = service.publisherList(publisher);
		Assert.assertTrue(journalList.size() == 2);
	}

	@Test(expected = Error.class)
	public void testUnPublishException1() {
		service.unPublish(publisher, 26l);
	}

	@Test(expected = Error.class)
	public void testUnPublishException2() {
		UserModel user1 = usService.createSubscriber("login2", "password2");
		PublisherModel publisher1 = pubService.createPublisher("toxa1", user1);
		service.unPublish(publisher1, 2l);
	}

}
