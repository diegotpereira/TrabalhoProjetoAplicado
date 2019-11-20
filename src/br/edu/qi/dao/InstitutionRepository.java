/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.dao;

import br.edu.qi.conexao.ConnectionFactory;
import br.edu.qi.model.Instituicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Diego
 */
public class InstitutionRepository {
    
      public static void salvar(Instituicao instituicao) throws Exception {
        if (instituicao.getIdinstituicao()== 0) {
            
            adcionar(instituicao);
            
        } else {
            alterar(instituicao);
        }
    
}

    public static void adcionar(Instituicao instituicao) throws SQLException, Exception {
        ConnectionFactory c = new ConnectionFactory(); 
        if (instituicao == null) {
            throw new Exception("Erro: arquivo não pode ser em branco.");
        }
                    System.out.println("Chegou aqui DAO 2");
		    String SQL = "INSERT INTO instituicao(nome , endereco, email, telefone)"
                     + "values(?,?,?,?)"; 
                        
                           
    			PreparedStatement ps = c.getCon().prepareStatement(SQL);
			ps.setString(1, instituicao.getNome());
                        ps.setString(2, instituicao.getEndereco()); 
                        ps.setString(3, instituicao.getEmail());
                        ps.setString(4, instituicao.getTelefone()); 
                        ps.execute();
			c.confirmar();
                        
    }

    public static void alterar(Instituicao instituicao) throws SQLException {
        
          ConnectionFactory c = new ConnectionFactory();
        String sql = "UPDATE instituicao SET NOME=?, ENDERECO=?, EMAIL=? , TELEFONE=? WHERE idinstituicao=?";
        PreparedStatement ps = c.getCon().prepareStatement(sql);
        ps.setString(1, instituicao.getNome());
        ps.setString(2, instituicao.getEndereco());
        ps.setString(3, instituicao.getEmail());
        ps.setString(4, instituicao.getTelefone());
        ps.setInt(5, instituicao.getIdinstituicao());
        ps.execute();
        ps.close();
        
    }
    
    public ObservableList<Instituicao> listar()throws Exception {

		Connection conn;
		ObservableList<Instituicao> itens = FXCollections.observableArrayList();
		
			conn = new ConnectionFactory().getCon();
			String sql = "select * from instituicao";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Instituicao instituicao = new Instituicao();
				instituicao.setNome(rs.getString("nome"));
                                instituicao.setEndereco(rs.getString("endereco"));
				instituicao.setEmail(rs.getString("email"));
                                instituicao.setTelefone(rs.getString("telefone"));
				itens.add(instituicao);
			}
		return itens;   
	}
    public static Instituicao recuperar(int codigo) throws Exception {
        ConnectionFactory c = new ConnectionFactory();
        String SQL = "SELECT * FROM instituicao WHERE idinstituicao=?";
        PreparedStatement ps = c.getCon().prepareStatement(SQL);
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        Instituicao instituicao = new Instituicao();
        if (rs.next()) {
            instituicao.setIdinstituicao(rs.getInt("CODIGO"));
            instituicao.setNome(rs.getString("NOME"));
            instituicao.setEndereco(rs.getString("ENDERECO"));
            instituicao.setEmail(rs.getString("EMAIL"));
            instituicao.setTelefone(rs.getString("TELEFONE"));
            System.out.println("Chegou aqui lista 25");
        }
        return instituicao;
    }
    public void excluir(Instituicao instituicao){
		Connection conn;
		try {
			conn = new ConnectionFactory().getCon();
			String sql = "DELETE FROM instituicao WHERE nome = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, instituicao.getNome());			
			ps.executeUpdate();
			ps.close();
			conn.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}
