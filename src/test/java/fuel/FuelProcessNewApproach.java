package fuel;

import org.testng.annotations.*;
import utils.*;

public class FuelProcessNewApproach extends BaseCore {

    @Test
    public void fuelProcessFullOrder() {

        fuelRequest
                .postNewFuelOrder()
                .checkFuelingStatus_created()
                .reserveFunds_fuel()
                .checkFuelingStatus_reserved()
                .verifyReservation()
                .unlockNozzle()
                .checkFuelingStatus_serviceReady()
                .checkFuelingStatus_serviceFinanced();
    }
}
