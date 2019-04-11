package fuel;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;
import utils.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class MobilePayReserveFundsFuel extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void reserveFunds_fuel() {

        given()
                .contentType(ContentType.JSON)
                .with().body(this.getBody())
                .when()
                        .request("POST", resource.getMobilePayUrl() + "/orders/" + resource.getOrderId() + "/_reserve_funds")
                .then()
                .statusCode(200);
    }

    private Map<String, Object> getBody() {
        Map<String, Object> body = new HashMap<>();
        body.put("Amount", resource.getPresetAmount());

        return body;
    }

}
