package com.qa.resourcereader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.jayway.jsonpath.JsonPath;

public class ZapiEndPointsReader {

	private Object zapiEndPoints;
	
	private String jiraBaseURL;
	
	public ZapiEndPointsReader() throws IOException{
		zapiEndPoints = JsonPath.read(readFromStream().toString(), "$");
		jiraBaseURL = new ResourceJSONReader().getJIRABaseURL();
		
	}
	
	public String getJiraBaseURL() throws IOException{
		return jiraBaseURL;
	}
	
	/**
	 * Methods gives End point of all project name
	 * @return
	 */
	public String getProjectListEndPoint(){
		return jiraBaseURL+JsonPath.read(zapiEndPoints, "$.zapi.projectlist");
	}
	
	/**
	 * Methods gives End point of all project versions name
	 * @return
	 */
	public String getProjectVersionListEndPoint(String projectId){
		return jiraBaseURL+JsonPath.read(zapiEndPoints, "$.zapi.projectversion").toString().replaceFirst("\\$\\{.+?\\}", projectId);
	}
	
	/**
	 * Methods gives End point of all project test cycle list
	 * @return
	 */
	public String getTestCycleListEndPoint(String projectId,String versionId){
		return jiraBaseURL+JsonPath.read(zapiEndPoints, "$.zapi.testcyclelist").toString().replaceFirst("\\$\\{.+?\\}", projectId).replaceFirst("\\$\\{.+?\\}", versionId);
	}
	
	/**
	 * Methods gives End point of all details of a  test cycle including test cases
	 * @return
	 */
	public String getTestCycleDetail(String testCycleId){
		return jiraBaseURL+JsonPath.read(zapiEndPoints, "$.zapi.testcycledetails").toString().replaceFirst("\\$\\{.+?\\}", testCycleId);
	}
	
	/**
	 * Methods gives End point of all details for test step of a test case
	 * @return
	 */
	public String getTestCaseStepDetailsEndPoint(String testCaseId){
		return jiraBaseURL+JsonPath.read(zapiEndPoints, "$.zapi.teststepsdetails").toString().replaceFirst("\\$\\{.+?\\}", testCaseId);
	}
	
	/**
	 * Methods gives End point of generate steps id
	 * @return
	 */
	public String getGenerateStepIdForTestCaseEndPoint(String testExecutionId){
		return jiraBaseURL+JsonPath.read(zapiEndPoints, "$.zapi.generatestepsid").toString().replaceFirst("\\$\\{.+?\\}", testExecutionId);
	}
	
	
	
	private StringBuilder readFromStream(){
		InputStream in = getClass().getResourceAsStream("/zapi_end_points.json"); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = "";
		StringBuilder responseStrBuilder = new StringBuilder();
		try {
			while((line =  reader.readLine()) != null){

			    responseStrBuilder.append(line);
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		return responseStrBuilder;
	}
	
	
	
	
	
}
