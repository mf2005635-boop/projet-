package service;

import dao.SecretaireDAO;
import model.Secretaire;

import java.sql.SQLException;

public class SecretaireService {

    private final SecretaireDAO secretaireDAO = new SecretaireDAO();

    public Secretaire seConnecter(String login, String password) throws SQLException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        return secretaireDAO.authenticate(login.trim(), password);
    }
}
