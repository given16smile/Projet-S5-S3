package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;

public class Production {
    private String id;
    private Date dateProduction;  // Représente la date de production
    private int quantite;         // Quantité produite
    private double cout;          // Coût de production
    private Timestamp datePeremption;  // Date de péremption
    private String idProduit;     // Identifiant du produit

    // Constructeur sans paramètre
    public Production() {}

    // Constructeur avec paramètres
    public Production(String id, Date dateProduction, int quantite, double cout, Timestamp datePeremption, String idProduit) {
       setId(id);
       setDateProduction(dateProduction);
       setQuantite(quantite);
       setCout(cout);
       setDatePeremption(datePeremption); 
       setIdProduit(idProduit);
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateProduction() {
        return dateProduction;
    }

    public void setDateProduction(Date dateProduction) {
        this.dateProduction = dateProduction;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getCout() {
        return cout;
    }

    public void setCout(double cout) {
        this.cout = cout;
    }

    public Timestamp getDatePeremption() {
        return datePeremption;
    }

    public void setDatePeremption(Timestamp datePeremption) {
        this.datePeremption = datePeremption;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    // Méthode pour insérer une production
    public void insert() throws DaoException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO production(id, dateProduction, quantite, cout, datePeremption, id_produit) "
                + "VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, id);
            statement.setDate(2, dateProduction);
            statement.setInt(3, quantite);
            statement.setDouble(4, cout);
            statement.setTimestamp(5, datePeremption);
            statement.setString(6, idProduit);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw new DaoException("Erreur lors de l'insertion de la production", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour mettre à jour une production
    public void update() throws DaoException, Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE production SET dateProduction = ?, quantite = ?, cout = ?, datePeremption = ?, id_produit = ? WHERE id = ?"
            );
            statement.setDate(1, dateProduction);
            statement.setInt(2, quantite);
            statement.setDouble(3, cout);
            statement.setTimestamp(4, datePeremption);
            statement.setString(5, idProduit);
            statement.setString(6, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw new DaoException("Erreur lors de la mise à jour de la production", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour supprimer une production
    public void delete() throws DaoException, SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM production WHERE id = ?"
            );
            statement.setString(1, id);
            statement.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw new DaoException("Erreur lors de la suppression de la production", e);
        } finally {
            try {
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
    }

    // Méthode pour trouver une production par son ID
    public void find() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM production WHERE id = ?");
            statement.setString(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                dateProduction = resultSet.getDate("dateProduction");
                quantite = resultSet.getInt("quantite");
                cout = resultSet.getDouble("cout");
                datePeremption = resultSet.getTimestamp("datePeremption");
                idProduit = resultSet.getString("id_produit");
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération de la production", e);
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

    // Méthode pour obtenir toutes les productions
    public static ArrayList<Production> getAll() throws DaoException {
        ArrayList<Production> productions = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM production");
            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                String id = resultSet.getString("id");
                Date dateProduction = resultSet.getDate("dateProduction");
                int quantite = resultSet.getInt("quantite");
                double cout = resultSet.getDouble("cout");
                Timestamp datePeremption = resultSet.getTimestamp("datePeremption");
                String idProduit = resultSet.getString("id_produit");

                productions.add(new Production(id, dateProduction, quantite, cout, datePeremption, idProduit));
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération des productions", e);
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                throw new DaoException("Erreur lors de la fermeture des ressources", e);
            }
        }
        return productions;
    }
}
