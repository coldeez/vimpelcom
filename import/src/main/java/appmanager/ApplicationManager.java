package appmanager;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.BrowserType;

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
/*      ProfilesIni profile = new ProfilesIni();
        FirefoxProfile myprofile = profile.getProfile("profileToolsQA");*/
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
