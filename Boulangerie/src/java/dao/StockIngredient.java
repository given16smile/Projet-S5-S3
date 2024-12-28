package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;

public class StockIngredient {
    private String idFournisseur;
    private String idIngredient;
    private int qte;
    private Date dateStock;
    private double prixAchat;
    private double prixAchatUnitaire;

    // Constructor with setters
    public StockIngredient(String idFournisseur, String idIngredient, int qte, Date dateStock, 
                           double prixAchat, double prixAchatUnitaire) throws Exception {
        setIdFournisseur(idFournisseur);
        setIdIngredient(idIngredient);
        setQte(qte);
        setDateStock(dateStock);
        setPrixAchat(prixAchat);
        setPrixAchatUnitaire(prixAchatUnitaire);
    }

    public String getIdFournisseur() {
        return idFournisseur;
    }

    public void setIdFournisseur(String idFournisseur) {
        this.idFournisseur = idFournisseur;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public int getQte() {
        return qte;
    }

    public void setQte(int qte) throws Exception {
        if (qte <= 0) {
            throw new Exception("La quantité doit être supérieure à 0");
        }
        this.qte = qte;
    }

    public Date getDateStock() {
        return dateStock;
    }

    public void setDateStock(Date dateStock) {
        this.dateStock = dateStock;
    }

    public double getPrixAchat() {
        return prixAchat;
    }

    public void setPrixAchat(double prixAchat) throws Exception {
        if (prixAchat <= 0) {
            throw new Exception("Le prix d'achat doit être supérieur à 0");
        }
        this.prixAchat = prixAchat;
    }

    public double getPrixAchatUnitaire() {
        return prixAchatUnitaire;
    }

    public void setPrixAchatUnitaire(double prixAchatUnitaire) throws Exception {
        if (prixAchatUnitaire <= 0) {
            throw new Exception("Le prix d'achat unitaire doit être supérieur à 0");
        }
        this.prixAchatUnitaire = prixAchatUnitaire;
    }

    public void insert() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO stock_indgredient(id_fournisseur, id_ingredient, qte, dateStock, prixAchat, prixAchatUnitaire) "
                + "VALUES (?, ?, ?, ?, ?, ?)"
            );
            statement.setString(1, idFournisseur);
            statement.setString(2, idIngredient);
            statement.setInt(3, qte);
            statement.setDate(4, dateStock);
            statement.setDouble(5, prixAchat);
            statement.setDouble(6, prixAchatUnitaire);
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
