package requests;

import io.restassured.http.*;
import io.restassured.response.*;
import org.awaitility.*;
import utils.*;

import java.util.*;
import java.util.concurrent.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class FuelRequest extends Body {

    public FuelRequest hasAvailablePump() {

        given()
                .log().uri()
                .when()
                .request("GET", "${mbUrl}" + "/sites/" + getFuelBody_createOrder_DK().get("siteId") + "/pumps")
                .then()
                .assertThat()
                .statusCode(200).and()
                .body("status.any {it == 'AVAILABLE xxx'}", is(true));

        return this;
    }

    public FuelRequest postNewFuelOrder(HashMap<String, Object> createData) {

        Response response;
        response = given()
                .contentType(ContentType.JSON)
                .with().body(createData)
                .log().uri()
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
        resource.setProcessId(response.path("id"));
        resource.setPresetAmount(response.path("presetAmount"));
        resource.setPumpId(((int) getFuelBody_createOrder_DK().get("pumpId")));
        resource.setSiteId((int) getFuelBody_createOrder_DK().get("siteId"));

        return this;
    }

    public FuelRequest checkFuelingStatus_created() {

        given()
                .log().uri()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getProcessId())).and()
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
                .when()
                .request("POST", resource.getMobilePayUrl() + "/orders/" + resource.getOrderId() + "/_reserve_funds")
                .then()
                .statusCode(200);


        return this;
    }

    public FuelRequest checkFuelingStatus_reserved() {

        given()
                .log().uri()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified")).and()
                .body("id", equalTo(resource.getProcessId())).and()
                .body("status", equalTo(FuelStatus.RESERVED.getValue())).and()
                .body("pumpId", equalTo(resource.getPumpId()));

        return this;
    }

    public FuelRequest verifyReservation() {

        given()
                .header("Content-Type", "application/json")
                .log().uri()
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId() + "/verify-reserved")
                .then()
                .log().ifError()
                .statusCode(200);

        return this;
    }

    public FuelRequest unlockNozzle() {

        given()
                .log().uri()
                .when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId() + "/unlock-pump")
                .then()
                .statusCode(200);

        return this;
    }

    public FuelRequest checkFuelingStatus_serviceReady() {

        given()
                .log().uri()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified")).and()
                .body("id", equalTo(resource.getProcessId())).and()
                .body("status", equalTo(FuelStatus.SERVICE_READY.getValue())).and()
                .body("pumpId", equalTo(resource.getPumpId())).and()
                .body("size()", is(5));

        return this;
    }

    public FuelRequest checkFuelingStatus_serviceInUse() {

        Awaitility.await().atMost(60, TimeUnit.SECONDS).until(
                () -> this.getFuelingStatus().equals(FuelStatus.SERVICE_IN_USE.getValue()));
        Awaitility.setDefaultPollInterval(2, TimeUnit.SECONDS);

        given()
                .log().uri()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("$", hasKey("created")).and()
                .body("$", hasKey("modified")).and()
                .body("id", equalTo(resource.getProcessId())).and()
                .body("status", equalTo(FuelStatus.SERVICE_IN_USE.getValue())).and()
                .body("pumpId", equalTo(resource.getPumpId())).and()
                .body("size()", is(5));

        return this;

    }

    public FuelRequest checkFuelingStatus_serviceFinanced() {

        Awaitility.await().atMost(80, TimeUnit.SECONDS).until(
                () -> this.getFuelingStatus().equals(FuelStatus.SERVICE_FINANCED.getValue()));
        Awaitility.setDefaultPollInterval(2, TimeUnit.SECONDS);

        given()
                .log().uri()
                .when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId())
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(resource.getProcessId())).and()
                .body("status", equalTo(FuelStatus.SERVICE_FINANCED.getValue())).and()
                .body("receipt.pumpId", equalTo(resource.getPumpId())).and()
                .body("size()", is(3)).and()
                .body("receipt.size()", is(16));

        return this;
    }

    private String getFuelingStatus() {

        return when()
                .request("GET", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getProcessId())
                .then()
                .statusCode(200)
                .extract()
                .jsonPath().get("status");
    }
}
