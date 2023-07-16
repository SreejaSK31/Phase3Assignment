package phase3PCK;

import java.util.List;

import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class EndToEndTest {
	
	Response response;
	String BaseURI = "http://localhost:3000/employees";
	
	@Test
	public void test1() {	
		response=GetMethodAll();
		Assert.assertEquals(response.statusCode(), 200);
		
		response= PostMethod("John", "10000");
		Assert.assertEquals(response.statusCode(), 201);
		JsonPath json = response.jsonPath();
		int empid = json.get("id");
		System.out.println("EmpdID is " +empid);
		
		response= PutMethod(empid, "Tom", "10000");
		Assert.assertEquals(response.statusCode(), 200);
		json = response.jsonPath();
		Assert.assertEquals(json.get("name"),"Tom");
		
		response= GetSingleEmp(empid);
		Assert.assertEquals(response.statusCode(), 200);
		json = response.jsonPath();
		Assert.assertEquals(json.get("name"),"Tom");
		
		response= DeleteMethod(empid);
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertEquals(response.getBody().asString(),"{}");
		
		response= GetMethod(empid);
		Assert.assertEquals(response.statusCode(), 404);
		Assert.assertEquals(response.getBody().asString(),"{}");
		
		
	}
	public Response GetMethodAll() {
		
			RestAssured.baseURI = BaseURI;
			RequestSpecification request = RestAssured.given();

			Response response = request.get();
			return response;
	}
	
	public Response PostMethod(String Name,String Salary) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		
		JSONObject jobj =  new JSONObject();
		jobj.put("name", Name);
		jobj.put("salary", Salary);
		
		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jobj.toString())
				.post("/create");
		
		return response;
}
	
public Response PutMethod(int Empid, String Name,String Salary) {
		
		RestAssured.baseURI = BaseURI;
		RequestSpecification request = RestAssured.given();
		
		JSONObject jobj =  new JSONObject();
		jobj.put("name", Name);
		jobj.put("salary", Salary);

		Response response = request.contentType(ContentType.JSON).accept(ContentType.JSON)
				.body(jobj.toString())
				.put("/4");
		
		return response;
}

public Response GetSingleEmp(int Empid) {
	
	RestAssured.baseURI = BaseURI;
	RequestSpecification request = RestAssured.given();

	Response response = request.get("/4");
	
	return response;
}

public Response DeleteMethod(int Empid) {
	
	RestAssured.baseURI = BaseURI;
	RequestSpecification request = RestAssured.given();

	Response response = request.delete("/4");
	
	return response;
}

public Response GetMethod(int Empid) {
	
	RestAssured.baseURI = BaseURI;
	RequestSpecification request = RestAssured.given();

	Response response = request.get("/4");
	
	return response;
	
}
}
