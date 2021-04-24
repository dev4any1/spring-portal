package net.dev4any1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import net.dev4any1.dao.PublisherDao;
import net.dev4any1.model.PublisherModel;
import net.dev4any1.pojo.Role;
import net.dev4any1.pojo.User;

@Component
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class PublisherService{
	@Autowired
	private PublisherDao pubDao = new PublisherDao();

	public PublisherModel createPublisher(String name, User user) {
		PublisherModel publisher = new PublisherModel();
		user.setRole(Role.PUBLISHER.name());
		publisher.setName(name);
		publisher.setUser(user);
		return pubDao.upsert(publisher); 
	}

	public PublisherModel getPublisher(User user) {
		for (PublisherModel pub: pubDao.getAll()) {
			if (pub.getUser().equals(user)) {
				return pub;
			}
		}
		return null;
	}

}
