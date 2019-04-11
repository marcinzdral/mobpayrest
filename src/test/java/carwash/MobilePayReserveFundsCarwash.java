package carwash;

import io.restassured.http.*;
import org.testng.annotations.*;
import utils.*;

import java.util.*;

import static io.restassured.RestAssured.given;

public class MobilePayReserveFundsCarwash extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void reserveFunds_carwash() {

        given()
                .contentType(ContentType.JSON)
                .with().body(getBody())
                .when()
                .request("POST", resource.getMobilePayUrl() + "/orders/" + resource.getOrderId() + "/_reserve_funds")
                .then()
                .statusCode(200);
    }

    private Map<String, Object> getBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("Amount", resource.getTotalAmount());

        return body;
    }
}
