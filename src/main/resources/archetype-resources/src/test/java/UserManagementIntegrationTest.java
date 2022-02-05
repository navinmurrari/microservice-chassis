package ${package};
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(classes = Application.class,webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class UserManagementIntegrationTest {
    @LocalServerPort
    private int port;

    @BeforeEach
    public void setUp(){
        RestAssured.baseURI = String.format("http://localhost:%d",port);
    }

    private String requestBody = "{\n" +
            "  \"username\": \"test_name\",\n" +
            "  \"firstName\": \"test_first_name\",\n" +
            "  \"lastName\": \"test_last_name\",\n" +
            "  \"email\": \"test@gmail.com\",\n" +
            "  \"password\": \"test@123\",\n" +
            "  \"phone\": \"776655433\"\n" +
            "}";


    @Test
    public void createUser() {
        Response response = given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("/user")
                .then()
                .extract().response();
        Assertions.assertEquals(200, response.statusCode());
    }

}

