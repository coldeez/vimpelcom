package appmanager;

import model.Users;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Properties;


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

    public void InvoicesHistoryScreen(String filename, String path) throws IOException {
        wd.get("https://agentdemo.beeline.ru/operator/showInvoicesUpload.do");
        File scrFile = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File(path + "\\screenshots\\history\\history_" + filename + ".png"));

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

    public void checkContractStatus(Users user, String filename, String path) throws InterruptedException, IOException {
        int n = 0;
        while (!isElementPresent(By.name("upload")) && n < 100) {
            Thread.sleep(1000);
        }
        if (!isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withContractStatus("OK");
        } else if (isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withContractStatus("FAIL");
            File scrFile = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(path + "\\screenshots\\contract_" + filename + ".png"));
        }

    }

    public void checkInvoiceStatus(Users user, String filename, String path) throws InterruptedException, IOException {
        int n = 0;
        while (!isElementPresent(By.name("upload")) && n < 100) {
            Thread.sleep(1000);
        }
        if (!isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withInvoiceStatus("OK");
        } else if (isElementPresent(By.xpath("//th[contains(text(), 'Протокол ошибок загрузки данных')]"))) {
            user.withInvoiceStatus("FAIL");
            File scrFile = ((TakesScreenshot)wd).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(scrFile, new File(path + "\\screenshots\\invoice_" + filename + ".png"));
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
        while (!isElementPresent(By.className("sortable")) && n < 18000 && !isElementPresent(By.xpath("//b[contains(text(),'Данные успешно обработаны.')]"))) {
            if (isElementPresent(By.xpath("//input[@onclick='returnToStart()']"))) {
                click(By.xpath("//input[@onclick='returnToStart()']"));
            }
            n++;
            Thread.sleep(2000);
        }
    }

    public void SendEmail(String messageText, String path, String doc, String filename) {

        String to = "k.balashov@mangotele.com";         // sender email
        String from = "vimpelcom_autoimport@mangotele.com";       // receiver email
        String host = "mx1.mangotele.com";            // mail server host

        Properties properties = System.getProperties();
        properties.setProperty("mail.smtp.host", host);



        Session session = Session.getDefaultInstance(properties); // default session

        try {
            MimeMessage message = new MimeMessage(session); // email message
            MimeMultipart multipart = new MimeMultipart();
            message.setFrom(new InternetAddress(from)); // setting header fields

            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject("Вымпелком автоимпорт"); // subject line

            // actual mail body
            message.setText(messageText);
            BodyPart messageBodyPart = new MimeBodyPart();
            messageBodyPart.setContent(messageText, "text/html");
            multipart.addBodyPart(messageBodyPart);
            messageBodyPart = new MimeBodyPart();
            DataSource fds = new FileDataSource(
                    path + "\\screenshots\\" + doc + filename + ".png");

            messageBodyPart.setDataHandler(new DataHandler(fds));
            messageBodyPart.setHeader("Content-ID", "<image>");
            message.setContent(multipart);
            // Send message
            Transport.send(message); System.out.println("Email Sent successfully....");
        } catch (MessagingException mex){ mex.printStackTrace(); }

    }
}
