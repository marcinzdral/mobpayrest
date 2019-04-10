package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.when;

public class UnlockNozzle extends BaseCore {

    @Test(groups = {"denmark.fuel"})
    public void unlockNozzle() {

        when()
                .request("POST", resource.getMobileBackendUrl() + "/orders/fuel/" + resource.getOrderId() + "/unlock-pump")
                .then()
                .statusCode(200);
    }
}
