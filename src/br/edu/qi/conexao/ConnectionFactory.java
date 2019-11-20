/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Diego
 */
public class ConnectionFactory {
    
      public ConnectionFactory(){
      
      
      }
      
      Connection conec;
    
  public Connection getCon(){
        
        try {
            Class.forName("org.postgresql.Driver");
            conec  = DriverManager.getConnection("jdbc:postgresql://localhost:5432/cliente", "postgres","1234");
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return conec;
    }  
   public void confirmar() throws SQLException {
        try {
            conec.commit();
        } catch (SQLException e) {
            throw new SQLException("Problemas na instrução SQL.\n" + e.getMessage());
        } finally {
            conec.close();
        }
    }
}
