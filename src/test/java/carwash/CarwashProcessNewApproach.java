package carwash;

import org.testng.annotations.*;
import utils.*;

public class CarwashProcessNewApproach extends BaseCore {

    @Test
    public void carwashProcessFullOrder() {

        carwashRequest
                .postNewCarwashOrder()
                .checkCarwashStatus_created()
                .reserveFunds_carwash()
                .checkCarwashStatus_reserved()
                .checkCarwashStatus_waitingForCustomer()
                .startCarwash()
                .checkCarwashStatus_serviceReady()
                .checkCarwashStatus_serviceInUse()
                .checkCarwashStatus_serviceCompleted();
    }
}
