package utils;

import org.apache.log4j.*;
import org.testng.annotations.*;
import requests.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class})
public class BaseCore {


    protected Resource resource = Resource.getInstance();
    protected FuelRequest fuelRequest;
    protected CarwashRequest carwashRequest;
    protected static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());

    @BeforeSuite
    public void setConfig() {
        fuelRequest = new FuelRequest();
        carwashRequest = new CarwashRequest();
    }

    @BeforeMethod
    public void print() {
        System.out.println("method name");
    }
}

