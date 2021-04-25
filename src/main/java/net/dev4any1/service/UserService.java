package net.dev4any1.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.dao.SubscriptionDao;
import net.dev4any1.dao.UserDao;
import net.dev4any1.model.Category;
import net.dev4any1.model.Role;
import net.dev4any1.model.Subscription;
import net.dev4any1.model.User;

@Component
@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {
	@Autowired
	private UserDao userDao;
	@Autowired
	private CategoryDao catDao;
	@Autowired
	private SubscriptionDao subscripDao;
	
	public User createSubscriber(String login, String password) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setRole(Role.SUBSCRIBER);
		return userDao.save(user);
	}

	public Subscription subscribe(User user, Long categoryId) {
		System.out.println(user.toString());
		Category cat = catDao.findOne(categoryId);
		if (cat == null) {
			throw new ServiceException("category with id " + categoryId + " was not found");
		} else {
			Subscription sub = new Subscription();
			sub.setCategory(cat);
			sub.setUser(user);
			sub.setCreatedAt(new Date());
			return subscripDao.save(sub);
		}
	}
/*
	public List<Subscription> getSubscription(User user) {
		List<Subscription> subList = new ArrayList<Subscription>();
		subscripDao.
		for (Subscription sub : subscripDao.getAll()) {
			if (sub.getUser().equals(user)) {
				subList.add(sub);
			}
		}
		return subList;
	}
*/
}
