package model;

/**
 * Created by kosty on 04.08.2016.
 */
public class Users {
    String user;
    String login;
    String password;
    String contractStatus;
    String invoiceStatus;

    public String getUser() {
        return user;
    }

    public Users withUsername(String user) {
        this.user = user;
        return this;
    }

    public String getLogin() {
        return login;
    }

    public Users withLogin(String login) {
        this.login = login;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public Users withPassword(String password) {
        this.password = password;
        return this;
    }

    public String getContractStatus() {
        return contractStatus;
    }

    public Users withContractStatus(String status) {
        this.contractStatus = status;
        return this;
    }

    public String getInvoiceStatus() {
        return invoiceStatus;
    }

    public Users withInvoiceStatus(String status) {
        this.invoiceStatus = status;
        return this;
    }

}
