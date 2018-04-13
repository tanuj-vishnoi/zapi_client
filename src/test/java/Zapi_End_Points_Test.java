import java.lang.reflect.Method;
import java.util.Map;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.zapiclient.ZapiClientAPI;

public class Zapi_End_Points_Test {

	ZapiClientAPI zapiAPI;
	
	@BeforeClass
	public void setSession(){
		zapiAPI = new ZapiClientAPI();
	}
	
	@BeforeMethod
	public void startMethod(Method name){
		System.out.println();
		System.out.println();
		System.out.println("***************** "+name+" ***************************************");
		System.out.println();
		System.out.println();
	}
	
	@Test
	public void Test_Porject_List_End_Point(){
		System.out.println(zapiAPI.getListOfAllProjects());
	}
	
	@Test
	public void Test_Get_Project_Id(){
		System.out.println(zapiAPI.getProjectId("30SecondsCV"));
	}
	
	@Test
	public void Test_VersionID(){
		System.out.println(zapiAPI.getProjectVersionId(zapiAPI.getProjectId("30SecondsCV"),"Unscheduled"));
	}
	
	@Test
	public void Test_CycleList(){
		String projectId = zapiAPI.getProjectId("30SecondsCV").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		System.out.println(zapiAPI.getTestCycleList(projectId, versionId));
	}
	
	@Test
	public void Test_CycleListDetails(){
		String projectId = zapiAPI.getProjectId("30SecondsCV").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		System.out.println(zapiAPI.getTestCycleDetails("Zapi_API",projectId, versionId));
	}
	
	@Test
	public void Test_CycleDetails(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		System.out.println(zapiAPI.getTestCycleDetail(testCycleId));
		System.out.println(zapiAPI.getTestDetailsAddedInTestCycle(testCycleId));
		System.out.println(zapiAPI.getCountOfTestCasesAddedInTestCycle(testCycleId));
	}
	
	@Test
	public void Test_Get_Count_Of_TestCase_In_TestCycle(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		System.out.println(zapiAPI.getCountOfTestCasesAddedInTestCycle(testCycleId));
	}
	
	@Test
	public void Test_Get_TestCase_Details_Added_In_TestCycle(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		Map<String, Map<String, String>> ids = zapiAPI.getAddedTestCasesDetailsInTestCycle(testCycleId);
		for(Map.Entry<String, Map<String,String>> testCase : ids.entrySet()){
			System.out.println("***"+testCase.getKey()+"***"+testCase.getValue());
		}
	}
	
	@Test
	public void Test_Get_TestCase_Id_Of_TestCase_Added_In_TestCycle(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		Map<String,String> ids = zapiAPI.getTestCasesIdAddedInTestCycle(testCycleId);
		System.out.println("**"+ids);
	}
	
	@Test
	public void Test_TestCase_Execution_Id(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		System.out.println(zapiAPI.getExecutionIdOfTestCase(testCycleId,"AUTOMATION-26"));
	}
	
	@Test
	public void Test_TestCase_Execution_Status(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		System.out.println(zapiAPI.getExecutionStatusOfTestCase(testCycleId,"AUTOMATION-26"));
	}
	
	@Test
	public void Test_TestCase_TestStepDetails(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		String testId = zapiAPI.getTestIdOfTestCase(testCycleId,"AUTOMATION-26");
		System.out.println(zapiAPI.getTestStepsOfATestCase(testId));
	}
	
	@Test
	public void Test_TestStepIds_TestStepDetails(){
		String projectId = zapiAPI.getProjectId("Automation_Demo").toString();
		String versionId = zapiAPI.getProjectVersionId(projectId,"Unscheduled").toString();
		String testCycleId = zapiAPI.getTestCycleDetails("Frontline_Demo",projectId, versionId);
		String testId = zapiAPI.getTestIdOfTestCase(testCycleId,"AUTOMATION-26");
		String testExecutionId = zapiAPI.getExecutionIdOfTestCase(testCycleId,"AUTOMATION-26");
		System.out.println(zapiAPI.getTestStepsOfATestCase(testId));
		System.out.println(zapiAPI.getTestStepsIdOfATestCase(testExecutionId,testId));
	}
}
