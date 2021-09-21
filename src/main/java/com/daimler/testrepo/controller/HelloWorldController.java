package com.daimler.testrepo.controller;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.daimler.testrepo.model.StringResponse;

@RestController
public class HelloWorldController {


    @RequestMapping(value = "/hello", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public StringResponse sayHello() {
    	System.out.println(" Responding with Hello");
    	StringResponse response = new StringResponse("Hello from controller");
        return response;
    }
}