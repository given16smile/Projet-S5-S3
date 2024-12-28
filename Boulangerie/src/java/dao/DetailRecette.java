package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DetailRecette {
    private String id;
    private double qte;
    private String idIngredient;
    private String idRecette;

    // Constructor with setters
    public DetailRecette(String id, double qte, String idIngredient, String idRecette) throws DaoException {
        try {
            setId(id);
            setQte(qte);
            setIdIngredient(idIngredient);
            setIdRecette(idRecette);
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la création du DetailRecette", e);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getQte() {
        return qte;
    }

    public void setQte(double qte) throws DaoException {
        if (qte <= 0) {
            throw new DaoException("La quantité doit être supérieure à 0");
        }
        this.qte = qte;
    }

    public String getIdIngredient() {
        return idIngredient;
    }

    public void setIdIngredient(String idIngredient) {
        this.idIngredient = idIngredient;
    }

    public String getIdRecette() {
        return idRecette;
    }

    public void setIdRecette(String idRecette) {
        this.idRecette = idRecette;
    }

    // Insert the detail recette into the database
    public void insert() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "INSERT INTO detailRecette(id, qte, id_ingredient, id_recette) "
                + "VALUES (?, ?, ?, ?)"
            );
            statement.setString(1, id);
            statement.setDouble(2, qte);
            statement.setString(3, idIngredient);
            statement.setString(4, idRecette);
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
            throw new DaoException("Erreur lors de l'insertion du détail de recette", e);
        } finally {
            close(connection, statement);
        }
    }

    // Update an existing detail recette
    public void update() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "UPDATE detailRecette SET qte = ?, id_ingredient = ?, id_recette = ? WHERE id = ?"
            );
            statement.setDouble(1, qte);
            statement.setString(2, idIngredient);
            statement.setString(3, idRecette);
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
            throw new DaoException("Erreur lors de la mise à jour du détail de recette", e);
        } finally {
            close(connection, statement);
        }
    }

    // Delete a detail recette
    public void delete() throws DaoException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = DbConnector.connect();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(
                "DELETE FROM detailRecette WHERE id = ?"
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
            throw new DaoException("Erreur lors de la suppression du détail de recette", e);
        } finally {
            close(connection, statement);
        }
    }

    // Fetch all detail recettes
    public static ArrayList<DetailRecette> getAll() throws DaoException {
        ArrayList<DetailRecette> detailRecettes = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DbConnector.connect();
            statement = connection.prepareStatement("SELECT * FROM detailRecette");
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                detailRecettes.add(new DetailRecette(
                    resultSet.getString("id"),
                    resultSet.getDouble("qte"),
                    resultSet.getString("id_ingredient"),
                    resultSet.getString("id_recette")
                ));
            }
        } catch (Exception e) {
            throw new DaoException("Erreur lors de la récupération des détails de recette", e);
        } finally {
            close(connection, statement, resultSet);
        }
        return detailRecettes;
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
