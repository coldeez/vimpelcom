package appmanager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * Created by kbal on 02.03.2016.
 */
public class ApplicationManager {
    private final Properties properties;
    WebDriver wd;
    public NavigationHelper navigationHelper;
    private String browser;
    private static ApplicationManager instance;

    public static ApplicationManager getInstance() {
        if(instance == null) {
            instance = new ApplicationManager(System.getProperty("browser", BrowserType.FIREFOX));
        }
        return instance;
    }



    private ApplicationManager(String browser) {
        this.browser = browser;
        properties = new Properties();
    }


    public void init() throws IOException {
        wd = new FirefoxDriver();
        wd.manage().timeouts().implicitlyWait(0, TimeUnit.SECONDS);
        navigationHelper = new NavigationHelper(wd);
    }


    public void stop() {
        wd.quit();
    }



    public NavigationHelper goTo() {
        return navigationHelper;
    }



}
