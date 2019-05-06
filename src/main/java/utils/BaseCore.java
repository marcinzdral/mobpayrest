package utils;

import org.apache.log4j.*;
import org.testng.annotations.*;
import requests.*;

import java.util.ResourceBundle;

@Listeners({org.uncommons.reportng.HTMLReporter.class})
public class BaseCore {


    protected Resource resource = Resource.getInstance();
    protected FuelRequest fuelRequest;
    protected CarwashRequest carwashRequest;
    protected static final Logger LOGGER = Logger.getLogger(Thread.currentThread().getStackTrace()[0].getClassName());
    protected static ResourceBundle rb = ResourceBundle.getBundle("global");

    @BeforeSuite
    public void setConfig() {
        fuelRequest = new FuelRequest();
        carwashRequest = new CarwashRequest();
    }
}

