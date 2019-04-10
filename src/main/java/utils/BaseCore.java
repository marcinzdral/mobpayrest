package utils;

import org.testng.annotations.BeforeSuite;

public class BaseCore {

    protected Resource resource = Resource.getInstance();

    @BeforeSuite
    public void setConfig() {

    }
}
