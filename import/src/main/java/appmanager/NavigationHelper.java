package appmanager;

import model.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Created by kosty on 04.08.2016.
 */
public class NavigationHelper extends HelperBase {

    public NavigationHelper(WebDriver wd) {
        super(wd);
    }


    public void contractsPage(String login, String password) {
        wd.get("https://" + login + ":" + password + "@agentdemo.beeline.ru/operator/showUploadContractsForm.do");
    }
    public void invoicesPage() {
        wd.get("https://agentdemo.beeline.ru/operator/showUploadClientInvoices.do");
    }

    public void chooseFileContract(String filename, String path) {
        wd.findElement(By.name("uploadFile")).sendKeys(path + "\\contracts\\" + filename + ".txt");
    }

    public void parseDocs() {
        click(By.name("parsing"));
    }

    public void checkStatus(Users user) {
        if (isElementPresent(By.xpath("//th[contains(text(), '...Протокол ошибок загрузки данных')]"))) {
            user.withStatus("OK");
        } else if (!isElementPresent(By.xpath("//th[contains(text(), '...Протокол ошибок загрузки данных')]"))) {
            user.withStatus("FAIL");
        }
    }


    public void confirmUpload() {
        click(By.name("upload"));
    }
    public void saveFile(List<Users> users, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (Users user : users) {
            writer.write(String.format("%s;%s;%s;%s\n",
                    user.getUser(),user.getLogin(), user.getPassword(), user.getStatus()));
        }
        writer.close();
    }
    public void quit() {
        click(By.id("exitLink"));
    }

    public void chooseFileInvoice(String filename, String path) {
        wd.findElement(By.name("uploadFile")).sendKeys(path + "\\invoices\\" + filename + ".txt");

    }

    public Boolean waitForElement(By locator) {
        try {
            (new WebDriverWait(wd, 1000)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    try {
                        return wd.findElement(locator).isDisplayed();
                    }
                    catch (NoSuchElementException e) {
                        return false;
                    }
                }
            });
        }
        catch (NoSuchElementException e) {
            return false;
        }
        return true;
    }

    public void parseFinish() throws InterruptedException {
        int n = 0;
        while
                (waitForElement(By.xpath("input[@onclick='returnToStart()']"))
                && waitForElement(By.className("sortable"))
                && n > 22000) {
            click(By.xpath("input[@onclick='returnToStart()']"));
            Thread.sleep(2000);
            n++;
        }
        waitForElement(By.className("sortable"));
    }
}
