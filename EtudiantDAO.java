package dao;

import model.Etudiant;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EtudiantDAO {

    public boolean idExists(int id) throws SQLException {
        String sql = "SELECT id FROM etudiants WHERE id = ?";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    public void insert(Etudiant etudiant) throws SQLException {
        String sqlUtilisateur = "INSERT INTO utilisateurs (id, nom, prenom, type) VALUES (?, ?, ?, 'ETUDIANT')";
        String sqlEtudiant = "INSERT INTO etudiants (id, filiere, niveau) VALUES (?, ?, ?)";

        Connection conn = ConnectionDB.getConnection();
        conn.setAutoCommit(false);

        try (PreparedStatement psUser = conn.prepareStatement(sqlUtilisateur);
             PreparedStatement psEtudiant = conn.prepareStatement(sqlEtudiant)) {

            psUser.setInt(1, etudiant.getId());
            psUser.setString(2, etudiant.getNom());
            psUser.setString(3, etudiant.getPrenom());
            psUser.executeUpdate();

            psEtudiant.setInt(1, etudiant.getId());
            psEtudiant.setString(2, etudiant.getFiliere());
            psEtudiant.setString(3, etudiant.getNiveau());
            psEtudiant.executeUpdate();

            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public Etudiant findById(int id) throws SQLException {
        String sql = """
                SELECT u.id, u.nom, u.prenom, e.filiere, e.niveau
                FROM etudiants e
                JOIN utilisateurs u ON u.id = e.id
                WHERE e.id = ?
                """;

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEtudiant(rs);
                }
            }
        }
        return null;
    }

    public List<Etudiant> findAll() throws SQLException {
        String sql = """
                SELECT u.id, u.nom, u.prenom, e.filiere, e.niveau
                FROM etudiants e
                JOIN utilisateurs u ON u.id = e.id
                ORDER BY u.id
                """;

        List<Etudiant> etudiants = new ArrayList<>();

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                etudiants.add(mapEtudiant(rs));
            }
        }
        return etudiants;
    }

    public boolean update(Etudiant etudiant) throws SQLException {
        String sqlUtilisateur = "UPDATE utilisateurs SET nom = ?, prenom = ? WHERE id = ?";
        String sqlEtudiant = "UPDATE etudiants SET filiere = ?, niveau = ? WHERE id = ?";

        Connection conn = ConnectionDB.getConnection();
        conn.setAutoCommit(false);

        try (PreparedStatement psUser = conn.prepareStatement(sqlUtilisateur);
             PreparedStatement psEtudiant = conn.prepareStatement(sqlEtudiant)) {

            psUser.setString(1, etudiant.getNom());
            psUser.setString(2, etudiant.getPrenom());
            psUser.setInt(3, etudiant.getId());
            int updatedUser = psUser.executeUpdate();

            psEtudiant.setString(1, etudiant.getFiliere());
            psEtudiant.setString(2, etudiant.getNiveau());
            psEtudiant.setInt(3, etudiant.getId());
            int updatedEtudiant = psEtudiant.executeUpdate();

            conn.commit();
            return updatedUser > 0 && updatedEtudiant > 0;
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        } finally {
            conn.setAutoCommit(true);
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM utilisateurs WHERE id = ? AND type = 'ETUDIANT'";

        try (Connection conn = ConnectionDB.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        }
    }

    private Etudiant mapEtudiant(ResultSet rs) throws SQLException {
        return new Etudiant(
                rs.getInt("id"),
                rs.getString("nom"),
                rs.getString("prenom"),
                rs.getString("filiere"),
                rs.getString("niveau")
        );
    }
}

