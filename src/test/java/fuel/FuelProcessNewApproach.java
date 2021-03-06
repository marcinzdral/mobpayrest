package fuel;

import org.testng.annotations.*;
import utils.*;

import java.util.*;

public class FuelProcessNewApproach extends BaseCore {

    @Test(dataProviderClass = DataProviderManager.class, dataProvider = "fuel_bu")
    public void fuelProcessFullOrder(HashMap<String, Object> createData) {

        fuelRequest
                .postNewFuelOrder(createData)
                .checkFuelingStatus_created()
                .reserveFunds_fuel()
                .checkFuelingStatus_reserved()
                .verifyReservation()
                .unlockNozzle()
                .checkFuelingStatus_serviceReady()
                .checkFuelingStatus_serviceInUse()
                .checkFuelingStatus_serviceFinanced();
    }
}
