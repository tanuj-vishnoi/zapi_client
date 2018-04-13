package com.qa.resourcereader;

import java.io.File;
import java.io.IOException;

import com.jayway.jsonpath.JsonPath;

public class ResourceJSONReader {

	private File file;
	private String baseURL;
	private Object jiraEndPoints;
	
	public ResourceJSONReader() throws IOException{
		jiraEndPoints = JsonPath.read(new File(System.getProperty("user.dir")+File.separator+"resource.json"), "$");
		baseURL = JsonPath.read(jiraEndPoints,"$.jira.baseurl");
	}
	
	public String getJSONFileDataAsString() throws IOException{
		return JsonPath.read(file, "$");
	}
	
	/**
	 * This method return the base url of JIRA TMS(Test Management tool)
	 * It reads value from resource.json file present in project root
	 * The json file must contains value like { "jira":{ "baseurl": "www.jira.com:port"}}
	 * @return
	 * @throws IOException 
	 */
	public String getJIRABaseURL(){
		return baseURL;
	}
	
	public String getUserName(){
		return JsonPath.read(jiraEndPoints, "$.jira.username");
	}
	
	public String getPassword(){
		return JsonPath.read(jiraEndPoints, "$.jira.password");
	}
	
	
}
