import java.io.File;
import java.io.IOException;

import org.testng.annotations.Test;

import com.jayway.jsonpath.JsonPath;
import com.qa.resourcereader.ZapiEndPointsReader;

public class TestJSONPATH {

	@Test
	public void readData() throws IOException{
		/*InputStream in = getClass().getResourceAsStream("./resource.json"); 
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));*/
		
		
		String json =System.getProperty("user.dir")+File.separator+"resource.json";
		//Object document = Configuration.defaultConfiguration().jsonProvider().parse(json);
		
		String jsonPath = "$";

		File jsonFile = new File(json);

		System.out.println("Author: "+JsonPath.read(jsonFile, jsonPath));
		
	}
	
	@Test
	public void TestZapiEndPointReader() throws IOException{
		ZapiEndPointsReader zapiFile = new ZapiEndPointsReader();
		System.out.println(zapiFile.getJiraBaseURL());
		System.out.println(zapiFile.getProjectListEndPoint());
	}
}
