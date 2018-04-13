package com.qa.zapiclient;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import com.qa.resourcereader.ResourceJSONReader;
import com.qa.resourcereader.ZapiEndPointsReader;
import com.qa.restclient.HTTPGetRequest;

public class ZapiClientAPI {

	private ResourceJSONReader resourceReader;
	private HTTPGetRequest getRequest;
	private ZapiEndPointsReader zapiEndPoints;

	public ZapiClientAPI() {
		try {
			resourceReader = new ResourceJSONReader();
			zapiEndPoints = new ZapiEndPointsReader();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Please check resource json or zapi end points json file path or format");
			e.printStackTrace();
		}
	}

	public String getListOfAllProjects() {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		return getRequest.makeGetRequestAndGetBody(zapiEndPoints.getProjectListEndPoint());
	}

	public String getProjectId(String projectName) {
		List<String> projectIds = JsonPath.read(getListOfAllProjects(),
				"$.options.*[?(@.label=='" + projectName + "')].value");
		System.out.println("********** Project : "+projectName+" has id : "+projectIds.get(0));
		return projectIds.get(0);
	}

	public String getVersionsList(String projectId) {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		return getRequest.makeGetRequestAndGetBody(zapiEndPoints.getProjectVersionListEndPoint(projectId));
	}

	public Object getProjectVersionId(String projectId, String versionName) {
		List<String> versionIds = JsonPath.read(getVersionsList(projectId),
				"$.*.*[?(@.label=='" + versionName + "')].value");
		System.out.println("********** Verion : "+versionName+" has id : "+versionIds.get(0));
		return versionIds.get(0);
	}

	//************************************ Test Cycle ******************************************************
	
	@SuppressWarnings("unchecked")
	public String getTestCycleDetails(String cycleName, String projectId, String versionId) {
		Object s = getTestCycleList(projectId, versionId);
		Map<String,Map<Object,String>> map = null;
		System.out.println(s.toString());
		ObjectMapper objectMapper = new ObjectMapper();
		String testCyleId = null;
		try {
			map = objectMapper.readValue(s.toString(), Map.class);
			System.out.println(map);
			for(Map.Entry<String, Map<Object,String>> values : map.entrySet()){
					 testCyleId = values.getKey();
					if(values.getValue().get("name").equals(cycleName))
						break;
				}
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("********** Test Cycle : "+cycleName+" has id : "+testCyleId);
		return testCyleId;
	}
	
	private void createCycle(){
		
	}
	
	public Object getTestCycleList(String projectId, String versionId) {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		return getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleListEndPoint(projectId, versionId));
	}
	
	public Object getTestCycleDetail(String testCycleId) {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		return getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleDetail(testCycleId));
	}
	
	public Object getTestDetailsAddedInTestCycle(String testCycleId) {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		return JsonPath.read(getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleDetail(testCycleId)).toString(),"$.executions.*");
		//return getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleDetail(testCycleId));
	}
	
	public Object getCountOfTestCasesAddedInTestCycle(String testCycleId) {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		return JsonPath.read(getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleDetail(testCycleId)).toString(),"$.recordsCount");
		//return getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleDetail(testCycleId));
	}
	
	public Map<String,String> getTestCasesIdAddedInTestCycle(String testCycleId) {
		 Map<String,Map<String,String>> testCaseDetails = getAddedTestCasesDetailsInTestCycle(testCycleId);
		 Map<String,String> testCaseIds = new HashMap<String,String>();
		 for(Map.Entry<String, Map<String,String>> testCase : testCaseDetails.entrySet()){
			 testCaseIds.put(testCase.getValue().get("issueKey"), String.valueOf(testCase.getValue().get("issueId")));
		}
		 return testCaseIds;
	}
	
	//*************************************************************************************************
	
	//********************************************** Test Case *****************************************
	
	@SuppressWarnings("unchecked")
	public  Map<String,Map<String,String>> getAddedTestCasesDetailsInTestCycle(String testCycleId) {
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		String jsonOutput = getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCycleDetail(testCycleId)).toString();
		 List<Object> details =  JsonPath.read(jsonOutput,"$.executions.*");
		 Map<String,Map<String,String>> testCaseDetails = new HashMap<String, Map<String,String>>();
		 for(int i=0;i<details.size();i++){
			 testCaseDetails.put(((Map<String, String>) details.get(i)).get("issueKey"), (Map<String, String>) details.get(i));
		 }
		 return testCaseDetails;
	}
	
	public String getExecutionIdOfTestCase(String testCycleId,String testCaseName){
		return String.valueOf(getAddedTestCasesDetailsInTestCycle(testCycleId).get(testCaseName).get("id"));
	}
	
	public String getExecutionStatusOfTestCase(String testCycleId,String testCaseName){
		return getAddedTestCasesDetailsInTestCycle(testCycleId).get(testCaseName).get("executionStatus");
	}
	
	public String getTestIdOfTestCase(String testCycleId,String testCaseName){
		return String.valueOf(getAddedTestCasesDetailsInTestCycle(testCycleId).get(testCaseName).get("issueId"));
	}
	
	public String getSummaryfTestCase(String testCycleId,String testCaseName){
		return String.valueOf(getAddedTestCasesDetailsInTestCycle(testCycleId).get(testCaseName).get("summary"));
	}
	
	public String getDescriptionOfTestCase(String testCycleId,String testCaseName){
		return String.valueOf(getAddedTestCasesDetailsInTestCycle(testCycleId).get(testCaseName).get("issueDescription"));
	}
	
	public List<Object> getTestStepsOfATestCase(String testCaseId){
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		String jsonOutput = getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCaseStepDetailsEndPoint(testCaseId)).toString();
		return JsonPath.read(jsonOutput,"$..step");
	}
	
	public List<Object> getTestStepsIdOfATestCase(String testCaseExecutionId,String testCaseId){
		generateExecutionIdOfTestSteps(testCaseExecutionId);
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		String jsonOutput = getRequest.makeGetRequestAndGetBody(zapiEndPoints.getTestCaseStepDetailsEndPoint(testCaseId)).toString();
		return JsonPath.read(jsonOutput,"$..id");
	}
	
	public void generateExecutionIdOfTestSteps(String testCaseExecutionId){
		getRequest = new HTTPGetRequest(resourceReader.getUserName(), resourceReader.getPassword());
		getRequest.makeGetRequestAndGetBody(zapiEndPoints.getGenerateStepIdForTestCaseEndPoint(testCaseExecutionId)).toString();
	}
	
	//************************************ End Test Case ****************************************************************
}
