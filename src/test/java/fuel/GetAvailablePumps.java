package fuel;

import org.testng.annotations.*;
import utils.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class GetAvailablePumps extends BaseCore {

    @Test
    public void hasAvailablePump() {

        when()
                .request("GET", resource.getMobileBackendUrl() + "/sites/10555/pumps")
                .then()
                .assertThat()
                .statusCode(200).and()
                .body("status", equalTo("AVAILABLE"));
    }
}
