package utils;

import org.testng.annotations.*;
import requests.*;

import java.util.*;

@Listeners({org.uncommons.reportng.HTMLReporter.class})
public class BaseCore {


    protected Resource resource = Resource.getInstance();
    protected FuelRequest fuelRequest;
    protected CarwashRequest carwashRequest;
    protected static ResourceBundle rb = ResourceBundle.getBundle("global");

    @BeforeSuite
    public void setConfig() {
        fuelRequest = new FuelRequest();
        carwashRequest = new CarwashRequest();
    }
}

