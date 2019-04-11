package carwash;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CheckCarwashStatusReserved extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void checkCarwashStatus_reserved() {

        given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo("RESERVED")).and()
                .body("stayIn", equalTo(true));
    }
}
