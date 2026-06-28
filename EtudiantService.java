package service;

import dao.EtudiantDAO;
import model.Etudiant;

import java.sql.SQLException;
import java.util.List;

public class EtudiantService {

    private final EtudiantDAO etudiantDAO = new EtudiantDAO();

    public void ajouterEtudiant(Etudiant etudiant) throws SQLException {
        if (etudiant == null) {
            throw new IllegalArgumentException("L'etudiant ne peut pas etre null.");
        }
        if (etudiant.getNom() == null || etudiant.getNom().isBlank()) {
            throw new IllegalArgumentException("Le nom est obligatoire.");
        }
        if (etudiant.getPrenom() == null || etudiant.getPrenom().isBlank()) {
            throw new IllegalArgumentException("Le prenom est obligatoire.");
        }
        if (etudiant.getFiliere() == null || etudiant.getFiliere().isBlank()) {
            throw new IllegalArgumentException("La filiere est obligatoire.");
        }
        if (etudiant.getNiveau() == null || etudiant.getNiveau().isBlank()) {
            throw new IllegalArgumentException("Le niveau est obligatoire.");
        }
        if (etudiantDAO.idExists(etudiant.getId())) {
            throw new IllegalStateException("Cet ID existe deja.");
        }

        etudiantDAO.insert(etudiant);
    }

    public Etudiant rechercherParId(int id) throws SQLException {
        return etudiantDAO.findById(id);
    }

    public List<Etudiant> listerTous() throws SQLException {
        return etudiantDAO.findAll();
    }

    public boolean modifierEtudiant(Etudiant etudiant) throws SQLException {
        if (etudiant == null || !etudiantDAO.idExists(etudiant.getId())) {
            return false;
        }
        return etudiantDAO.update(etudiant);
    }

    public boolean supprimerEtudiant(int id) throws SQLException {
        return etudiantDAO.delete(id);
    }
}
