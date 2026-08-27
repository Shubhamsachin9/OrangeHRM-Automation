package org.example.pages;

import org.example.utils.WaitUtils;
import org.openqa.selenium.By;

public class DashboardPage {

    private final By profileMenu = By.cssSelector(".oxd-userdropdown-tab");
    private final By logoutButton = By.xpath("//a[text()='Logout']");

    public void logout() {

        WaitUtils.waitForClickable(profileMenu).click();
        WaitUtils.waitForClickable(logoutButton).click();
    }
}
