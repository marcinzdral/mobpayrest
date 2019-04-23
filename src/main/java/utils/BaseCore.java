package utils;

import org.testng.annotations.*;
import requests.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class})
public class BaseCore {


    protected Resource resource = Resource.getInstance();
    protected FuelRequest fuelRequest;
    protected CarwashRequest carwashRequest;

    @BeforeSuite
    public void setConfig() {
        fuelRequest = new FuelRequest();
        carwashRequest = new CarwashRequest();
    }

}
