package dao;

import model.Secretaire;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SecretaireDAO {

    public Secretaire findByLogin(String login) throws SQLException {
        String sql = """
                SELECT u.id, u.nom, u.prenom, s.login, s.password
                FROM secretaires s
                JOIN utilisateurs u ON u.id = s.id
                WHERE s.login = ?
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, login);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Secretaire(
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

    public Secretaire authenticate(String login, String password) throws SQLException {
        Secretaire secretaire = findByLogin(login);
        if (secretaire != null && secretaire.seConnecter(login, password)) {
            return secretaire;
        }
        return null;
    }
}

