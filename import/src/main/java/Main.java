import appmanager.ApplicationManager;
import org.testng.ITestContext;

import java.io.IOException;

/**
 * Created by kosty on 04.08.2016.
 */
public class Main {

    protected static final ApplicationManager app = ApplicationManager.getInstance();

    public static void main(String[] args) throws IOException {
        app.init();
        app.goTo().contractsPage();
        app.goTo().authorize();
        app.stop();


    }


    public void setUp(ITestContext context) throws Exception {

    }

    public void tearDown() {

            }

/*        https://agent.beeline.ru/operator/showUploadClientInvoices.do*/

    }

