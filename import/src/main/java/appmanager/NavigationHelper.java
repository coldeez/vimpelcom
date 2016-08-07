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
        wd.findElement(By.name("uploadFile")).sendKeys(path + "\\contracts\\" + filename + ".csv");
    }

    public void parseDocs() throws InterruptedException {
        int n = 0;
        while (!isElementPresent(By.name("parsing")) && n < 100){
            n++;
            Thread.sleep(1000);
        }
        click(By.name("parsing"));

    }

    public void checkContractStatus(Users user) throws InterruptedException {
        int n = 0;
        while (!isElementPresent(By.name("upload")) && n < 100) {
            Thread.sleep(1000);
        }
        if (!isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withContractStatus("OK");
        } else if (isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withContractStatus("FAIL");
        }

    }

    public void checkInvoiceStatus(Users user) throws InterruptedException {
        int n = 0;
        while (!isElementPresent(By.name("upload")) && n < 100) {
            Thread.sleep(1000);
        }
        if (!isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withInvoiceStatus("OK");
        } else if (isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withInvoiceStatus("FAIL");
        }
    }


    public void confirmUpload() {
        click(By.name("upload"));
    }
    public void saveFile(List<Users> users, File file) throws IOException {
        Writer writer = new FileWriter(file);
        for (Users user : users) {
            writer.write(String.format("%s;%s;%s;%s;%s\n",
                    user.getUser(),user.getLogin(), user.getPassword(), user.getContractStatus(), user.getInvoiceStatus()));
        }
        writer.close();
    }
    public void quit() {
        click(By.id("exitLink"));
    }

    public void chooseFileInvoice(String filename, String path) {
        wd.findElement(By.name("uploadFile")).sendKeys(path + "\\invoices\\" + filename + ".csv");

    }

    public Boolean waitForElement(By locator) {
        try {
            (new WebDriverWait(wd, 1)).until(new ExpectedCondition<Boolean>() {
                public Boolean apply(WebDriver d) {
                    try {
                        return wd.findElement(locator).isDisplayed();
                    }
                    catch (NoSuchElementException ex) {
                        return false;
                    }
                }
            });
        }
        catch (NoSuchElementException ignored) {
            return false;
        }
        return true;
    }

    public void parseFinish() throws InterruptedException {
        int n = 0;
        while (!isElementPresent(By.className("sortable")) && n < 100 && !isElementPresent(By.xpath("//b[contains(text(),'Данные успешно обработаны.')]"))) {
            if (isElementPresent(By.xpath("//input[@onclick='returnToStart()']"))) {
                click(By.xpath("//input[@onclick='returnToStart()']"));
            }
            n++;
            Thread.sleep(2000);
        }
    }
}
