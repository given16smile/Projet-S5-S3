/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author Tsiky
 */
public class Unite {
    String id;
    String nom;
    String description;

    public Unite() {}

    public Unite(String id, String nom, String description) {
        setId(id);
        setNom(nom);
        setDescription(description);
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

    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public void insert () throws Exception{
        Connection connection = null;
         PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO unite(nom,description)"
                + " VALUES (?,?)"
            );
            statement.setString(1, nom);
            statement.setString(2, description);
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
                "SELECT * FROM unite"
                + " WHERE id = ?"
            );
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                nom = resultSet.getString("nom");
                description = resultSet.getString("description");
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }
    }


    public void update() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE unite"
                + " SET nom = ?"
                + " SET description = ?"
                + " WHERE id = ?"
            );
            statement.setString(1, nom);
            statement.setString(2, description);
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
                "DELETE FROM unite"
                + " WHERE id = ?"
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

     public static ArrayList<Unite> getAll() throws Exception {
        ArrayList<Unite> unites = new ArrayList<Unite>();

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement(
                "SELECT * FROM unite"
            );
            resultSet = statement.executeQuery();

            String id;
            String name;
            String description;
            while (resultSet.next()) {
                id = resultSet.getString("id");
                name = resultSet.getString("nom");
                description = resultSet.getString("description");

                unites.add(
                    new Unite(id, name, description)
                );
            }
        } finally {
            if (resultSet != null) {
                resultSet.close();
            }
            if (statement != null) {
                statement.close();
            }
            if (connection != null) {
                connection.close();
            }
        }

        return unites;
    }

}
