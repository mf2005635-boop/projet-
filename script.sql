-- Script de création de la base de données
-- Exécuter : mysql -u root -p < src/database/script.sql

CREATE DATABASE IF NOT EXISTS gestion_etudiants
    CHARACTER SET utf8mb4
    COLLATE utf8mb4_unicode_ci;

USE gestion_etudiants;

-- Table commune pour tous les utilisateurs
CREATE TABLE IF NOT EXISTS utilisateurs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    prenom VARCHAR(100) NOT NULL,
    type ENUM('ADMIN', 'SECRETAIRE', 'ETUDIANT') NOT NULL
);

-- Administrateurs
CREATE TABLE IF NOT EXISTS admins (
    id INT PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Secrétaires
CREATE TABLE IF NOT EXISTS secretaires (
    id INT PRIMARY KEY,
    login VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Étudiants
CREATE TABLE IF NOT EXISTS etudiants (
    id INT PRIMARY KEY,
    filiere VARCHAR(100) NOT NULL,
    niveau VARCHAR(50) NOT NULL,
    FOREIGN KEY (id) REFERENCES utilisateurs(id) ON DELETE CASCADE
);

-- Données de démonstration
INSERT INTO utilisateurs (id, nom, prenom, type) VALUES
    (1, 'Super', 'Admin', 'ADMIN'),
    (2, 'Yamal', 'Lamine', 'SECRETAIRE'),
    (3, 'Diallo', 'Aminata', 'ETUDIANT'),
    (4, 'Ndiaye', 'Ibrahima', 'ETUDIANT'),
    (5, 'Sarr', 'Fatou', 'ETUDIANT'),
    (6, 'Ba', 'Moussa', 'ETUDIANT'),
    (7, 'Fall', 'Khadija', 'ETUDIANT')
ON DUPLICATE KEY UPDATE nom = VALUES(nom);

INSERT INTO admins (id, login, password) VALUES
    (1, 'admin', 'admin123')
ON DUPLICATE KEY UPDATE login = VALUES(login);

INSERT INTO secretaires (id, login, password) VALUES
    (2, 'admin', '1234')
ON DUPLICATE KEY UPDATE login = VALUES(login);

INSERT INTO etudiants (id, filiere, niveau) VALUES
    (3, 'Informatique', 'L1'),
    (4, 'Gestion', 'L2'),
    (5, 'Marketing', 'L3'),
    (6, 'Droit', 'L1'),
    (7, 'Finance', 'M1')
ON DUPLICATE KEY UPDATE filiere = VALUES(filiere);
