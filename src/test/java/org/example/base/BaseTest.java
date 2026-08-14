package org.example.base;

import org.example.utils.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    @BeforeMethod
    public void setup(){
        DriverFactory.initializeDriver();
        DriverFactory.getDriver().get("https://opensource-demo.orangehrmlive.com");
    }


    @AfterMethod
    public void tearDown(){
        DriverFactory.quitDriver();
    }
}
