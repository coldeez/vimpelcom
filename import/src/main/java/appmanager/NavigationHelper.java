package appmanager;

import model.Users;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

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
        wd.get("https://" + login + ":" + password + "@agent.beeline.ru/operator/showUploadContractsForm.do");
    }
    public void invoicesPage() {
        wd.get("https://agent.beeline.ru/operator/showUploadClientInvoices.do");
    }

    public void chooseFile(String filename) {
        wd.findElement(By.name("uploadFile")).sendKeys("C:\\contracts\\" + filename + ".txt");
    }

    public void successfulUpload(Users user) {
        user.withStatus("OK");
    }
    public void failUpload(Users user) {
        user.withStatus("FAIL");
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

    public void chooseFileInvoice(String filename) {
        wd.findElement(By.name("uploadFile")).sendKeys("C:\\invoices\\" + filename + ".txt");
    }
}
