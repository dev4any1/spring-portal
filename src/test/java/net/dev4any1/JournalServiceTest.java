package net.dev4any1;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import net.dev4any1.dao.JournalDao;
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
@SpringBootTest(classes = { SampleWebJspApplication.class })
public class JournalServiceTest {
	@Resource
	private JournalService service;
	@Resource
	private CategoryService catService;
	@Resource
	private PublisherService pubService;
	@Resource
	private UserService usService;
	@Autowired
	private JournalDao journalDao;

	//@Test
	public void testGetNewByCategory() {
		Publisher publisher = pubService.createPublisher("newByCat",
				usService.createSubscriber("newByCat", "newByCat"));
		Category category = catService.createCategory("newByCat");
		
		Journal oldJournal1 = service.create(publisher, "newByCat1", "newByCat1", category);
		oldJournal1.setPublishedAt(new Date(System.currentTimeMillis() - (7 * 24 * 60 * 60 * 1000)));
		journalDao.save(oldJournal1);
		Journal oldJournal2 = service.create(publisher, "newByCat2", "newByCat2", category);
		oldJournal1.setPublishedAt(new Date(System.currentTimeMillis() - (8 * 24 * 60 * 60 * 1000)));
		journalDao.save(oldJournal2);
		service.create(publisher, "newByCat3", "newByCat3", category);
		List<Journal> journalList = service.getNewByCategory(category.getId());
		Assert.assertEquals(1, journalList.size());
		Assert.assertTrue(journalList.get(0).getId() != null);
	}

	@Test(expected = ServiceException.class)
	public void testGetNewByCategoryException() {
		service.getNewByCategory(Long.MAX_VALUE);
	}

	//@Test
	public void testUserListAllJournals() {
		User user = usService.createSubscriber("listUserJournals", "listUserJournals");
		Publisher publisher = pubService.createPublisher("listUserJournals", 
				usService.createSubscriber("listUserJournalsPub", "listUserJournalsPub"));
		Category category1 = catService.createCategory("listUserJournals1");
		Category category2 = catService.createCategory("listUserJournals2");
		service.create(publisher, "listUserJournals1", "listUserJournals1", category1);
		service.create(publisher, "listUserJournals2", "listUserJournals2", category2);
		service.create(publisher, "listUserJournals3", "listUserJournals3", category2);
		usService.subscribe(user, category1);
		usService.subscribe(user, category2);
		List<Journal> journalList = service.getUserList(user);
		Assert.assertEquals(3, journalList.size());
	}

	@Test
	public void testPublisherList() {
		Publisher publisher = pubService.createPublisher("listPublisherJournals",
				usService.createSubscriber("listPublisherJournals", "listPublisherJournals"));
		Category category1 = catService.createCategory("listPublisherJournals1");
		Category category2 = catService.createCategory("listPublisherJournals2");
		service.create(publisher, "listPublisherJournals1", "listPublisherJournals1", category1);
		service.create(publisher, "listPublisherJournals2", "listPublisherJournals2", category2);
		service.create(publisher, "listPublisherJournals3", "listPublisherJournals3", category2);
		List<Journal> journalList = service.getPublisherList(publisher);
		Assert.assertEquals(3, journalList.size());
	}

	
	@Test
	public void testCreate() {
		Category category = catService.createCategory("createJournal");
		User user = usService.createSubscriber("createJournal", "createJournal");
		Publisher publisher = pubService.createPublisher("createJournal", user);
		Long createdId = service.create(publisher, "createJournal", "createJournal", category).getId();
		Journal journal = journalDao.findOne(createdId);
		Assert.assertEquals(publisher.getId(), journal.getPublisher().getId());
		Assert.assertEquals(category, journal.getCategory());
	}

	@Test
	public void testDelete() {
		Category category = catService.createCategory("deleteJournal");
		User user = usService.createSubscriber("deleteJournal", "deleteJournal");
		Publisher publisher = pubService.createPublisher("deleteJournal", user);
		Long createdId = service.create(publisher, "deleteJournal", "deleteJournal", category).getId();
		service.delete(publisher, createdId);
		Journal journal = journalDao.findOne(createdId);
		Assert.assertEquals(journal, null);
	}

	@Test(expected = ServiceException.class)
	public void testDeleteException1() {
		User user = usService.createSubscriber("deleteJournalNeg1", "deleteJournalNeg1");
		Publisher publisher = pubService.createPublisher("deleteJournalNeg1", user);
		service.delete(publisher, 0l);
	}

	@Test(expected = ServiceException.class)
	public void testDeleteException2() {
		Category category = catService.createCategory("deleteJournalNeg2");
		User user1 = usService.createSubscriber("deleteJournalNeg21", "deleteJournalNeg2");
		User user2 = usService.createSubscriber("deleteJournalNeg22", "deleteJournalNeg2");
		Publisher publisher1 = pubService.createPublisher("deleteJournalNeg21", user1);
		Publisher publisher2 = pubService.createPublisher("deleteJournalNeg22", user2);
		Long createdId = service.create(publisher1, "deleteJournalNeg2", "deleteJournalNeg2", category).getId();
		service.delete(publisher2, createdId);
	}

}
