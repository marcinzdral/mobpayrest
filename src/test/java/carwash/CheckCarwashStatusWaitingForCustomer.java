package carwash;

import org.awaitility.*;
import org.testng.annotations.*;
import utils.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CheckCarwashStatusWaitingForCustomer extends BaseCore {

    @Test(groups = {"denmark.carwash"})
    public void checkCarwashStatus_waitingForCustomer() {

        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> this.getCarwashStatus().equals(CarwashStatus.WAITING_FOR_CUSTOMER.getValue()));
        Awaitility.setDefaultPollInterval(200, TimeUnit.MILLISECONDS);

        given()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.WAITING_FOR_CUSTOMER.getValue())).and()
                .body("stayIn", equalTo(true)).and()
                .body("washpointsStatuses[0].washpointNo", equalTo(1)).and()
                .body("washpointsStatuses[0].available", equalTo(true));
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
