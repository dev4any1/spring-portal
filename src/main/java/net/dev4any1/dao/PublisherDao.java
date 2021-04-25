package net.dev4any1.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.dev4any1.model.Publisher;
import net.dev4any1.model.User;

public interface  PublisherDao extends CrudRepository<Publisher, Long> {
	Optional<Publisher> findByUser(User user);
}
