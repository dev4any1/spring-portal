package net.dev4any1.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.dao.SubscriptionDao;
import net.dev4any1.dao.UserDao;
import net.dev4any1.model.CategoryModel;
import net.dev4any1.model.SubscriptionModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.pojo.Role;

@Component
//@Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService {
	@Autowired
	private UserDao userDao;// = new UserDao();
	@Autowired
	private CategoryDao catDao;// = new CategoryDao();
	@Autowired
	private SubscriptionDao subscripDao;// = new SubscriptionDao();
	
	public UserModel createSubscriber(String login, String password) {
		UserModel user = new UserModel();
		user.setLogin(login);
		user.setPassword(password);
		user.setRole(Role.SUBSCRIBER.name());
		return userDao.createAndGet(user);
	}

	public Optional<UserModel> getByLogin(String login) {
		for (UserModel object : userDao.getAll()) {
			if (object.getLogin().equals(login)) {
				System.out.println(object.toString());
				return Optional.ofNullable(object);
			}
		}

		return Optional.ofNullable(null);
	}

	public SubscriptionModel subscribe(UserModel user, Long categoryId) {
		System.out.println(user.toString());
		CategoryModel cat = catDao.get(categoryId);
		if (cat == null) {
			throw new Error("category with id " + categoryId + " was not found");
		} else {
			SubscriptionModel sub = new SubscriptionModel();
			sub.setCategory(cat);
			sub.setUser(user);
			sub.setCreatedAt(new Date());
			return subscripDao.createAndGet(sub);
		}
	}

	public List<SubscriptionModel> getSubscription(UserModel user) {
		List<SubscriptionModel> subList = new ArrayList<SubscriptionModel>();
		for (SubscriptionModel sub : subscripDao.getAll()) {
			if (sub.getUser().equals(user)) {
				subList.add(sub);
			}
		}
		return subList;
	}
}
