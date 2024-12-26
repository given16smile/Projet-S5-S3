package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;


public class Fournisseur {
    private String id;
    private String contact;
    private String nom;

    public Fournisseur() {}

    public Fournisseur(String id, String contact, String nom) {
        setId(id);
        setContact(contact);
        setNom(nom);
    }

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

    public void insert() throws Exception {
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
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public void find() throws Exception {
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
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    // Méthode pour mettre à jour un fournisseur
    public void update() throws Exception {
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
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public void delete() throws Exception {
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
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }
    }

    public static ArrayList<Fournisseur> getAll() throws Exception {
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
        } finally {
            if (resultSet != null) resultSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return fournisseurs;
    }
}
