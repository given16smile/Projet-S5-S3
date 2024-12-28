package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Fournisseur {
    private String id;
    private String contact;
    private String nom;

    // Constructors
    public Fournisseur() {}

    public Fournisseur(String id, String contact, String nom) {
        setId(id);
        setContact(contact);
        setNom(nom);
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    // Insert the fournisseur into the database
    public void insert() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO fournisseur(id, contact, nom) VALUES (?, ?, ?)"
            );
            statement.setString(1, id);
            statement.setString(2, contact);
            statement.setString(3, nom);
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
            throw new DaoException("Erreur lors de l'insertion du fournisseur", e);
        } finally {
            close(connection, statement);
        }
    }

    // Find a fournisseur by id
    public void find() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement(
                "SELECT * FROM fournisseur WHERE id = ?"
            );
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                contact = resultSet.getString("contact");
                nom = resultSet.getString("nom");
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la recherche du fournisseur", e);
        } finally {
            close(connection, statement, resultSet);
        }
    }

    // Update an existing fournisseur
    public void update() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE fournisseur SET contact = ?, nom = ? WHERE id = ?"
            );
            statement.setString(1, contact);
            statement.setString(2, nom);
            statement.setString(3, id);
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
            throw new DaoException("Erreur lors de la mise à jour du fournisseur", e);
        } finally {
            close(connection, statement);
        }
    }

    // Delete a fournisseur from the database
    public void delete() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM fournisseur WHERE id = ?"
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
            throw new DaoException("Erreur lors de la suppression du fournisseur", e);
        } finally {
            close(connection, statement);
        }
    }

    // Fetch all fournisseurs from the database
    public static ArrayList<Fournisseur> getAll() throws DaoException {
        ArrayList<Fournisseur> fournisseurs = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM fournisseur");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                String contact = resultSet.getString("contact");
                String nom = resultSet.getString("nom");

                fournisseurs.add(new Fournisseur(id, contact, nom));
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération des fournisseurs", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return fournisseurs;
    }

    // Utility method to close resources
    private static void close(AutoCloseable... resources) {
        for (AutoCloseable resource : resources) {
            try {
                if (resource != null) {
                    resource.close();
                }
            } catch (Exception e) {
                // Log the exception if needed, but do not rethrow as it is closing
            }
        }
    }
}
