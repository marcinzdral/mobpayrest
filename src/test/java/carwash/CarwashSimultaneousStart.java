package carwash;

import org.testng.annotations.*;
import utils.*;

import java.util.*;

public class CarwashSimultaneousStart extends BaseCore {

    @Test(dataProviderClass = DataProviderManager.class, dataProvider = "carwash_bu")
    public void carwashProcessFullOrder(HashMap<String, Object> createData) {

        carwashRequest
                .postNewCarwashOrder(createData)
                .checkCarwashStatus_created()
                .reserveFunds_carwash()
                .checkCarwashStatus_reserved()
                .verifyReservation()
                .checkCarwashStatus_waitingForCustomer()
                .startCarwashSimultaneously()
                .checkCarwashStatus_serviceReady()
                .checkCarwashStatus_serviceInUse()
                .checkCarwashStatus_serviceCompleted();
    }
}

