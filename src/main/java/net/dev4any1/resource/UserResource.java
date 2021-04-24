package net.dev4any1.resource;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.dev4any1.model.UserModel;
import net.dev4any1.service.PublisherService;
import net.dev4any1.service.UserService;

@Controller
@RequestMapping("/user")
public class UserResource {

	public final static Logger LOG = Logger.getLogger(UserResource.class.getName());

	@Autowired
	public UserService userService;
	@Autowired
	public PublisherService pubService;

	//@Produces(MediaType.APPLICATION_XML)
	
	@RequestMapping(value ="/create/{login}/{password}", method = {RequestMethod.POST, RequestMethod.GET})
	public ResponseEntity<String> create(@PathVariable("login") String login, @PathVariable("password") String password) {
		if (userService.getByLogin(login).isPresent()) {
			return new ResponseEntity<String>(login + " user already exists", HttpStatus.BAD_REQUEST);
		}
		UserModel user = userService.createSubscriber(login, password);
        LOG.info("user " + user.getLogin() + " was successfully created");
		return new ResponseEntity<String>(UserModel.toUser(user).toString(), HttpStatus.OK); //entity(UserModel.toUser(user)).build();
       
	}
	
	//@Produces(MediaType.APPLICATION_XML)
	@RequestMapping(value ="/grant/{login}/{name}", method = {RequestMethod.POST, RequestMethod.GET})
	public ResponseEntity<String> grantPublisher(@PathVariable("login") String login, @PathVariable("name") String name) {
		Optional<UserModel> user = userService.getByLogin(login);
		if (!user.isPresent()) {
			return new ResponseEntity<String>(login + " does not exists", HttpStatus.BAD_REQUEST);
		}
		pubService.createPublisher(name, user.get());
        LOG.info("user " + user.get().getLogin() + " was successfully granted as publisher " + name);
		return new ResponseEntity<String>(user.get().toString(),HttpStatus.OK); //entity(user.get()).build();
	}
	
}