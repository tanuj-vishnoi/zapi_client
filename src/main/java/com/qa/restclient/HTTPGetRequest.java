package com.qa.restclient;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Headers;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.*;


public class HTTPGetRequest {

	/**
	 * Set Global basic authentication
	 * @param userName
	 * @param password
	 */
	public HTTPGetRequest(String userName,String password){
		RestAssured.authentication = preemptive().basic(userName, password);	
	}
	
	public HTTPGetRequest(){
		
	}
	
	public String makeGetRequestAndGetBody(String url){
		System.out.println("URL is : "+url);
		return given().get(url).body().asString();
	}
	
	public Response makeGetRequestAndGetResponse(String url){
		return given().get(url);
	}
	
	public int makeGetRequestAndGetStatusCode(String url){
		return given().get(url).getStatusCode();
	}
	
	public String makeGetRequestAndGetContentType(String url){
		return given().get(url).getContentType();
	}
	
	public Headers makeGetRequestAndGetHeaders(String url){
		return given().get(url).headers();
	}
	
	public String makeGetRequestAndGetHeaders(String url,String headerName){
		return given().get(url).getHeader(headerName);
	}
}
