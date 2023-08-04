import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import io.restassured.module.jsv.JsonSchemaValidator;
import java.io.File;
import static org.hamcrest.Matchers.*;

@Test
public class APITest {

    // all URLs and Paths defined as global variables, to make it can be reused in other functions
    public static String baseUrl = "https://jsonplaceholder.typicode.com/posts";
    public static String pathSchema="src/test/Data/schema.json";
    public static String pathDataInput="src/test/Data/dataInput.json";

    public void VerifyJSONSchema(){
        given()
                .when().get(baseUrl)
                .then().assertThat()
                .body(JsonSchemaValidator.matchesJsonSchema(new File(pathSchema)));
    }

    public void VerifyInputedPayload(){
        given().body(pathDataInput)
                .when().post(baseUrl)
                .then().statusCode(201)
                .body("id", greaterThan(100)); // make sure id > 100 because there are already 100 existing data
    }
}
