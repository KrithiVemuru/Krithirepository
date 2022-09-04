package utils;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {

	private HashMap<String, String> headers = new HashMap<String, String>();

	public void login(String userName,String password) {
		Response response = RestAssured.given()
				.contentType("application/json")
				.post("/v1/login");
		System.out.println(response.getBody().asString());
		headers.put("authorization", "Bearer "+response.getBody().asString());
	}

	public void post(HashMap<String,String> payload) {
		Response response = RestAssured.given()
				.contentType("application/json")
				.headers(headers)
				.body(payload.get("body"))
				.post(payload.get("uri"));
		System.out.println(response.getBody().asString());
	}

	public void put(HashMap<String, String> payload) {
		Response response = RestAssured.given()
				.contentType("application/json")
				.headers(headers)
				.body(payload.get("body"))
				.put(payload.get("uri"));
		System.out.println(response.getBody().asString());
	}

	public void get(HashMap<String, String> payload) {
		Response response = RestAssured.given()
				.contentType("application/json")
				.headers(headers)
				.get(payload.get("uri"));
		System.out.println(response.getBody().asString());
	}

	public void delete(HashMap<String, String> payload) {
		Response response = RestAssured.given()
				.contentType("application/json")
				.headers(headers)
				.delete(payload.get("uri"));
		System.out.println(response.getStatusCode());
	}
}
