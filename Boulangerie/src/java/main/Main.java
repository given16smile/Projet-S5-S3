/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import dao.DbConnector;

/**
 *
 * @author Tsiky
 */
public class Main  {
    public static void main(String[] args) throws Exception{
        try {
            DbConnector.connect();
        } catch (Exception e) {
            throw e;
        } finally{
            DbConnector.connect().close();
        }
    }
    
}
