package net.dev4any1.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.dev4any1.dao.CategoryDao;
import net.dev4any1.dao.UserDao;
import net.dev4any1.model.Category;
import net.dev4any1.model.Journal;
import net.dev4any1.model.Role;
import net.dev4any1.model.User;
import net.dev4any1.service.JournalService;
import net.dev4any1.service.PublisherService;
import net.dev4any1.service.ServiceException;

@Controller
@RequestMapping("/publisher")
public class PublisherResource {

	public final static Logger LOG = Logger.getLogger(PublisherResource.class.getName());

	@Autowired
	public UserDao userDao;
	@Autowired
	public CategoryDao catDao;
	@Autowired
	public JournalService journalService;
	@Autowired
	public PublisherService pubService;

	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_XML)

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("login") String login,
			@RequestParam("title") String title,
			@RequestParam("category") String categoryName,
			@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = System.getProperty("user.dir") + "/target/" + UUID.randomUUID().toString() + ".pdf";
		if (file.isEmpty()) {
			return new ResponseEntity<String>("journal file is empty", HttpStatus.BAD_REQUEST);
		}
		saveToFile(file.getInputStream(), fileName);
		LOG.info("current path is " + System.getProperty("user.dir") + " login " + login + " category " + categoryName);
		LOG.info("file " + fileName + " was successfully uploaded");
		Optional<Category> cat = catDao.findByName(categoryName);
		if (!cat.isPresent()) {
			return new ResponseEntity<String>("category " + categoryName + " does not exist", HttpStatus.BAD_REQUEST);
		}
		Optional<User> user = userDao.findByLogin(login);
		if (!user.isPresent()) {
			return new ResponseEntity<String>("user " + user + " does not exist", HttpStatus.BAD_REQUEST);
		}
		if (!user.get().getRole().equals(Role.PUBLISHER)) {
			return new ResponseEntity<String>("user " + user + " is not a publisher", HttpStatus.BAD_REQUEST);
		}
		Journal journal = journalService.create(pubService.getPublisher(user.get()), title, fileName, cat.get());
		return new ResponseEntity<String>(journal.toString(), HttpStatus.OK);
	}

	private void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) {

		try {
			OutputStream out = null;
			int theByte = 0;
			byte[] bytes = new byte[1024];
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((theByte = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, theByte);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			throw new ServiceException("unable to save file", e);
		}
	}

}