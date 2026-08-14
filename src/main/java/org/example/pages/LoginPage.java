package org.example.pages;

import org.example.utils.WaitUtils;
import org.openqa.selenium.By;

public class LoginPage {

    private final By username =
            By.name("username");

    private final By password =
            By.name("password");

    private final By loginBtn =
            By.xpath("//button[@type='submit']");

    public void login(String user,String pass){

        WaitUtils.waitForVisibility(username)
                .sendKeys(user);

        WaitUtils.waitForVisibility(password)
                .sendKeys(pass);

        WaitUtils.waitForClickable(loginBtn)
                .click();
    }
}
