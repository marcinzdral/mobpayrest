package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CheckFuelingStatusServiceFinanced extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void checkFuelingStatus_serviceFinanced() {

            when()
                    .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                    .then()
                    .statusCode(200)
                    .assertThat()
                    .body("id", equalTo(resource.getOrderId())).and()
                    .body("status", equalTo("SERVICE_FINANCED")).and()
                    .body("pumpId", equalTo(resource.getPumpId())).and()
                    .body("size()", is(17));

    }
}
