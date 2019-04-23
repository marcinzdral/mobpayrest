package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CheckFuelingStatusServiceReady extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void checkFuelingStatus_serviceReady() {

        when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified")).and()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(FuelStatus.SERVICE_READY.getValue())).and()
                .body("pumpId", equalTo(resource.getPumpId())).and()
                .body("size()", is(5));
    }
}
