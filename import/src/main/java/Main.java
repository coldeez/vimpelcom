import appmanager.ApplicationManager;
import model.Users;
import org.testng.ITestContext;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kosty on 04.08.2016.
 */

public class Main {

    protected static final ApplicationManager app = ApplicationManager.getInstance();

    public static void main(String[] args) throws Exception {
        String path = System.getProperty("Basepath", "C:/test1/test1.txt" );
        String usersFile = System.getProperty("Usersfile", "C:/test1/test2.txt");
        List<Users> users = getInfo(usersFile);
        for ( Users user :  users ) {
            app.init();
            app.goTo().contractsPage(user.getLogin(), user.getPassword());
            Thread.sleep(1000);
            app.navigationHelper.chooseFileContract(user.getUser(), path);
            Thread.sleep(1000);
            app.navigationHelper.confirmUpload();
            Thread.sleep(1000);
            app.navigationHelper.parseDocs();
            Thread.sleep(1000);
            app.navigationHelper.parseFinish();
            Thread.sleep(1000);
            app.navigationHelper.checkStatus(user);
            Thread.sleep(1000);
            app.goTo().invoicesPage();
            Thread.sleep(1000);
            app.navigationHelper.chooseFileInvoice(user.getUser(), path);
            Thread.sleep(1000);
            app.navigationHelper.confirmUpload();
            Thread.sleep(1000);
/*          app.navigationHelper.checkStatus(user);*/
            Thread.sleep(1000);
            app.stop();
            Thread.sleep(1000);
         }
        app.navigationHelper.saveFile(users,(new File (usersFile)));
    }

    public static List<Users> getInfo(String usersFile) throws IOException {
        List<Users> list = new ArrayList<Users>();
        BufferedReader reader = new BufferedReader(new FileReader(new File(usersFile)));
        String line = reader.readLine();
        while (line != null) {
            String[] split = line.split(";");
            list.add(new Users().withUsername(split[0]).withLogin(split[1]).withPassword(split[2]));
            line = reader.readLine();
        }
        return list;
    }


    public void setUp(ITestContext context) throws Exception {

    }

    public void tearDown() {

            }
    }

