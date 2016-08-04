package appmanager;

import org.browsermob.proxy.ProxyServer;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.io.IOException;
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


    public void init() throws Exception {
        ProfilesIni profile = new ProfilesIni();

        FirefoxProfile myprofile = profile.getProfile("profileToolsQA");
        wd = new FirefoxDriver(myprofile);
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
