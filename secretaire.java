package model;

public class Secretaire extends Utilisateur {

    private String login;
    private String password;

    public Secretaire() {
    }

    public Secretaire(int id, String nom, String prenom, String login, String password) {
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
        return "Secretaire - " + super.toString() + " | Login: " + login;
    }
}

