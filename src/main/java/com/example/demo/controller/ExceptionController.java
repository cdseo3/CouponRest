package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {
	
    @GetMapping(value = "/accessdenied")
    public @ResponseBody Map<String,String> accessdeniedException() {
    	throw new AccessDeniedException("");
    }

}