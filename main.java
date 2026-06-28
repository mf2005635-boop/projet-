package app;

import dao.ConnectionDB;
import model.Admin;
import model.Etudiant;
import model.Secretaire;
import model.Utilisateur;
import service.AdminService;
import service.EtudiantService;
import service.SecretaireService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static final EtudiantService etudiantService = new EtudiantService();
    private static final AdminService adminService = new AdminService();
    private static final SecretaireService secretaireService = new SecretaireService();

    public static void main(String[] args) {
        System.out.println("=== Gestion des Etudiants ===");

        try {
            Utilisateur utilisateurConnecte = authentifier();
            if (utilisateurConnecte == null) {
                System.out.println("Connexion echouee.");
                return;
            }

            System.out.println("Bienvenue " + utilisateurConnecte.getPrenom() + " " + utilisateurConnecte.getNom() + " !");
            afficherMenuPrincipal();
        } catch (SQLException e) {
            System.err.println("Erreur base de donnees : " + e.getMessage());
            System.err.println("Verifiez MySQL, le script SQL et les parametres dans ConnectionDB.java.");
        } finally {
            ConnectionDB.closeConnection();
            scanner.close();
        }
    }

    private static Utilisateur authentifier() throws SQLException {
        System.out.println("\nConnexion");
        System.out.println("1. Admin");
        System.out.println("2. Secretaire");
        System.out.print("Type de compte : ");

        int type = lireEntier();
        System.out.print("Login : ");
        String login = scanner.nextLine().trim();
        System.out.print("Mot de passe : ");
        String password = scanner.nextLine().trim();

        if (type == 1) {
            Admin admin = adminService.seConnecter(login, password);
            return admin;
        }
        if (type == 2) {
            Secretaire secretaire = secretaireService.seConnecter(login, password);
            return secretaire;
        }

        System.out.println("Type de compte invalide.");
        return null;
    }

    private static void afficherMenuPrincipal() throws SQLException {
        int choix;
        do {
            System.out.println("\n1. Ajouter un etudiant");
            System.out.println("2. Rechercher un etudiant");
            System.out.println("3. Afficher la liste");
            System.out.println("4. Modifier un etudiant");
            System.out.println("5. Supprimer un etudiant");
            System.out.println("6. Quitter");
            System.out.print("Choix : ");

            choix = lireEntier();

            switch (choix) {
                case 1 -> ajouterEtudiant();
                case 2 -> rechercherEtudiant();
                case 3 -> afficherListe();
                case 4 -> modifierEtudiant();
                case 5 -> supprimerEtudiant();
                case 6 -> System.out.println("Au revoir !");
                default -> System.out.println("Choix invalide.");
            }
        } while (choix != 6);
    }

    private static void ajouterEtudiant() {
        try {
            System.out.print("ID : ");
            int id = lireEntier();

            Etudiant etudiant = new Etudiant();
            etudiant.setId(id);

            System.out.print("Nom : ");
            etudiant.setNom(scanner.nextLine().trim());
            System.out.print("Prenom : ");
            etudiant.setPrenom(scanner.nextLine().trim());
            System.out.print("Filiere : ");
            etudiant.setFiliere(scanner.nextLine().trim());
            System.out.print("Niveau : ");
            etudiant.setNiveau(scanner.nextLine().trim());

            etudiantService.ajouterEtudiant(etudiant);
            System.out.println("Etudiant enregistre avec succes.");
        } catch (IllegalArgumentException | IllegalStateException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    private static void rechercherEtudiant() {
        try {
            System.out.print("ID a rechercher : ");
            int id = lireEntier();

            Etudiant etudiant = etudiantService.rechercherParId(id);
            if (etudiant != null) {
                etudiant.afficher();
            } else {
                System.out.println("Etudiant introuvable.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche : " + e.getMessage());
        }
    }

    private static void afficherListe() {
        try {
            List<Etudiant> etudiants = etudiantService.listerTous();
            if (etudiants.isEmpty()) {
                System.out.println("Aucun etudiant.");
                return;
            }

            for (Etudiant etudiant : etudiants) {
                etudiant.afficher();
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
    }

    private static void modifierEtudiant() {
        try {
            System.out.print("ID de l'etudiant a modifier : ");
            int id = lireEntier();

            Etudiant etudiant = etudiantService.rechercherParId(id);
            if (etudiant == null) {
                System.out.println("Etudiant introuvable.");
                return;
            }

            System.out.print("Nouveau nom (" + etudiant.getNom() + ") : ");
            String nom = scanner.nextLine().trim();
            if (!nom.isBlank()) {
                etudiant.setNom(nom);
            }

            System.out.print("Nouveau prenom (" + etudiant.getPrenom() + ") : ");
            String prenom = scanner.nextLine().trim();
            if (!prenom.isBlank()) {
                etudiant.setPrenom(prenom);
            }

            System.out.print("Nouvelle filiere (" + etudiant.getFiliere() + ") : ");
            String filiere = scanner.nextLine().trim();
            if (!filiere.isBlank()) {
                etudiant.setFiliere(filiere);
            }

            System.out.print("Nouveau niveau (" + etudiant.getNiveau() + ") : ");
            String niveau = scanner.nextLine().trim();
            if (!niveau.isBlank()) {
                etudiant.setNiveau(niveau);
            }

            if (etudiantService.modifierEtudiant(etudiant)) {
                System.out.println("Etudiant modifie avec succes.");
            } else {
                System.out.println("Echec de la modification.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    private static void supprimerEtudiant() {
        try {
            System.out.print("ID de l'etudiant a supprimer : ");
            int id = lireEntier();

            if (etudiantService.supprimerEtudiant(id)) {
                System.out.println("Etudiant supprime avec succes.");
            } else {
                System.out.println("Etudiant introuvable.");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    private static int lireEntier() {
        while (!scanner.hasNextInt()) {
            scanner.next();
            System.out.print("Veuillez entrer un nombre entier : ");
        }
        int value = scanner.nextInt();
        scanner.nextLine();
        return value;
    }
}

