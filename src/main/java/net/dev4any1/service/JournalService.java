package net.dev4any1.service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.dao.JournalDao;
import net.dev4any1.model.Category;
import net.dev4any1.model.Journal;
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

	public List<Journal> listAll(User user) {
		List<Subscription> subList = user.getSubscriptions();
		Set<Category> catSet = new HashSet<Category>();
		for (Subscription sub : subList) {
			catSet.add((Category) sub.getCategory());
		}
		List<Journal> journalList = new ArrayList<Journal>();
		for (Journal journal : journalDao.findAll()) {
			if (catSet.contains(journal.getCategory())) {
				journalList.add(journal);
			}
		}
		return journalList;
	}

	public List<Journal> publisherList(Publisher publisher) {
		List<Journal> journalList = new ArrayList<Journal>();
		journalList.addAll(journalDao.findByPublisher(publisher));
		return journalList;
	}

	public Journal publish(Publisher publisher, String fileName, String catName, Date publishedAt) {
		Optional<Category> cat = catDao.findByName(catName);
		if (cat == null) {
			throw new ServiceException("unable to publish journal, category " + catName + " not found");
		}
		Journal journal = new Journal();
		journal.setName(fileName);
		journal.setPublisher(publisher);
		journal.setCategory(cat.get());
		journal.setPublishedAt(publishedAt);
		return journalDao.save(journal);
	}

	public void unPublish(Publisher publisher, Long journalId) {
		Journal journal = journalDao.findOne(journalId);
		if (journal == null) {
			throw new ServiceException("unable to unpublish journal, journal " + journalId + " not found");
		}
		if (!journal.getPublisher().getId().equals(publisher.getId())) {
			throw new ServiceException("unable to unpublish journal, publisher  " + publisher.getName() + " is lier");
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
