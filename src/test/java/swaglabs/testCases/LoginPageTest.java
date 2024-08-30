package swaglabs.testCases;

import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import swaglabs.listeners.ExtentReportListener;
import swaglabs.pages.ProductListingPage;
import swaglabs.utils.Constants;

@Listeners(ExtentReportListener.class)
public class LoginPageTest extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LoginPageTest.class);

    @Description("Login Page Title Test")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 1)
    public void loginPageTitleTest() {
        logger.info("********Login Page Title Test Starts*****");
        String loginPageTitle = loginpage.getLoginPageTitle();
        Assert.assertEquals(loginPageTitle, Constants.LOGIN_PAGE_TITLE);
    }

    @Description("Login Page Header Test")
    @Severity(SeverityLevel.NORMAL)
    @Test(priority = 2)
    public void loginPageHeaderTest() {
        String loginHeader = loginpage.getLoginPageHeaderText();
        logger.info("Login Page Header is:" + loginHeader);
        Assert.assertEquals(loginHeader, Constants.LOGIN_PAGE_HEADER);
    }

    @Description("Checking Login Credentails")
    @Severity(SeverityLevel.CRITICAL)
    @Test(priority = 3)
    public void loginTest() {
        ProductListingPage prodlistingPage = loginpage.doLogin(prop.getProperty("username").trim(),
                prop.getProperty("password").trim());
        Assert.assertEquals(prodlistingPage.getProductListHeader(), Constants.PRODUCT_LIST_HEADER);
    }
}
