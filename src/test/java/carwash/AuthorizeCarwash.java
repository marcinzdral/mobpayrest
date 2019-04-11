package carwash;

import io.restassured.http.*;
import org.testng.annotations.*;
import utils.*;

import java.util.*;

import static io.restassured.RestAssured.given;

public class AuthorizeCarwash extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void startCarwash() {

        given()
                .contentType(ContentType.JSON)
                .with().body(this.getBody())
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId() + "/start-carwash")
                .then()
                .statusCode(200);
    }

    private Map<String, Object> getBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("washpointNo", 1);

        return body;
    }
}
