import org.testng.annotations.Test;
import utils.BaseCore;

public class TestMaven extends BaseCore {

    @Test
    public void mavenTest() {
        fuelRequest
                .hasAvailablePump();
    }
}
