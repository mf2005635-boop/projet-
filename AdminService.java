package service;

import dao.AdminDAO;
import model.Admin;

import java.sql.SQLException;

public class AdminService {

    private final AdminDAO adminDAO = new AdminDAO();

    public Admin seConnecter(String login, String password) throws SQLException {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            return null;
        }
        return adminDAO.authenticate(login.trim(), password);
    }
}
