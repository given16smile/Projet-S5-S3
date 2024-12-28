/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.sql.Connection;

import dao.DbConnector;
import dao.Unite;

/**
 *
 * @author Tsiky
 */
public class Main  {
    public static void main(String[] args) throws Exception{
        Unite kg=  new Unite();
        kg.setNom("l");
        kg.setDescription("litre");
        try {
            kg.insert();
            System.out.println("valider");
            
        } catch (Exception e) {
            throw e;
        } 
    }
    
}
