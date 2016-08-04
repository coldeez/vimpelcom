package appmanager;

import org.openqa.selenium.Alert;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by kosty on 04.08.2016.
 */
public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }


    public void contractsPage() {
        wd.get("https://" + login + ":" + password + "@agent.beeline.ru/operator/showUploadContractsForm.do");
    }


    public void authorize() {
        WebDriverWait wait = new WebDriverWait(wd, 2);
        wait.until(ExpectedConditions.alertIsPresent());
        Alert alert = wd.switchTo().alert();
        alert.accept();


    }
}
