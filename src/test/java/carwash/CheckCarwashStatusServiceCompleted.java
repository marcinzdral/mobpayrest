package carwash;

import org.awaitility.*;
import org.testng.annotations.*;
import utils.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class CheckCarwashStatusServiceCompleted extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void checkCarwashStatus_serviceCompleted() {

        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> this.getCarwashStatus().equals(CarwashStatus.SERVICE_COMPLETED.getValue()));
        Awaitility.setDefaultPollInterval(1000, TimeUnit.MILLISECONDS);

        given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.SERVICE_COMPLETED.getValue())).and()
                .body("$", hasKey("receipt"));

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
