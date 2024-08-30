package swaglabs.factory;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

public class DriverFactory {
    public static String highlight;
//    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();
    public static WebDriver driver;
    Properties prop;
    private OptionsManager optionsManager;
    public DesiredCapabilities capabilities;

    public static WebDriver getDriver() {
        return driver;
    }

    // This method is used to intilize the driver
    public WebDriver initDriver(Properties prop) {
        String browserName = prop.getProperty("browser");
        optionsManager = new OptionsManager(prop);
        highlight = prop.getProperty("highlight");
        System.out.println("Brower Name is:" + browserName);
        if (browserName.equalsIgnoreCase("chrome")) {
            if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                init_remoteDriver(browserName);
            } else {
                driver=new ChromeDriver(optionsManager.getchromeOptions());
            }
        } else if (browserName.equalsIgnoreCase("firefox")) {
            if (Boolean.parseBoolean(prop.getProperty("remote"))) {
                init_remoteDriver("firefox");
            } else {
                driver=new FirefoxDriver(optionsManager.getfirefoxOptions());
            }
        } else {
            System.out.println("Please pass correct browser Name:" + browserName);
        }
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get(prop.getProperty("URL"));
        return driver;
    }

    private void init_remoteDriver(String browserName) {
        System.out.println("Running test on remote browser:" + browserName);
        capabilities=new DesiredCapabilities();

        //os
        if(prop.getProperty("os").equalsIgnoreCase("windows"))
        {
            capabilities.setPlatform(Platform.WIN11);
        }
        else if(prop.getProperty("os").equalsIgnoreCase("linux"))
        {
            capabilities.setPlatform(Platform.LINUX);

        }
        else if (prop.getProperty("os").equalsIgnoreCase("mac"))
        {
            capabilities.setPlatform(Platform.MAC);
        }
        else
        {
            System.out.println("No matching os");
            return;
        }
        try {
            switch (browserName.toLowerCase().trim()) {
                case "chrome":
                    driver= new RemoteWebDriver(new URL(prop.getProperty("huburl")), capabilities);
                    break;
                case "firefox":
                    driver= new RemoteWebDriver(new URL(prop.getProperty("huburl")), capabilities);
                    break;
                default:
                    System.out.println("wrong browser info..can not run on grid remote machine....");
                    break;
            }
        } catch (MalformedURLException e) {
        }
    }

    public Properties initProperties() {
        FileInputStream ip = null;
        prop = new Properties();
        String env = System.getProperty("env"); // will be passing through maven as mvn clean install -Denv
        try {
            if (env == null) {
                System.out.println("Running on PROD environment as no envrionment is specified");

                ip = new FileInputStream("./config.properties");
            } else {
                System.out.println("Running on the environment:" + env);
                switch (env) {
                    case "staging":
                        ip = new FileInputStream("./src/test/resources/staging.config.properties");
                        break;
                    default:
                        System.out.println("No Env found");
                        throw new Exception("NOENVFOUNDEXCEPTION");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            // prop = new Properties();
            prop.load(ip);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return prop;
    }

    public String getScreenshot() {
        File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";

        File destination = new File(path);
        try {
            FileHandler.copy(srcFile, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return path;
    }
}
