package model;

public class Admin extends Utilisateur {

    private String login;
    private String password;

    public Admin() {
    }

    public Admin(int id, String nom, String prenom, String login, String password) {
        super(id, nom, prenom);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean seConnecter(String login, String password) {
        return this.login.equals(login) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Admin - " + super.toString() + " | Login: " + login;
    }
}
