package net.dev4any1.dao;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.dev4any1.model.Journal;
import net.dev4any1.model.Publisher;
public interface JournalDao extends CrudRepository<Journal, Long>  {

    Collection<Journal> findByPublisher(Publisher publisher);
    List<Journal> findByCategoryIdIn(List<Long> ids);
    List<Journal> findByCategoryIdAndPublishedAtBetween(Long catId, Date endDate, Date startDate);
}
