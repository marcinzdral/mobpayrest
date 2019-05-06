package fuel;

import org.testng.annotations.*;
import utils.*;

import java.util.*;

public class FuelFullHappyPath extends BaseCore {

    //@Test(dataProviderClass = DataProviderManager.class, dataProvider = "fuel_bu")
    public void fuelProcessFullOrder(HashMap<String, Object> createData) {

        fuelRequest
                .hasAvailablePump()
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
