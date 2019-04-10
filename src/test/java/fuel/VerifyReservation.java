package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.*;

public class VerifyReservation extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void verifyReserved() {

        given()
                .header("Content-Type", "application/json")
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId() + "/verify-reserved")
                .then()
                .statusCode(200);


    }
}
