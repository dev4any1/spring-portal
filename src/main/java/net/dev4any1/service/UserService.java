package net.dev4any1.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
	private SubscriptionDao subscripDao;

	@Transactional
	public User createSubscriber(String login, String password) {
		User user = new User();
		user.setLogin(login);
		user.setPassword(password);
		user.setRole(Role.SUBSCRIBER);
		return userDao.save(user);
	}

	@Transactional
	public Subscription subscribe(User user, Category category) {
		Subscription sub = new Subscription();
		sub.setCategory(category);
		sub.setUser(user);
		sub.setCreatedAt(new Date());
		return subscripDao.save(sub);
	}
}
