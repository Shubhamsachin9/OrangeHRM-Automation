package org.example.test;

import io.restassured.response.Response;
import org.example.api.EmployeeAPI;
import org.example.base.BaseTest;
import org.example.model.Employee;
import org.example.pages.DashboardPage;
import org.example.pages.LoginPage;
import org.example.pages.PIMPage;
import org.example.utils.DriverFactory;
import org.example.utils.JsonReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class EmployeeLifeCycleTest extends BaseTest {

    private static final AtomicInteger EMP_COUNTER=new AtomicInteger(1000);

    @Test
    public void employeeLifecycleTest() {

        // Read employee data from json
        Employee employee = JsonReader.getEmployeeData();

        LoginPage loginPage = new LoginPage();
        PIMPage pimPage = new PIMPage();
        DashboardPage dashboardPage = new DashboardPage();



        // Generate unique employee id for every execution
        String employeeId = "EMP" + EMP_COUNTER.incrementAndGet();

        // Login to application
        loginPage.login("Admin", "admin123");

        Assert.assertTrue(
                DriverFactory.getDriver()
                        .getCurrentUrl()
                        .contains("dashboard"),
                "Login failed");

        System.out.println("TC01 Login Passed");

        // Create a new employee
        pimPage.addEmployee(
                employee.getFirstName(),
                employee.getLastName(),
                employeeId,
                System.getProperty("user.dir")
                        + "/src/test/resources/profile.png"
        );

        Assert.assertTrue(
                DriverFactory.getDriver()
                        .getCurrentUrl()
                        .contains("viewPersonalDetails"),
                "Employee creation failed");

        System.out.println("Employee Created With ID : " + employeeId);
        System.out.println("TC02 Add Employee Passed");

        // Search the employee we just created
        pimPage.searchEmployee(employeeId);

        // Open employee details page
        pimPage.openEmployeeRecord();

        // Update employee job information
        pimPage.updateJobDetails(
                employee.getJobTitle(),
                employee.getEmploymentStatus()
        );

        Assert.assertTrue(pimPage.isSuccessMessageDisplayed(), "Employee update failed");
        System.out.println("TC03 Edit Employee Passed");

        // API validation
        Response response = new EmployeeAPI().getUser(2);
        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "API validation failed");

        Assert.assertNotNull(response.jsonPath().getString("data.first_name"));
        Assert.assertNotNull(response.jsonPath().getString("data.last_name"));
        System.out.println("TC04 API Validation Passed");

        // Search employee again before deletion
        pimPage.searchEmployee(employeeId);

        // Delete employee
        pimPage.deleteEmployee();
        Assert.assertTrue(pimPage.isEmployeeDeleted(employeeId), "Employee deletion failed");

        System.out.println("TC05 Delete Employee Passed");

        // Logout
        dashboardPage.logout();
        Assert.assertTrue(DriverFactory.getDriver()
                        .getCurrentUrl()
                        .contains("login"),
                "Logout failed");

        // Verify session is invalidated
        DriverFactory.getDriver().get("https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index");
        Assert.assertTrue(DriverFactory.getDriver()
                        .getCurrentUrl()
                        .contains("login"),
                "Session is still active after logout");

        System.out.println("TC06 Logout Passed");

        System.out.println("==================================");
        System.out.println("Employee Lifecycle Test Completed");
        System.out.println("==================================");
    }
}