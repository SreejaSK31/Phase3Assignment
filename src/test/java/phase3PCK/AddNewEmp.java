package phase3PCK;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddNewEmp {
	@Test
	public void test1() {
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();

		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body("{\r\n" + "    \"name\": \"Sreeja\",\r\n" + "     \"salary\": \"10000\"\r\n" + "}")
				.post("/create");

		String body = response.getBody().asString();
		System.out.println("Response body is " + body);
		System.out.println("Status code is " + response.statusCode());
		Assert.assertEquals(response.statusCode(), 201);
		
		JsonPath json = response.jsonPath();
		List<String> names = json.get("name");
		List<Integer> id = json.getList("id");
		int empid = 4;
		for(int i=0;i<id.size();i++) {
			if(id.get(i)==empid) {
				Assert.assertEquals(names.get(i), "Sreeja");
			}
		}
		Assert.assertEquals(names.size(), 4);
	}

}
