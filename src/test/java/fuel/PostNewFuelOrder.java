package fuel;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import utils.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class PostNewFuelOrder extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void createNewFuelOrder() {

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .with().body(getBody())
                        .when()
                        .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/create")
                        .then()
                        .assertThat().statusCode(200)
                        .body("$", hasKey("orderId")).and()
                        .body("$", hasKey("presetAmount")).and()
                        .body("$", hasKey("currency"))
                        .extract().response();

        resource.setOrderId(response.path("orderId"));
        resource.setPresetAmount(response.path("presetAmount"));

        resource.setPumpId(((int) getBody().get("pumpId")));
        resource.setSiteId((int) getBody().get("siteId"));

    }

    private Map<String, Object> getBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("siteId", 10555);
        body.put("mobileDeviceId", "1234");
        body.put("pspSelector", "MOBILE_PAY");
        body.put("pumpId", 2);

        return body;
    }
}
