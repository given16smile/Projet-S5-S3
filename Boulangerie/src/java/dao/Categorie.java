package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Categorie {
    private String id;
    private String nom;

    public Categorie() {}

    public Categorie(String id, String nom) {
        this.id = id;
        this.nom = nom;
    }

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

    public void insert() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO categorie(nom) VALUES (?)"
            );
            statement.setString(1, nom);
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
            throw new DaoException("Erreur lors de l'insertion de la catégorie", e);
        } finally {
            closeResources(statement, connection);
        }
    }

    public void find() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement(
                "SELECT * FROM categorie WHERE id = ?"
            );
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                nom = resultSet.getString("nom");
            } else {
                throw new DaoException("Catégorie avec l'id " + id + " non trouvée.");
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la recherche de la catégorie", e);
        } finally {
            closeResources(resultSet, statement, connection);
        }
    }

    public void update() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE categorie SET nom = ? WHERE id = ?"
            );
            statement.setString(1, nom);
            statement.setString(2, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Aucune catégorie trouvée pour l'ID " + id + " à mettre à jour.");
            }
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception rollbackException) {
                    throw new DaoException("Erreur lors du rollback de la transaction", rollbackException);
                }
            }
            throw new DaoException("Erreur lors de la mise à jour de la catégorie", e);
        } finally {
            closeResources(statement, connection);
        }
    }

    public void delete() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM categorie WHERE id = ?"
            );
            statement.setString(1, id);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                throw new DaoException("Aucune catégorie trouvée pour l'ID " + id + " à supprimer.");
            }
            connection.commit();
        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback();
                } catch (Exception rollbackException) {
                    throw new DaoException("Erreur lors du rollback de la transaction", rollbackException);
                }
            }
            throw new DaoException("Erreur lors de la suppression de la catégorie", e);
        } finally {
            closeResources(statement, connection);
        }
    }

    public static ArrayList<Categorie> getAll() throws DaoException {
        ArrayList<Categorie> categories = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM categorie");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                categories.add(new Categorie(
                    resultSet.getString("id"),
                    resultSet.getString("nom")
                ));
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération des catégories", e);
        } finally {
            closeResources(resultSet, statement, connection);
        }

        return categories;
    }

    private static void closeResources(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
            }
        }
    }
}
