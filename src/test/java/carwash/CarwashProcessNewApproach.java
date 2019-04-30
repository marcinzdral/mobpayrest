package carwash;

import org.testng.annotations.*;
import utils.*;

import java.util.*;

public class CarwashProcessNewApproach extends BaseCore {

    @Test(dataProviderClass = DataProviderManager.class, dataProvider = "carwash_bu")
    public void carwashProcessFullOrder(HashMap<String, Object> createData) {

        carwashRequest
                .postNewCarwashOrder(createData)
                .checkCarwashStatus_created()
                .reserveFunds_carwash()
                .checkCarwashStatus_reserved()
                .verifyReservation()
                .checkCarwashStatus_waitingForCustomer()
                .startCarwash()
                .checkCarwashStatus_serviceReady()
                .checkCarwashStatus_serviceInUse()
                .checkCarwashStatus_serviceCompleted();
    }
}
