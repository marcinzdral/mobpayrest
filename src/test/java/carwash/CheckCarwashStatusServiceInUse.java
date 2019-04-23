package carwash;

import org.awaitility.*;
import org.testng.annotations.*;
import utils.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class CheckCarwashStatusServiceInUse extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void checkCarwashStatus_serviceInUse() {

        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> this.getCarwashStatus().equals(CarwashStatus.SERVICE_IN_USE.getValue()));
        Awaitility.setDefaultPollInterval(1000, TimeUnit.MILLISECONDS);

        given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.SERVICE_IN_USE.getValue())).and()
                .body("$", hasKey("remainingTime"));

    }

    private String getCarwashStatus() {

        return given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().get("status");
    }
}

