import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.* ;
import static io.restassured.matcher.RestAssuredMatchers.* ;
import static org.hamcrest.Matchers.* ;

import java.util.HashMap;

public class ChatServiceAPI {
	
	String organization_id;
	String channel_id;
	
	
	@Test(priority =1)
	void healthCheck()
	{
		given()
		
		.when()
		 .get("https://chat-service-s5g2.onrender.com/")
		
		.then()
		.statusCode(200)
		.body("message",equalTo("<Service is healthy>"))
		.log().all();
		
		}
	
		
		  @Test(priority =2, dependsOnMethods = {"healthCheck"}) void userSignup() {
		  HashMap data = new HashMap(); data.put("email", "harshtest1@gmail.com");
		  data.put("password", "Test@12345");
		  
		  given() .contentType("application/json") .body(data)
		  
		  .when() .post("https://chat-service-s5g2.onrender.com/auth/signup")
		  
		  .then() .statusCode(201) .body("message",equalTo("signup successfull"))
		  .log().all();
		  
		  }
		  
		  @Test(priority =3, dependsOnMethods = {"userSignup"}) void userLogin() {
		  HashMap data = new HashMap(); data.put("email", "harshtest1@gmail.com");
		  data.put("password", "Test@12345");
		  
		  given() .contentType("application/json") .body(data)
		  
		  .when() .post("https://chat-service-s5g2.onrender.com/auth/login")
		  
		  .then() .statusCode(200) .body("message",equalTo("login successfull"))
		  .log().all();
		  
		  }
		  
		  @Test(priority =4, dependsOnMethods = {"userLogin"}) void postMessages() {
		  HashMap data = new HashMap(); data.put("message",
		  "This is the message indicating that the message is posted successfully");
		  
		  given() .contentType("application/json") .body(data)
		  
		  .when() .post("https://chat-service-s5g2.onrender.com/chat/postMessage")
		  
		  .then() .statusCode(201)
		  .body("message",equalTo("Message is successfully posted")) .log().all(); }
		  
		  @Test(priority =5, dependsOnMethods = {"userLogin"}) void getAllMessages() {
		  given()
		  
		  .when() .get("https://chat-service-s5g2.onrender.com/chat/messages")
		  
		  .then() .statusCode(200) .body("message",
		  equalTo("This is the message indicating that the message is posted successfully"
		  )) .log().all(); }
		  
		  @Test(priority =6, dependsOnMethods = {"userLogin"}) void getAllReplies() {
		  given()
		  
		  .when() .get("https://chat-service-s5g2.onrender.com/chat/replies")
		  
		  .then() .statusCode(200) .body("message",equalTo("This is the replies"))
		  .log().all(); }
		  
		  @Test(priority =7, dependsOnMethods = {"userLogin"}) void
		  createOrganizations() { HashMap data = new HashMap(); data.put("name",
		  "HarshTestOrganization");
		  
		  organization_id = given() .contentType("application/json") .body(data)
		  
		  .when() .post("https://chat-service-s5g2.onrender.com/organizations")
		  .jsonPath().getString("organization_id");
		  
		  
		  
		  }
		  
		  @Test(priority =8, dependsOnMethods = {"userLogin"}) void
		  getOrganizationsList() { given()
		  
		  .when() .get("https://chat-service-s5g2.onrender.com/organizations")
		  
		  .then() .statusCode(200)
		  .body("message",equalTo("This is the list of organizations")) .log().all(); }
		  
		  @Test(priority =9, dependsOnMethods = {"userLogin"}) void createChannel() {
		  HashMap data = new HashMap(); data.put("name", "HarshTestOrganization");
		  data.put("channel_type", "HarshTestOrganization"); data.put("is_private",
		  true);
		  
		  given() .pathParam("organizations_id", organization_id)
		  
		  .when() .post(
		  "https://chat-service-s5g2.onrender.com/channel/org/{organizations_id}")
		  
		  .then() .statusCode(201)
		  .body("message",equalTo("Channel is successfully created")) .log().all();
		  
		  }
		  
		  @Test(priority =10, dependsOnMethods = {"userLogin"}) void
		  getAllChannelInTheOrganizations() { given() .pathParam("organizations_id",
		  organization_id)
		  
		  .when()
		  .get("https://chat-service-s5g2.onrender.com/channel/org/{organizations_id}")
		  
		  .then() .statusCode(200)
		  .body("message",equalTo("This is the list of all the channel "))
		  .log().all();
		  
		  
		  
		  
		  }
		  
		  @Test(priority =11, dependsOnMethods = {"userLogin"}) void
		  addChannelMembers() { HashMap data = new HashMap(); data.put("channel_id",
		  "HarshChannelId"); data.put("organisationsss_id", organization_id);
		  data.put("user_ids", "harshhhh");
		  
		  channel_id = given() .contentType("application/json") .body(data)
		  
		  .when() .post("https://chat-service-s5g2.onrender.com/channel/members")
		  .jsonPath().getString("channel_id");
		  
		  
		  }
		  
		  @Test(priority =12, dependsOnMethods = {"userLogin"}) void
		  getSpecificChannelDetails() { given() .pathParam("organizations_id",
		  organization_id)
		  
		  .when() .get(
		  "https://chat-service-s5g2.onrender.com/channel/{channel_id}/org/{organization_id}")
		  .then()
		  
		  .statusCode(200)
		  .body("message",equalTo("This is the details of the specific channel"))
		  .log().all(); }
		  
		  @AfterClass public void teardown()
		  {
			  
		  }
		 
	
	
	

}
