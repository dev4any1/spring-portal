package net.dev4any1.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.dao.JournalDao;
import net.dev4any1.dao.SubscriptionDao;
import net.dev4any1.model.Category;
import net.dev4any1.model.Journal;
import net.dev4any1.model.Publisher;
import net.dev4any1.model.Subscription;
import net.dev4any1.model.User;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class JournalService{

	@Autowired
	private JournalDao journalDao;
	@Autowired
	private CategoryDao catDao;
	@Autowired
	private SubscriptionDao subDao;

	@Transactional(readOnly = true)	
	public List<Journal> getUserList(User user) {
		List<Subscription> subscriptions = user.getSubscriptions();
		if (subscriptions != null) {
			List<Long> ids = new ArrayList<>(subscriptions.size());
			subscriptions.stream().forEach(s -> ids.add(s.getCategory().getId()));
			return journalDao.findByCategoryIdIn(ids);
		} else {
			return Collections.emptyList();
		}
	}

	@Transactional(readOnly = true)
	public List<Journal> getPublisherList(Publisher publisher) {
		List<Journal> journalList = new ArrayList<Journal>();
		journalList.addAll(journalDao.findByPublisher(publisher));
		return journalList;
	}

	@Transactional
	public Journal create(Publisher publisher, String title, String fileName, Category category) {
		Journal journal = new Journal();
		journal.setTitle(title);
		journal.setFileId(fileName);
		journal.setPublisher(publisher);
		journal.setCategory(category);
		return journalDao.save(journal);
	}

	@Transactional
	public void delete(Publisher publisher, Long journalId) {
		Journal journal = journalDao.findOne(journalId);
		if (journal == null) {
			throw new ServiceException("unable to delete journal, journal " + journalId + " not found");
		}
		if (!journal.getPublisher().getId().equals(publisher.getId())) {
			throw new ServiceException("unable to delete journal, " + publisher.getName() + " isn't an owner");
		}
		journalDao.delete(journalId);
	}

	@Transactional(readOnly = true)
	public List<Journal> getNewByCategory(Long categoryId) {
		if (catDao.findOne(categoryId) == null) {
			throw new ServiceException("unable to add journal in category, category " + categoryId + " not exist");
		}
		LocalDateTime now = LocalDateTime.now();
		Date newPeriodEnd = localDateToDateWithDefaultTz(now);
		Date newPeriodStart = localDateToDateWithDefaultTz(now.minusDays(1L));
		return journalDao.findByCategoryIdAndPublishedAtBetween(categoryId, newPeriodEnd, newPeriodStart);
	}

	private Date localDateToDateWithDefaultTz(LocalDateTime localDate) {
		return Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
	}
}
