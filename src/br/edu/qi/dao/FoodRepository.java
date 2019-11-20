/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.dao;

import br.edu.qi.conexao.ConnectionFactory;
import br.edu.qi.model.Pessoa;
import br.edu.qi.model.Refeicao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Diego
 */
public class FoodRepository {
    
      public static void salvar(Refeicao refeicao) throws Exception {
        if (refeicao.getIdrefeicoes() == 0) {
            
            adcionar(refeicao);
            
        } else {
            alterar(refeicao);
        }
    }
     

    public static void adcionar(Refeicao refeicao) throws Exception {
        
        ConnectionFactory c = new ConnectionFactory();
        if (refeicao == null) {
            throw new Exception("Erro: arquivo não pode ser em branco.");
        }
                    
			//conn = new ConnectionFactory().getCon();
			String SQL = "INSERT INTO refeicoes(descricaoRef , dataCadastro, dataRefeicao)"
                     + "values(?,?,?)";               
                          
    			PreparedStatement ps = c.getCon().prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, refeicao.getDescricaoref());
                        ps.setString(2, refeicao.getDatacadastro()); 
                        ps.setString(3, refeicao.getDatarefeicao());
                        ps.execute();
			c.confirmar();
	}
    
     public static void alterar(Refeicao refeicao) throws Exception {
        ConnectionFactory c = new ConnectionFactory();
        String sql = "UPDATE refeicoes SET descricaoref=?, datacadastro=?, datarefeicao=? WHERE idrefeicoes=?";
        PreparedStatement ps = c.getCon().prepareStatement(sql);
        ps.setString(1, refeicao.getDescricaoref());
        ps.setString(2, refeicao.getDatacadastro());
        ps.setString(3, refeicao.getDatarefeicao());
        ps.setInt(4, refeicao.getIdrefeicoes());
        ps.execute();
        ps.close();
    }
    
    
    public ObservableList<Refeicao> listar()throws Exception {
		Connection conn;
		ObservableList<Refeicao> itens = FXCollections.observableArrayList();
			conn = new ConnectionFactory().getCon();
			String sql = "select * from refeicoes";
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
			        Refeicao refeicao = new Refeicao();                                                               
				refeicao.setDescricaoref(rs.getString("descricaoRef"));
				refeicao.setDatacadastro(rs.getString("dataCadastro"));
				refeicao.setDatarefeicao(rs.getString("dataRefeicao"));
				itens.add(refeicao);
			}
                        System.out.println("Chegou aqui item");
		return itens;
                
	}
    
    public static Refeicao recuperar(int codigo) throws Exception {
        ConnectionFactory c = new ConnectionFactory();        
        String SQL = "SELECT * FROM refeicoes WHERE idrefeicoes=?";
        PreparedStatement ps = c.getCon().prepareStatement(SQL);    
        ps.setInt(1, codigo);
        ResultSet rs = ps.executeQuery();
        Refeicao refeicao = new Refeicao();
        if (rs.next()) {
            refeicao.setIdrefeicoes(rs.getInt("idrefeicoes"));
            refeicao.setDescricaoref(rs.getString("descricaoref"));
            refeicao.setDatacadastro(rs.getString("datacadastro"));
            refeicao.setDatarefeicao(rs.getString("datarefeicao"));
            System.out.println("Chegou aqui lista 25");
        }

        return refeicao;
    }
    
	public void excluir(Refeicao refeicao){
		Connection conn;
		try {
			conn = new ConnectionFactory().getCon();
			String sql = "DELETE FROM pessoa WHERE nome = ?";
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, refeicao.getDescricaoref());			
			ps.executeUpdate();
			ps.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
    
}
