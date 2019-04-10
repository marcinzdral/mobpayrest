package fuel;

import org.testng.annotations.Test;
import utils.*;

import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.hasItem;

public class GetAvailablePumps extends BaseCore {

    @Test
    public void hasAvailablePump() {

        when()
                .request("GET", resource.getMobileBackendUrl() + "/sites/10555/pumps")
                .then()
                .assertThat()
                .statusCode(200)
                .and()
                .body("status", hasItem("AVAILABLE"));
    }
}
