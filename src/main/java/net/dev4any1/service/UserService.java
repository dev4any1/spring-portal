package net.dev4any1.service;



import java.util.List;
import java.util.Optional;

import net.dev4any1.model.SubscriptionModel;
import net.dev4any1.model.UserModel;

public interface UserService {

	public SubscriptionModel subscribe(UserModel user, Long categoryId);

	public UserModel createSubscriber(String login, String password);

	public Optional <UserModel> getByLogin(String login);
	
	public List<SubscriptionModel> getSubscription(UserModel user); 
}
