package org.example.pages;

import org.example.utils.DriverFactory;
import org.example.utils.WaitUtils;
import org.openqa.selenium.*;
import java.util.List;

public class PIMPage {

    private final By loader = By.cssSelector(".oxd-form-loader");
    private final By pimMenu = By.xpath("//span[normalize-space()='PIM']");
    private final By addEmployeeMenu = By.xpath("//a[normalize-space()='Add Employee']");
    private final By employeeListMenu = By.xpath("//a[normalize-space()='Employee List']");
    private final By firstName = By.name("firstName");
    private final By lastName = By.name("lastName");

    private final By employeeIdField =
            By.xpath("//label[text()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");

    private final By profilePhoto = By.cssSelector("input[type='file']");
    private final By saveButton = By.xpath("//button[@type='submit']");
    private final By employeeIdSearchBox = By.xpath("//label[text()='Employee Id']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.xpath("//button[normalize-space()='Search']");
    private final By firstEditButton = By.xpath("(//i[contains(@class,'bi-pencil-fill')])[1]");
    private final By firstDeleteButton = By.xpath("(//i[contains(@class,'bi-trash')])[1]");
    private final By confirmDeleteButton = By.xpath("//button[normalize-space()='Yes, Delete']");


    private final By successToast =
            By.xpath("//p[contains(@class,'oxd-text--toast-message') or contains(@class,'oxd-toast-content-text')]");

    private final By noRecordsFound = By.xpath("//*[contains(text(),'No Records Found')]");
    private final By jobMenu = By.xpath("//a[normalize-space()='Job']");

    private final By jobTitleDropdown =
            By.xpath("//label[text()='Job Title']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");

    private final By employmentStatusDropdown =
            By.xpath("//label[text()='Employment Status']/ancestor::div[contains(@class,'oxd-input-group')]//div[contains(@class,'oxd-select-text')]");

    private final By tableRows =
            By.xpath("//div[@class='oxd-table-body']//div[@role='row']");

    public void addEmployee(String firstNameValue, String lastNameValue, String employeeIdValue, String imagePath) {
        WaitUtils.waitForInvisibility(loader);

        WaitUtils.waitForClickable(pimMenu).click();
        WaitUtils.waitForClickable(addEmployeeMenu).click();

        WaitUtils.waitForVisibility(firstName).sendKeys(firstNameValue);
        WaitUtils.waitForVisibility(lastName).sendKeys(lastNameValue);

        WebElement empId = WaitUtils.waitForVisibility(employeeIdField);
        empId.sendKeys(Keys.CONTROL + "a");
        empId.sendKeys(Keys.DELETE);
        empId.sendKeys(employeeIdValue);

        System.out.println("Employee ID Entered: " + employeeIdValue);

        try {
            WaitUtils.waitForPresence(profilePhoto).sendKeys(imagePath);
        } catch (Exception e) {
            System.out.println("Profile image upload skipped");
        }

        WaitUtils.waitForInvisibility(loader);
        WaitUtils.waitForClickable(saveButton).click();
        WaitUtils.waitForUrlContains("viewPersonalDetails");

        System.out.println("Employee Created With ID : " + employeeIdValue);
    }

    public void searchEmployee(String employeeIdValue) {

        WaitUtils.waitForInvisibility(loader);

        WaitUtils.waitForClickable(pimMenu).click();
        WaitUtils.waitForClickable(employeeListMenu).click();
        WaitUtils.waitForInvisibility(loader);

        WebElement searchBox = WaitUtils.waitForVisibility(employeeIdSearchBox);
        searchBox.clear();
        searchBox.sendKeys(employeeIdValue);


        WaitUtils.waitForClickable(searchButton).click();
        WaitUtils.waitForInvisibility(loader);
    }

    public void openEmployeeRecord(){
        WaitUtils.waitForInvisibility(loader);
        try{
            WaitUtils.waitForClickable(firstEditButton).click();
        }catch(StaleElementReferenceException e){
            WaitUtils.waitForClickable(firstEditButton).click();
        }
        WaitUtils.waitForUrlContains("viewPersonalDetails");
        WaitUtils.waitForInvisibility(loader);
    }

    public void updateJobDetails(String jobTitle,String employmentStatus){
        WaitUtils.waitForInvisibility(loader);
        WaitUtils.waitForClickable(jobMenu).click();
        WaitUtils.waitForInvisibility(loader);
        System.out.println("Current URL : " + DriverFactory.getDriver().getCurrentUrl());
        selectDropdownValue(jobTitleDropdown,jobTitle);
        selectDropdownValue(employmentStatusDropdown,employmentStatus);
        WaitUtils.waitForClickable(saveButton).click();
        WaitUtils.waitForVisibility(successToast);
    }

    private void selectDropdownValue(By dropdown,String value){
        WaitUtils.waitForInvisibility(loader);
        try{
            WaitUtils.waitForClickable(dropdown).click();
        }catch(StaleElementReferenceException e){
            WaitUtils.waitForClickable(dropdown).click();
        }

        List<WebElement> options = DriverFactory.getDriver().findElements(By.xpath("//div[@role='option']"));
        for(WebElement option : options){

            try{
                if(option.getText().trim().equalsIgnoreCase(value)){
                    option.click();
                    return;
                }
            }catch(StaleElementReferenceException ignored){
            }
        }
        throw new RuntimeException("Dropdown value not found : " + value);
    }

    public void deleteEmployee() {

        WaitUtils.waitForInvisibility(loader);
        ((JavascriptExecutor) DriverFactory.getDriver())
                .executeScript("arguments[0].click();", WaitUtils.waitForVisibility(firstDeleteButton));

        WaitUtils.waitForClickable(confirmDeleteButton).click();
        WaitUtils.waitForVisibility(successToast);
        WaitUtils.waitForInvisibility(loader);
        System.out.println("Employee deleted successfully");
    }

    public boolean isEmployeeDeleted(String employeeIdValue){

        searchEmployee(employeeIdValue);
        WaitUtils.waitForInvisibility(loader);
        List<WebElement> rows = DriverFactory.getDriver().findElements(tableRows);
        System.out.println("Rows Found : " + rows.size());
        return rows.isEmpty();
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            return WaitUtils.waitForVisibility(successToast).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }
}