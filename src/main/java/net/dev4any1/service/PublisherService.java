package net.dev4any1.service;

import net.dev4any1.model.PublisherModel;
import net.dev4any1.pojo.User;

public interface PublisherService {
	public PublisherModel createPublisher(String name, User user);
	public PublisherModel getPublisher(User user);
}
