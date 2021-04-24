package net.dev4any1.model;

import java.util.Date;

import net.dev4any1.pojo.Category;
import net.dev4any1.pojo.Journal;
import net.dev4any1.pojo.Publisher;

public class JournalModel extends Journal implements DbObject {
	

	@Override
	public Long getId() {
		return id;
	}


	public JournalModel withId(Long id) {
		this.id = id;
		return this;
	}

	public JournalModel withName(String name) {
		this.name = name;
		return this;
	}

	public JournalModel withFileId(String fileId) {
		this.fileId = fileId;
		return this;
	}

	public JournalModel withPublishedAt(Date publishedAt) {
		this.publishedAt = publishedAt;
		return this;
	}

	public JournalModel withCategory(Category category) {
		this.category = category;
		return this;
	}

	public JournalModel withPublisher(Publisher publisher) {
		this.publisher = publisher;
		return this;
	}

}
