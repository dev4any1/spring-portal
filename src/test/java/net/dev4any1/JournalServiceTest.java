package net.dev4any1;

import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

//import com.google.inject.AbstractModule;
//import com.google.inject.Guice;
//import com.google.inject.Injector;
//import com.google.inject.Scopes;
//import com.google.inject.servlet.SessionScoped;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.dao.JournalDao;
import net.dev4any1.dao.SubscriptionDao;
import net.dev4any1.dao.UserDao;
import net.dev4any1.model.CategoryModel;
import net.dev4any1.model.JournalModel;
import net.dev4any1.model.PublisherModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.service.CategoryServiceImpl;
import net.dev4any1.service.JournalServiceImpl;
import net.dev4any1.service.PublisherServiceImpl;
import net.dev4any1.service.UserServiceImpl;

public class JournalServiceTest {

	private JournalServiceImpl service = new JournalServiceImpl();
	private CategoryServiceImpl catService = new CategoryServiceImpl();
	private PublisherServiceImpl pubService = new PublisherServiceImpl();
	private UserServiceImpl usService = new UserServiceImpl();
	private JournalDao journalDao = new JournalDao();
	private PublisherModel publisher;
	private CategoryModel cat = new CategoryModel();
	private UserModel userPublisher;

/*	protected Injector injector = Guice.createInjector(new AbstractModule() {
		@Override
		protected void configure() {
			bindScope(SessionScoped.class, Scopes.SINGLETON);
			bind(CategoryDao.class);
			bind(SubscriptionDao.class);
			bind(UserDao.class);
			bind(UserServiceImpl.class);
			bind(JournalDao.class);
		}
	});  

	@Before
	public void setup() {
		injector.injectMembers(service);
		injector.injectMembers(catService);
		injector.injectMembers(pubService);
		injector.injectMembers(usService);
		injector.injectMembers(journalDao);
	}   */

	@Before
	public void init() {

		cat = catService.createCategory("test");
		userPublisher = usService.createSubscriber("login", "password");
		publisher = pubService.createPublisher("toxa", userPublisher);
		JournalModel journal1 = createJournal(1l, new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)));
		JournalModel journal2 = createJournal(2l, new Date(System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000)));
		JournalModel journal3 = createJournal(3l, new Date(System.currentTimeMillis() - (12 * 60 * 60 * 1000)));
		service.publish(publisher, journal1.getName(), cat.getId(), new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)));
		service.publish(publisher, journal2.getName(), cat.getId(), new Date(System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000)));
		service.publish(publisher, journal3.getName(), cat.getId(), new Date(System.currentTimeMillis() - (12 * 60 * 60 * 1000)));
	}

	private JournalModel createJournal(long id, Date date) {
		JournalModel journal = new JournalModel();
		System.out.println(date);
		journal.withField("testfile").withId(id).withName("name").withPublishedAt(date);
		return journal;
	}

	@Test
	public void testGetNewByCategory() {
		System.out.println(cat.getId());
		List<JournalModel> journalList = service.getNewByCategory(cat.getId());
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
		JournalModel journal = createJournal(4l, new Date(System.currentTimeMillis()));
		CategoryModel cat1 = catService.createCategory("test1");
		UserModel user1 = usService.createSubscriber("login1", "password1");
		PublisherModel publisher1 = pubService.createPublisher("toxa1", user1);
		JournalModel journal1 = service.publish(publisher1, journal.getName(), cat1.getId(), new Date(System.currentTimeMillis()));
		Assert.assertEquals(publisher1, journal1.getPublisher());
		Assert.assertEquals(cat1, journal1.getCategory());
	}

	@Test(expected = Error.class)
	public void testPublishException() {
		JournalModel journal = createJournal(4l, new Date(System.currentTimeMillis()));
		service.publish(publisher, journal.getName() + "1", 24l, new Date(System.currentTimeMillis()));
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
		UserModel user1 = usService.createSubscriber("login1", "password1");
		PublisherModel publisher1 = pubService.createPublisher("toxa1", user1);
		service.unPublish(publisher1, 2l);
	}

}
