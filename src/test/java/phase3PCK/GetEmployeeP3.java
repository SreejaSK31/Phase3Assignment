package phase3PCK;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetEmployeeP3 {
@Test
	public void test1() {
		RestAssured.baseURI = "http://localhost:3000/employees";
		RequestSpecification request = RestAssured.given();

		Response response = request.get();

		String body = response.getBody().asString();

		System.out.println("Response body is " + body);
		System.out.println("Status code is " + response.getStatusCode());
		System.out.println("Response header is " + response.getHeaders().asList());

		JsonPath json = response.jsonPath();
		List<String> names = json.get("name");
		System.out.println("The number of employees are " + names.size());
		Assert.assertEquals(names.size(), 3);
		Assert.assertEquals(response.statusCode(), 200);
		
	}


}
