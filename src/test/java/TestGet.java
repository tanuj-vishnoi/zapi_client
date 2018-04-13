import org.testng.annotations.Test;
import com.qa.restclient.HTTPGetRequest;

public class TestGet {

	
	
	@Test
	public void Test1(){
		HTTPGetRequest req = new HTTPGetRequest("cntr_tvishnoi","Spring2021");
		String s = req.makeGetRequestAndGetBody("https://jira.pennfoster.edu/rest/api/2/project");
		System.out.println(s);
	}
	
	//@Test
	public void Test12(){
		HTTPGetRequest req = new HTTPGetRequest();
		String s = req.makeGetRequestAndGetBody("http://mysafeinfo.com/api/data?list=englishmonarchs&format=json");
		System.out.println(s);
	}
}
