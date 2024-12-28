package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.ArrayList;

public class PrixProduit {
    private String idPrixProduit;
    private double prixRevient;
    private Date dateProduction;
    private String idProduit;

    // Constructeur avec les setters
    public PrixProduit(String idPrixProduit, double prixRevient, Date dateProduction, String idProduit) throws DaoException {
        setIdPrixProduit(idPrixProduit);
        setPrixRevient(prixRevient);
        setDateProduction(dateProduction);
        setIdProduit(idProduit);
    }

    public String getIdPrixProduit() {
        return idPrixProduit;
    }

    public void setIdPrixProduit(String idPrixProduit) {
        this.idPrixProduit = idPrixProduit;
    }

    public double getPrixRevient() {
        return prixRevient;
    }

    public void setPrixRevient(double prixRevient) throws DaoException {
        if (prixRevient <= 0) {
            throw new DaoException("Le prix revient doit être supérieur à 0");
        }
        this.prixRevient = prixRevient;
    }

    public Date getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(Date dateProduction) {
        this.dateProduction = dateProduction;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    // Méthode pour insérer un prix de produit
    public void insert() throws DaoException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO prix_produit(id_prixProduit, prixRevient, dateProduction, id_produit) "
                + "VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, idPrixProduit);
            statement.setDouble(2, prixRevient);
            statement.setDate(3, dateProduction);
            statement.setString(4, idProduit);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw new DaoException("Erreur lors de l'insertion du prix du produit", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour mettre à jour un prix de produit
    public void update() throws DaoException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE prix_produit SET prixRevient = ?, dateProduction = ?, id_produit = ? WHERE id_prixProduit = ?"
            );
            statement.setDouble(1, prixRevient);
            statement.setDate(2, dateProduction);
            statement.setString(3, idProduit);
            statement.setString(4, idPrixProduit);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw new DaoException("Erreur lors de la mise à jour du prix du produit", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour supprimer un prix de produit
    public void delete() throws DaoException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM prix_produit WHERE id_prixProduit = ?"
            );
            statement.setString(1, idPrixProduit);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw new DaoException("Erreur lors de la suppression du prix du produit", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour trouver un prix de produit par son ID
    public void find() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM prix_produit WHERE id_prixProduit = ?");
            statement.setString(1, idPrixProduit);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                prixRevient = resultSet.getDouble("prixRevient");
                dateProduction = resultSet.getDate("dateProduction");
                idProduit = resultSet.getString("id_produit");
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération du prix du produit", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour obtenir tous les prix des produits
    public static ArrayList<PrixProduit> getAll() throws DaoException {
        ArrayList<PrixProduit> prixProduits = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM prix_produit");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String idPrixProduit = resultSet.getString("id_prixProduit");
                double prixRevient = resultSet.getDouble("prixRevient");
                Date dateProduction = resultSet.getDate("dateProduction");
                String idProduit = resultSet.getString("id_produit");

                prixProduits.add(new PrixProduit(idPrixProduit, prixRevient, dateProduction, idProduit));
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération des prix des produits", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
        return prixProduits;
    }
}
