package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

public class Produit {
    private String id;
    private String nom;
    private String description;
    private Time dureeConservation;  // Utilisation de java.sql.Time
    private String idCategorie;

    // Constructeur sans paramètre
    public Produit() {}

    // Constructeur avec paramètres
    public Produit(String id, String nom, String description, Time dureeConservation, String idCategorie) {
        setId(id);setNom(nom);setDescription(description);
        setDureeConservation(dureeConservation);setIdCategorie(idCategorie);
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Time getDureeConservation() {
        return dureeConservation;
    }

    public void setDureeConservation(Time dureeConservation) {
        this.dureeConservation = dureeConservation;
    }

    public String getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(String idCategorie) {
        this.idCategorie = idCategorie;
    }

    // Méthode pour insérer un produit
    public void insert() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "INSERT INTO produit (id, nom, description, dureeConservation, id_categorie) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, id);
            statement.setString(2, nom);
            statement.setString(3, description);
            statement.setTime(4, dureeConservation);  // Utilisation de setTime pour Time
            statement.setString(5, idCategorie);

            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour mettre à jour un produit
    public void update() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "UPDATE produit SET nom = ?, description = ?, dureeConservation = ?, id_categorie = ? WHERE id = ?"
            );
            statement.setString(1, nom);
            statement.setString(2, description);
            statement.setTime(3, dureeConservation);  // Utilisation de setTime pour Time
            statement.setString(4, idCategorie);
            statement.setString(5, id);

            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour supprimer un produit
    public void delete() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "DELETE FROM produit WHERE id = ?"
            );
            statement.setString(1, id);

            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour trouver un produit par son ID
    public void find() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement(
                "SELECT * FROM produit WHERE id = ?"
            );
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nom = resultSet.getString("nom");
                description = resultSet.getString("description");
                dureeConservation = resultSet.getTime("dureeConservation");  // Récupération de Time
                idCategorie = resultSet.getString("id_categorie");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour obtenir tous les produits
    public static ArrayList<Produit> getAll() throws Exception {
        ArrayList<Produit> produits = new ArrayList<Produit>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM produit");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                String description = resultSet.getString("description");
                Time dureeConservation = resultSet.getTime("dureeConservation");  // Récupération de Time
                String idCategorie = resultSet.getString("id_categorie");

                produits.add(new Produit(id, nom, description, dureeConservation, idCategorie));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return produits;
    }
}
