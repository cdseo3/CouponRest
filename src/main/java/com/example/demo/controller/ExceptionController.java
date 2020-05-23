package com.example.demo.controller;

import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/exception")
public class ExceptionController {

	@GetMapping(value = "/entrypoint")
    public @ResponseBody Map<String,String> entrypointException() {
		Map<String,String> response = new HashMap<String, String>();
		
		response.put("message","해당 리소스에 접근하기 위한 권한이 없습니다");
		
        return  response;
    }

    @GetMapping(value = "/accessdenied")
    public @ResponseBody Map<String,String> accessdeniedException() {
    	
    	Map<String,String> response = new HashMap<String, String>();
    	
    	response.put("message","접근이 금지되었습니다.");
    	
        return response;
    }

}