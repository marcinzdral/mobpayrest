package requests;

import io.restassured.http.*;
import io.restassured.response.*;
import org.awaitility.*;
import utils.*;

import java.util.concurrent.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FuelRequest extends Body {

    public FuelRequest postNewFuelOrder() {

        Response response;
        response = given()
                .contentType(ContentType.JSON)
                .with().body(getFuelBody_createOrder())
                .log().uri()
                .log().method()
                .log().body()
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/create")
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("orderId")).and()
                .body("$", hasKey("presetAmount")).and()
                .body("$", hasKey("currency"))
                .extract().response();

        resource.setOrderId(response.path("orderId"));
        resource.setPresetAmount(response.path("presetAmount"));
        resource.setPumpId(((int) getFuelBody_createOrder().get("pumpId")));
        resource.setSiteId((int) getFuelBody_createOrder().get("siteId"));

        return this;
    }

    public FuelRequest checkFuelingStatus_created() {

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(FuelStatus.CREATED.getValue())).and()
                .body("siteId", equalTo(resource.getSiteId())).and()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified"));

        return this;
    }

    public FuelRequest reserveFunds_fuel() {

        given()
                .contentType(ContentType.JSON)
                .with().body(getFuelBody_reserveFunds())
                .log().uri()
                .log().method()
                .when()
                .request("POST", resource.getMobilePayUrl() + "/orders/" + resource.getOrderId() + "/_reserve_funds")
                .then()
                .statusCode(200);


        return this;
    }

    public FuelRequest checkFuelingStatus_reserved() {

        given()
                .log().uri()
                .log().method()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified")).and()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(FuelStatus.RESERVED.getValue())).and()
                .body("pumpId", equalTo(resource.getPumpId()));

        return this;
    }

    public FuelRequest verifyReservation() {

        given()
                .header("Content-Type", "application/json")
                .log().uri()
                .log().method()
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId() + "/verify-reserved")
                .then()
                .statusCode(200);

        return this;
    }

    public FuelRequest unlockNozzle() {

        given()
                .log().uri()
                .log().method()
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId() + "/unlock-pump")
                .then()
                .statusCode(200);

        return this;
    }

    public FuelRequest checkFuelingStatus_serviceReady() {

        given()
                .log().uri()
                .log().method()
                .when()
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

        return this;
    }

    public FuelRequest checkFuelingStatus_serviceFinanced() {

        Awaitility.await().atMost(60, TimeUnit.SECONDS).until(() -> this.getFuelingStatus().equals(FuelStatus.SERVICE_FINANCED.getValue()));
        Awaitility.setDefaultPollInterval(2, TimeUnit.SECONDS);

        when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getOrderId())).and()
                .body("status", equalTo(FuelStatus.SERVICE_FINANCED.getValue())).and()
                .body("pumpId", equalTo(resource.getPumpId())).and()
                .body("size()", is(17));

        return this;
    }

    private String getFuelingStatus() {

        return when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().get("status");
    }
}
