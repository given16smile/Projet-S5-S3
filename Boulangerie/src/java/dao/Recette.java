package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;

public class Recette {
    private String id;
    private String nom;
    private Time tempsPreparation;  // Utilisation de java.sql.Time pour le temps
    private String idProduit;
    private String idIngredient;

    // Constructeur sans paramètre
    public Recette() {}

    // Constructeur avec paramètres
    public Recette(String id, String nom, Time tempsPreparation, String idProduit, String idIngredient) {
        setId(id);setNom(nom);setTempsPreparation(tempsPreparation);
        setIdProduit(idProduit); setIdIngredient(idIngredient);
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

    public Time getTempsPreparation() {
        return tempsPreparation;
    }

    public void setTempsPreparation(Time tempsPreparation) {
        this.tempsPreparation = tempsPreparation;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    // Méthode pour insérer une recette
    public void insert() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "INSERT INTO recette (id, nom, tempsPreparation, id_produit, id_ingredient) VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, id);
            statement.setString(2, nom);
            statement.setTime(3, tempsPreparation);  // Utilisation de setTime pour Time
            statement.setString(4, idProduit);
            statement.setString(5, idIngredient);

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

    // Méthode pour mettre à jour une recette
    public void update() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "UPDATE recette SET nom = ?, tempsPreparation = ?, id_produit = ?, id_ingredient = ? WHERE id = ?"
            );
            statement.setString(1, nom);
            statement.setTime(2, tempsPreparation);  // Utilisation de setTime pour Time
            statement.setString(3, idProduit);
            statement.setString(4, idIngredient);
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

    // Méthode pour supprimer une recette
    public void delete() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "DELETE FROM recette WHERE id = ?"
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

    // Méthode pour trouver une recette par son ID
    public void find() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement(
                "SELECT * FROM recette WHERE id = ?"
            );
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nom = resultSet.getString("nom");
                tempsPreparation = resultSet.getTime("tempsPreparation");  // Récupération de Time
                idProduit = resultSet.getString("id_produit");
                idIngredient = resultSet.getString("id_ingredient");
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour obtenir toutes les recettes
    public static ArrayList<Recette> getAll() throws Exception {
        ArrayList<Recette> recettes = new ArrayList<Recette>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM recette");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                Time tempsPreparation = resultSet.getTime("tempsPreparation");  // Récupération de Time
                String idProduit = resultSet.getString("id_produit");
                String idIngredient = resultSet.getString("id_ingredient");

                recettes.add(new Recette(id, nom, tempsPreparation, idProduit, idIngredient));
            }
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return recettes;
    }
}
