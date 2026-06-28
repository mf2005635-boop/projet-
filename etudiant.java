package model;

public class Etudiant extends Utilisateur {

    private String filiere;
    private String niveau;

    public Etudiant() {
    }

    public Etudiant(int id, String nom, String prenom, String filiere, String niveau) {
        super(id, nom, prenom);
        this.filiere = filiere;
        this.niveau = niveau;
    }

    public String getFiliere() {
        return filiere;
    }

    public void setFiliere(String filiere) {
        this.filiere = filiere;
    }

    public String getNiveau() {
        return niveau;
    }

    public void setNiveau(String niveau) {
        this.niveau = niveau;
    }

    public void afficher() {
        System.out.println("ID: " + id);
        System.out.println("Nom: " + nom);
        System.out.println("Prenom: " + prenom);
        System.out.println("Filiere: " + filiere);
        System.out.println("Niveau: " + niveau);
        System.out.println("-------------------");
    }

    @Override
    public String toString() {
        return "Etudiant - " + super.toString() + " | Filiere: " + filiere + " | Niveau: " + niveau;
    }
}

