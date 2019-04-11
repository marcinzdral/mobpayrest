package fuel;

import org.awaitility.*;
import org.testng.annotations.*;
import utils.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CheckFuelingStatusServiceFinanced extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void checkFuelingStatus_serviceFinanced() {

        Awaitility.await().atMost(60, TimeUnit.SECONDS).until(() -> this.getFuelingStatus().equals("SERVICE_FINANCED"));
        Awaitility.setDefaultPollInterval(2, TimeUnit.SECONDS);

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

    private String getFuelingStatus() {

        return given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().get("status");
    }
}

