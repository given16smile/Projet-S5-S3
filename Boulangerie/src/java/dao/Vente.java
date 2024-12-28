package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class Vente {
    private String id;
    private Date dateVente;
    private double prixVente;
    private int quantite;
    private String idProduit;

    // Constructor with setters
    public Vente(String id, Date dateVente, double prixVente, int quantite, String idProduit) throws Exception {
        setId(id);
        setDateVente(dateVente);
        setPrixVente(prixVente);
        setQuantite(quantite);
        setIdProduit(idProduit);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDateVente() {
        return dateVente;
    }

    public void setDateVente(Date dateVente) {
        this.dateVente = dateVente;
    }

    public double getPrixVente() {
        return prixVente;
    }

    public void setPrixVente(double prixVente) throws Exception {
        if (prixVente <= 0) {
            throw new Exception("Le prix de vente doit être supérieur à 0");
        }
        this.prixVente = prixVente;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) throws Exception {
        if (quantite <= 0) {
            throw new Exception("La quantité doit être supérieure à 0");
        }
        this.quantite = quantite;
    }

    public String getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(String idProduit) {
        this.idProduit = idProduit;
    }

    public void insert() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO vente(id, dateVente, prixVente, quantite, id_produit) "
                + "VALUES (?, ?, ?, ?, ?)"
            );
            statement.setString(1, id);
            statement.setDate(2, dateVente);
            statement.setDouble(3, prixVente);
            statement.setInt(4, quantite);
            statement.setString(5, idProduit);
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
}

