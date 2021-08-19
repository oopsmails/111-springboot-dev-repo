package com.oopsmails.springboot.mockbackend.user;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/backendmock/user-api")
public class UserController {

	@Autowired
	UserService userService;

	@PostMapping(value = "/upload", consumes = { MediaType.APPLICATION_JSON_VALUE,
												 MediaType.MULTIPART_FORM_DATA_VALUE })

	public User upload(@RequestParam("user") String user, @RequestParam("file") List<MultipartFile> file) {

		User userJson = userService.getJson(user, file);
		return userJson;
	}
}
