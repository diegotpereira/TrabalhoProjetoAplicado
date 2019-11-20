/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.qi.dao;

import br.edu.qi.conexao.ConnectionFactory;
import br.edu.qi.model.Arquivo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class ArquivoRepository {
    
    public Boolean inserir(Arquivo exemplo) throws SQLException
    {
        ConnectionFactory c = new ConnectionFactory();
        Boolean retorno = false;
        String sql = "INSERT INTO imagens (imagem) values (?)";
        
        PreparedStatement ps = c.getCon().prepareStatement(sql);
        try
        {
            ps.setBytes(1, exemplo.getImagem());
            ps.executeUpdate();
            retorno = true;
            
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
        
        return retorno;
    
    }
    
    public Arquivo buscar(Integer id) throws SQLException
    {
        ConnectionFactory c = new ConnectionFactory();
        Arquivo retorno = null;
        String sql = "SELECT id,imagem from imagens where id=?";
        PreparedStatement ps = c.getCon().prepareStatement(sql);
        
        try {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next())
            {
                retorno = new Arquivo();
                retorno.setId(rs.getInt("id"));
                retorno.setImagem(rs.getBytes("imagem"));
            
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            retorno = null;
        }
        
        return retorno;
    
    }
    
}
