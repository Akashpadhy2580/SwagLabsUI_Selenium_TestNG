package swaglabs.testCases;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import swaglabs.factory.DriverFactory;
import swaglabs.pages.*;

import java.util.Properties;


public class BaseTest {

    WebDriver driver;
    DriverFactory df;
    Properties prop;
    LoginPage loginpage;
    ProductListingPage prodlistingPage;
    ProductDetailsPage proDetailsPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    CheckoutOverviewPage checkoutoverviewPage;
    OrderCompletePage ordercomPage;

    @Parameters({"os","browser"})
    @BeforeTest
    public void setup(String os,String browserName) {

        df = new DriverFactory();
        prop = df.initProperties();
        if (browserName != null) {
            prop.setProperty("browser", browserName);
            prop.setProperty("os", os);
        }
        driver = df.initDriver(prop);
        loginpage = new LoginPage(driver);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}
