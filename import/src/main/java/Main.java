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


        List<Users> users = getInfo();
        for ( Users user :  users ) {
            app.init();
            app.goTo().contractsPage(user.getLogin(), user.getPassword());
/*            app.navigationHelper.chooseFileContract(user.getUser());*/
/*            app.navigationHelper.confirmUpload();*/
            app.goTo().invoicesPage();
            app.navigationHelper.chooseFileInvoice(user.getUser());
/*            app.navigationHelper.confirmUpload();*/

         }
        app.navigationHelper.saveFile(users,(new File ("import/src/resources/users.csv")));
        app.stop();





    }

    public static List<Users> getInfo() throws IOException {
        List<Users> list = new ArrayList<Users>();
        BufferedReader reader = new BufferedReader(new FileReader(new File("import/src/resources/users.csv")));
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

/*        https://agent.beeline.ru/operator/showUploadClientInvoices.do*/

    }

