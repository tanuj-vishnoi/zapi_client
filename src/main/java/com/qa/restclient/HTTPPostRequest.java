package com.qa.restclient;

import static com.jayway.restassured.RestAssured.preemptive;

import java.util.Map;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import net.minidev.json.JSONObject;

public class HTTPPostRequest {

	public HTTPPostRequest(String userName,String password){
		RestAssured.authentication = preemptive().basic(userName, password);	
	}
	
	public void putRequestWithJsonBody(Map<String,Object> jsonBody){
		RestAssured.baseURI ="http://restapi.demoqa.com/customer";
		RequestSpecification request = RestAssured.given();
	 
		JSONObject requestParams = new JSONObject();
		requestParams.put("FirstName", "Virender"); // Cast
		requestParams.put("LastName", "Singh");
		requestParams.put("UserName", "sdimpleuser2dd2011");
		requestParams.put("Password", "password1");
	 
		requestParams.put("Email",  "sample2ee26d9@gmail.com");
		request.body(requestParams.toJSONString());
		Response response = request.post("/register");
	}
}
