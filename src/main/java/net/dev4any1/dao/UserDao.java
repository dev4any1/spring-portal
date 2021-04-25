package net.dev4any1.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import net.dev4any1.model.User;

public interface  UserDao extends CrudRepository<User, Long> {
	Optional<User> findByLogin(String login);
}
