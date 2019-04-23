package carwash;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class CheckCarwashStatusCreated extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void checkCarwashStatus_created() {

        given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.CREATED.getValue()));
    }
}
