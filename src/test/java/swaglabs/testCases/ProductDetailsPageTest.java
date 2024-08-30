package swaglabs.testCases;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import swaglabs.listeners.ExtentReportListener;
import swaglabs.utils.Constants;

@Listeners(ExtentReportListener.class)
public class ProductDetailsPageTest extends BaseTest {
    @BeforeClass
    public void prodDetailsPageSetup() {
        prodlistingPage = loginpage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
        proDetailsPage = prodlistingPage.selectProduct("Sauce Labs Bike Light");
    }

    @Test(priority = 1)
    public void CartPageHeaderTest() {
        cartPage = proDetailsPage.clickonCartandIcon();
        String cartPageHeader = cartPage.getCartPageHeader();
        System.out.println("Cart Page Header is: " + cartPageHeader);
        Assert.assertEquals(cartPageHeader, Constants.CART_PAGE_HEADER);

    }
}
