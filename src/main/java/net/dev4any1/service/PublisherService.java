package net.dev4any1.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import net.dev4any1.dao.PublisherDao;
import net.dev4any1.model.Publisher;
import net.dev4any1.model.Role;
import net.dev4any1.model.User;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PublisherService{

	@Autowired
	private PublisherDao pubDao;

	@Transactional
	public Publisher createPublisher(String name, User user) {
		Publisher publisher = new Publisher();
		user.setRole(Role.PUBLISHER);
		publisher.setName(name);
		publisher.setUser(user);
		return pubDao.save(publisher); 
	}

	@Transactional(readOnly = true)
	public Publisher getPublisher(User user) {
		Optional<Publisher> pub = pubDao.findByUser(user);
		if (!pub.isPresent()) {
			throw new ServiceException("User " + user.getLogin() + " is not a Publisher!");
		}
		return pub.get();
	}

}
