package net.dev4any1.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import net.dev4any1.model.CategoryModel;
import net.dev4any1.pojo.Category;
import net.dev4any1.service.CategoryServiceImpl;
import net.dev4any1.service.PublisherServiceImpl;

@Controller
@RequestMapping("/category")

public class CategoryResource {

	public final static Logger LOG = Logger.getLogger(CategoryResource.class.getName());

	@Autowired
	public CategoryServiceImpl catService;
	@Autowired
	public PublisherServiceImpl pubService;

	@RequestMapping(value = "/create/{name}", method = RequestMethod.POST)
	public ResponseEntity<String> create(@PathVariable("name") String name) {
		if (catService.getByName(name) != null) {
			return new ResponseEntity<String>("category " + name + " already exists", HttpStatus.BAD_REQUEST);
		}
		CategoryModel category = catService.createCategory(name);
		LOG.info("category " + category.getName() + " was successfully created");
		return new ResponseEntity<String>("category " + name + " is created", HttpStatus.OK);
	}

	@RequestMapping(value="/list", method = RequestMethod.GET)
	public ResponseEntity<List<Category>> list() {
		List<Category> categoryList = new ArrayList<Category>();
		categoryList.addAll(catService.getAll());
		ResponseEntity<List<Category>> entity = new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK) {
		};
		LOG.info("categoryList: " + categoryList);
		return entity;
	}

}