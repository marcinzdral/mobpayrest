package utils;

import org.testng.annotations.*;

public class DataProviderManager extends Body {

    @DataProvider
    public Object[][] fuel_bu() {
        return new Object[][] {
                {getFuelBody_createOrder_DK()}
        };
    }

    @DataProvider
    public Object[][] carwash_bu() {
        return new Object[][] {
                {getCarwashBody_createOrder_DK("STAY_IN")}
                //{getCarwashBody_createOrder_DK("STAY_OUT")}
        };
    }
}
