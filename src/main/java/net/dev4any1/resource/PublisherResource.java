package net.dev4any1.resource;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import net.dev4any1.model.CategoryModel;
import net.dev4any1.model.UserModel;
import net.dev4any1.pojo.Role;
import net.dev4any1.service.CategoryService;
import net.dev4any1.service.JournalService;
import net.dev4any1.service.PublisherService;
import net.dev4any1.service.UserService;

@Controller
@RequestMapping("/publisher")
public class PublisherResource {

	public final static Logger LOG = Logger.getLogger(PublisherResource.class.getName());

	@Autowired
	public UserService userService;
	@Autowired
	public CategoryService catService;
	@Autowired
	public JournalService journalService;
	@Autowired
	public PublisherService pubService;

	// @Consumes(MediaType.MULTIPART_FORM_DATA)
	// @Produces(MediaType.APPLICATION_XML)

	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public ResponseEntity<String> uploadFile(@RequestParam("login") String login,
			//@RequestParam("category") String category,
			@RequestParam("file") MultipartFile file) throws IOException {
		String fileName = System.getProperty("user.dir") + "/target/" + UUID.randomUUID().toString() + ".pdf";
		if (file.isEmpty()) {
			return new ResponseEntity<String>("journal file is empty", HttpStatus.BAD_REQUEST);
		}
		String category = "test";
		saveToFile(file.getInputStream(), fileName);
		LOG.info("current path is " + System.getProperty("user.dir") + " login " + login + " category " + category);
		LOG.info("file " + fileName + " was successfully uploaded");
		CategoryModel cat = catService.getByName(category);
		if (cat == null) {
			return new ResponseEntity<String>("category " + category + " does not exist", HttpStatus.BAD_REQUEST);
		}
		Optional<UserModel> user = userService.getByLogin(login);
		if (!user.isPresent()) {
			return new ResponseEntity<String>("user " + user + " does not exist", HttpStatus.BAD_REQUEST);
		}
		if (!user.get().getRole().equals(Role.PUBLISHER.name())) {
			return new ResponseEntity<String>("user " + user + " is not a publisher", HttpStatus.BAD_REQUEST);
		}
		journalService.publish(pubService.getPublisher(user.get()), fileName, cat.getId(),
				new Date(System.currentTimeMillis()));
		return new ResponseEntity<String>(journalService.publish(pubService.getPublisher(user.get()), fileName,
				cat.getId(), new Date(System.currentTimeMillis())).toString(), HttpStatus.OK);
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

			e.printStackTrace();
		}

	}

	/*
	 * @RequestMapping(value= "/uploadFile/{login}/{category}",
	 * method=RequestMethod.POST) public @ResponseBody String
	 * handleFileUpload(@PathVariable("login") String login,
	 * 
	 * @PathVariable("category") String category,
	 * 
	 * @RequestParam("file") MultipartFile file){ String fileName =
	 * "/mnt/c/Users/robot/testfile.txt"; if (!file.isEmpty()) {
	 * 
	 * try {
	 * 
	 * byte[] bytes = file.getBytes(); BufferedOutputStream stream = new
	 * BufferedOutputStream(new FileOutputStream(new File(fileName + "-uploaded")));
	 * stream.write(bytes); stream.close(); return "Вы удачно загрузили " + fileName
	 * + " в " + fileName + "-uploaded !"; } catch (Exception e) { return
	 * "Вам не удалось загрузить " + fileName + " => " + e.getMessage(); } } else {
	 * return "Вам не удалось загрузить " + fileName + " потому что файл пустой."; }
	 * }
	 */

}