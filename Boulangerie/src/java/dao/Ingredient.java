package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Ingredient {
    private String id;
    private String nom;
    private int seuilAlerte;
    private String id_unite;

    // Constructeurs
    public Ingredient() {}

    public Ingredient(String id, String nom, int seuilAlerte, String id_unite) throws Exception {
        setId(id);
        setNom(nom);
        setSeuilAlerte(seuilAlerte);
        setId_unite(id_unite);
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

    public int getSeuilAlerte() {
        return seuilAlerte;
    }

    public void setSeuilAlerte(int seuilAlerte) throws Exception {
        if (seuilAlerte < 0) {
            throw new Exception("Le seuil d'alerte doit être positif.");
        }
        this.seuilAlerte = seuilAlerte;
    }

    public String getId_unite() {
        return id_unite;
    }

    public void setId_unite(String id_unite) {
        this.id_unite = id_unite;
    }

    // Méthode pour insérer un ingrédient
    public void insert() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO ingredient (nom, seuil_alerte, id_unite) VALUES (?, ?, ?)"
            );
            statement.setString(1, nom);
            statement.setInt(2, seuilAlerte);
            statement.setString(3, id_unite);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception rollbackException) {
                    throw new DaoException("Erreur lors du rollback de la transaction", rollbackException);
                }
            }
            throw new DaoException("Erreur lors de l'insertion de l'ingrédient", e);
        } finally {
            close(connection, statement);
        }
    }

    // Méthode pour récupérer un ingrédient par son ID
    public void find() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement(
                "SELECT * FROM ingredient WHERE id = ?"
            );
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nom = resultSet.getString("nom");
                seuilAlerte = resultSet.getInt("seuil_alerte");
                id_unite = resultSet.getString("id_unite");
            } else {
                throw new DaoException("Ingrédient introuvable avec l'ID " + id);
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la recherche de l'ingrédient", e);
        } finally {
            close(connection, statement, resultSet);
        }
    }

    // Méthode pour mettre à jour un ingrédient
    public void update() throws DaoException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE ingredient SET nom = ?, seuil_alerte = ?, id_unite = ? WHERE id = ?"
            );
            statement.setString(1, nom);
            statement.setInt(2, seuilAlerte);
            statement.setString(3, id_unite);
            statement.setString(4, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception rollbackException) {
                    throw new DaoException("Erreur lors du rollback de la transaction", rollbackException);
                }
            }
            throw new DaoException("Erreur lors de la mise à jour de l'ingrédient", e);
        } finally {
            close(connection, statement);
        }
    }

    // Méthode pour supprimer un ingrédient
    public void delete() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM ingredient WHERE id = ?"
            );
            statement.setString(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception rollbackException) {
                    throw new DaoException("Erreur lors du rollback de la transaction", rollbackException);
                }
            }
            throw new DaoException("Erreur lors de la suppression de l'ingrédient", e);
        } finally {
            close(connection, statement);
        }
    }

    // Méthode pour récupérer tous les ingrédients
    public static ArrayList<Ingredient> getAll() throws Exception {
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM ingredient");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String nom = resultSet.getString("nom");
                int seuilAlerte = resultSet.getInt("seuil_alerte");
                String id_unite = resultSet.getString("id_unite");

                ingredients.add(new Ingredient(id, nom, seuilAlerte, id_unite));
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération des ingrédients", e);
        } finally {
            close(connection, statement, resultSet);
        }

        return ingredients;
    }

    private static void close(AutoCloseable... resources) throws Exception {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                throw e;
            }
        }
    }
}
