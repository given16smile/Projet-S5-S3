/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Tsiky
 */
public class DbConnector {

    private static final String postgres_hote = "localhost";
    private static final String postgres_port = "5432"; 
    private static final String postgres_bdd = "boulangerie";
    private static final String postgres_utilisateur = "postgres";
    private static final String postgres_mdp = "Ituprom16";
   
    private static final String postgresql_url = "jdbc:postgresql://" + postgres_hote + ":" + postgres_port + "/" + postgres_bdd;

    public static final String postgres_driver = "org.postgresql.Driver";

    public static Connection connect() throws ClassNotFoundException, SQLException {
        Connection postgres = null;
        Class.forName(postgres_driver); 
        postgres = DriverManager.getConnection(postgresql_url, postgres_utilisateur, postgres_mdp);
        return postgres;
    }
<<<<<<< Updated upstream
=======
    
   /**
     * Ferme une connexion à la base de données.
     * @param connection La connexion à fermer
     */
    public static void disconnect(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Déconnexion réussie");
            } catch (SQLException e) {
                System.err.println("Erreur lors de la fermeture de la connexion : " + e.getMessage());
            }
        }
    }
>>>>>>> Stashed changes
}
    
 
