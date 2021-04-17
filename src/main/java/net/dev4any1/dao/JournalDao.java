package net.dev4any1.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import net.dev4any1.model.JournalModel;
@Component
public class JournalDao extends BaseDao<JournalModel>  {
	
	public static final long ONEDAY = 24 * 60 * 60 * 1000;

	public List<JournalModel> getLastPosts(Long categoryId) {
		Date date = new Date();
		List<JournalModel> journalList = new ArrayList<JournalModel>();
		for (JournalModel journal: getAll()) {
			if (date.getTime() - journal.getPublishedAt().getTime() <= ONEDAY) {
				journalList.add(journal);
			}
		}
		return journalList;
	}
}
