package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class CheckFuelingStatusCreated extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void checkFuelingStatus_created() {

        when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo("CREATED")).and()
                .body("siteId", equalTo(resource.getSiteId())).and()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified"));
    }
}
