package carwash;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class PostNewCarwashOrder extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void createNewCarWashOrder() {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .with().body(this.getBody())
                        .when()
                        .request("POST", resource.getMobileBackendUrl() + "/orders/carwash/create")
                        .then()
                        .statusCode(200)
                        .extract().response();

        resource.setOrderId(response.path("orderId"));
        resource.setTotalAmount(response.path("totalAmount"));
    }


    private Map<String, Object> getBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("userLocationDuringCarwash", "STAY_IN");
        body.put("mobileUser", "1234");
        body.put("pspSelector", "MOBILE_PAY");
        body.put("siteId", 10555);
        body.put("articleNo", "1113585");

        return body;
    }
}
