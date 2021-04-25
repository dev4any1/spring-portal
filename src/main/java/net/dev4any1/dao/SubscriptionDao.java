package net.dev4any1.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import net.dev4any1.model.Category;
import net.dev4any1.model.Subscription;

public interface  SubscriptionDao extends CrudRepository<Subscription, Long> {
	List<Subscription> findUserDistinctByCategory(Category cat);
}
