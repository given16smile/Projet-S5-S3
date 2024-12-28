package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class FournisseurIngredient {
    private String idFournisseur;
    private String idIngredient;
    private String adresse;

    // Constructor with setters
    public FournisseurIngredient(String idFournisseur, String idIngredient, String adresse) throws Exception {
        setIdFournisseur(idFournisseur);
        setIdIngredient(idIngredient);
        setAdresse(adresse);
    }

    // Getters and Setters
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

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) throws Exception {
        if (adresse == null || adresse.trim().isEmpty()) {
            throw new Exception("L'adresse ne peut pas être vide.");
        }
        this.adresse = adresse;
    }

    // Insert method to save a FournisseurIngredient into the database
    public void insert() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);

            statement = connection.prepareStatement(
                "INSERT INTO fournisseur_ingredient(id_fournisseur, id_ingredient, adresse) "
                + "VALUES (?, ?, ?)"
            );
            statement.setString(1, idFournisseur);
            statement.setString(2, idIngredient);
            statement.setString(3, adresse);

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
            throw new DaoException("Erreur lors de l'insertion du fournisseur-ingredient", e);
        } finally {
            close(connection, statement);
        }
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
