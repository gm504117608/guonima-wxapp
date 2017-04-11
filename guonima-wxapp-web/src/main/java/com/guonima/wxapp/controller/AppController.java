package com.guonima.wxapp.controller;

import com.guonima.wxapp.domain.User;
import com.guonima.wxapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/app")
public class AppController {

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.GET, value = "/users/{id}")
    public User getUser(@PathVariable("id") Long id) throws IOException {
        return userService.getUserById(id);
    }


}
