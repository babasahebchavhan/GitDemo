import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;

import files.Payload;

public class Basics {

	public static void main(String[] args)  
	{
		
		System.out.println("Git Demo Line one ");
		System.out.println("Git Demo Line two ");
		System.out.println("Git Demo Line three ");
		
		RestAssured.baseURI="https://rahulshettyacademy.com";
		
		String response1=given().log().all().queryParam("key","qaclick123")
		       
		       .header("Content-Type","application/json")
		       
		       .body(Payload.AddPlace())
		       
		       
	      .when()
		       .post("maps/api/place/add/json")
		       
		       
	      .then().assertThat().statusCode(200).body("scope", equalTo("APP"))
	      
	      .extract().response().asString();//extracted response in string
		
		System.out.println("Actual Response from Server-  "+response1);

		JsonPath js1=new JsonPath(response1);
		String placeId=js1.getString("place_id");
		String Scope=js1.getString("scope");
		System.err.println(Scope);
		
		System.out.println(placeId);
		
		//Update Place 
		String response2= given().log().all().queryParam("key", "qaclick123")
		       .header("Content-Type","application/json")
		       .body("{\r\n"
		       		+ "\"place_id\":\""+placeId+"\",\r\n"
		       		+ "\"address\":\"70 Summer walk, India\",\r\n"
		       		+ "\"key\":\"qaclick123\"\r\n"
		       		+ "}\r\n"
		       		+ "")
		
		.when().put("maps/api/place/update/json")
		
		.then().log().all().assertThat().statusCode(200).body("msg",equalTo("Address successfully updated"))
		.extract().response().asString();
		System.out.println(response2);
		
		//Get Place
		
		String Getresponse= given().queryParam("key", "qaclick123").queryParam("place_id",placeId)
		       
		
		.when().get("maps/api/place/get/json")
		.then().assertThat().log().all().extract().asString();
		
		JsonPath GetJson=new JsonPath(Getresponse);
		String GetAddress= GetJson.getString("address");
		System.out.println(GetAddress);
		Assert.assertEquals("70 Summer walk, India", GetAddress);
		
	}

}
