package requests;

import io.restassured.http.*;
import io.restassured.response.*;
import org.awaitility.*;
import utils.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;

public class CarwashRequest extends Body {

    public CarwashRequest postNewCarwashOrder() {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .with().body(getCarwashBody_createOrder())
                        .log().uri()
                        .log().method()
                        .when()
                        .request("POST", resource.getMobileBackendUrl() + "/orders/carwash/create")
                        .then()
                        .statusCode(200)
                        .extract().response();

        resource.setOrderId(response.path("orderId"));
        resource.setTotalAmount(response.path("totalAmount"));

        return this;
    }

    public CarwashRequest checkCarwashStatus_created() {

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.CREATED.getValue()));

        return this;
    }

    public CarwashRequest reserveFunds_carwash() {

        given()
                .contentType(ContentType.JSON)
                .with().body(getCarwashBody_reserveFunds())
                .log().uri()
                .log().method()
                .when()
                .request("POST", resource.getMobilePayUrl() + "/orders/" + resource.getOrderId() + "/_reserve_funds")
                .then()
                .statusCode(200);

        return this;
    }

    public CarwashRequest checkCarwashStatus_reserved() {

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.RESERVED.getValue())).and()
                .body("stayIn", equalTo(true));

        return this;
    }

    public CarwashRequest checkCarwashStatus_waitingForCustomer() {

        Awaitility.await().atMost(10, TimeUnit.SECONDS).until(() -> this.getCarwashStatus().equals(CarwashStatus.WAITING_FOR_CUSTOMER.getValue()));
        Awaitility.setDefaultPollInterval(200, TimeUnit.MILLISECONDS);

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.WAITING_FOR_CUSTOMER.getValue())).and()
                .body("stayIn", equalTo(true)).and()
                .body("washpointsStatuses[0].washpointNo", equalTo(1)).and()
                .body("washpointsStatuses[0].available", equalTo(true));

        return this;
    }

    public CarwashRequest startCarwash() {

        given()
                .log().uri()
                .log().method()
                .contentType(ContentType.JSON)
                .with().body(getCarwashBody_authorizeCarwash())
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId() + "/start-carwash")
                .then()
                .statusCode(200);

        return this;
    }

    public CarwashRequest checkCarwashStatus_serviceReady() {
        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.SERVICE_READY.getValue())).and()
                .body("stayIn", equalTo(true));

        return this;
    }

    public CarwashRequest checkCarwashStatus_serviceInUse() {

        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> this.getCarwashStatus().equals(CarwashStatus.SERVICE_IN_USE.getValue()));
        Awaitility.setDefaultPollInterval(1000, TimeUnit.MILLISECONDS);

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.SERVICE_IN_USE.getValue())).and()
                .body("$", hasKey("remainingTime"));

        return this;
    }

    public CarwashRequest checkCarwashStatus_serviceCompleted() {

        Awaitility.await().atMost(30, TimeUnit.SECONDS).until(() -> this.getCarwashStatus().equals(CarwashStatus.SERVICE_COMPLETED.getValue()));
        Awaitility.setDefaultPollInterval(1000, TimeUnit.MILLISECONDS);

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(CarwashStatus.SERVICE_COMPLETED.getValue())).and()
                .body("$", hasKey("receipt"));

        return this;

    }


    private String getCarwashStatus() {

        return when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/carwash/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().get("status");
    }

}
