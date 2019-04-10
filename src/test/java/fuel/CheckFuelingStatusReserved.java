package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CheckFuelingStatusReserved extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void checkFuelingStatus_reserved() {

        when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified")).and()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo("RESERVED")).and()
                .body("pumpId", equalTo(resource.getPumpId()));
    }
}
