package dao;

import model.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDAO {

    public Admin findByLogin(String login) throws SQLException {
        String sql = """
                SELECT u.id, u.nom, u.prenom, a.login, a.password
                FROM admins a
                JOIN utilisateurs u ON u.id = a.id
                WHERE a.login = ?
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getInt("id"),
                            rs.getString("nom"),
                            rs.getString("prenom"),
                            rs.getString("login"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null;
    }

    public Admin authenticate(String login, String password) throws SQLException {
        Admin admin = findByLogin(login);
        if (admin != null && admin.seConnecter(login, password)) {
            return admin;
        }
        return null;
    }
}
