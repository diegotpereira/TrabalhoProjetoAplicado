/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;


import br.edu.qi.dao.InstitutionRepository;
import br.edu.qi.model.Instituicao;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;

/**
 *
 * @author Diego
 */
public class ControllerInstitution implements Initializable{
    
     private Instituicao instituicao;
    
    @FXML
    private Parent consulta;
    
    @FXML
    private TableView <Instituicao> tabela;
    
    @FXML
    private TableColumn<Instituicao, Boolean> selectCol;
    
    @FXML
    private TableColumn colunaNome;

    @FXML
    private TableColumn colunaEndereco;

    @FXML
    private TableColumn colunaEmail;
        
    @FXML
    private TableColumn colunaTelefone;
    
    @FXML
    private Parent formulario;
    
     @FXML
    private Label rotuloNome;

    @FXML
    private TextField campoNome;

    @FXML
    private Label rotuloEndereco;

    @FXML
    private TextField campoEndereco;

    @FXML
    private Label rotuloEmail;

    @FXML
    private TextField campoEmail;
    
     @FXML
    private Label rotuloTelefone;

    @FXML
    private TextField campoTelefone;
    
     @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoCancelar;
    
     private List<Instituicao> listInstituicao;
    
    ObservableList<Instituicao> data;
    
    private final InstitutionRepository personJDBC = new InstitutionRepository();
    
     private final int QUANTIDADE_PAGINA = 9;
    
     @FXML
    private Hyperlink mostrarLink;
       
    private StringProperty usuarioLogado = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
          try {
        
           preencherTableViewInstituicao();
    } catch (Exception ex) {
            Logger.getLogger(ControllerInstitution.class.getName()).log(Level.SEVERE, null, ex);
    }
          tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTvInstituicao(newValue));
      trocar(false);
        
    }
    @FXML
    private void showSelected() {
        instituicao = new Instituicao();
        System.out.println(instituicao);
        for (Instituicao instituicao : tabela.getItems()) {
            System.out.printf("%s (Faltou: %s)%n", instituicao.getNome(), !instituicao.isChamada()? "Sim" : "Não");
        }
        System.out.println();
        //usuarioLogado.set("marco");
    }
    
     private void trocar(boolean form) {
         formulario.setVisible(form);
        consulta.setVisible(!form);
    }
     
      @FXML
    private void novo() {
        this.instituicao = new Instituicao();
        campoNome.setText("");
        campoEndereco.setText("");
        campoEmail.setText("");
        campoTelefone.setText("");
        trocar(true);
    }
    
    @FXML
    private void salvar() {
    
      Instituicao instituicao = new Instituicao();
        rotuloNome.setTextFill(Paint.valueOf("#333333"));
        rotuloEndereco.setTextFill(Paint.valueOf("#333333"));
        rotuloEmail.setTextFill(Paint.valueOf("#333333"));
        rotuloTelefone.setTextFill(Paint.valueOf("#333333"));

        boolean erro = false;

        try {
            instituicao.setNome(campoNome.getText().trim());
        } catch (Exception e) {
            rotuloNome.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        try {
            instituicao.setEndereco(campoEndereco.getText());
        } catch (Exception e) {
            rotuloEndereco.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        try {
            instituicao.setEmail(campoEmail.getText());
        } catch (Exception e) {
            rotuloEmail.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        
         try {
            instituicao.setTelefone(campoTelefone.getText());
        } catch (Exception e) {
            rotuloTelefone.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        if (erro) {
            return;
        }
        try {
            InstitutionRepository dao = new InstitutionRepository();
            dao.adcionar(instituicao);
            
            System.out.println("Instituição salva com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        trocar(false);
    }
     @FXML
    private void cancelar() {
            trocar(false);
    }
       @FXML
    public void alterar() throws Exception {
        
        Instituicao instituicao = tabela.getSelectionModel().getSelectedItem();
        boolean btConfirmarClicked = abrirInstituicao(instituicao);
         if (instituicao != null) {
            if (btConfirmarClicked) {
                InstitutionRepository.alterar(instituicao);
                preencherTableViewInstituicao();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma instituição na tabela");
            alert.show();
        }

    }
  
    
    @FXML
    private void excluir(){}
    
       private void preencherTableViewInstituicao() throws Exception {
         colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
         colunaEndereco.setCellValueFactory(new PropertyValueFactory<>("endereco"));
         colunaEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
         colunaTelefone.setCellValueFactory(new PropertyValueFactory<>("telefone"));
        
          listInstituicao = personJDBC.listar();
        
        //vincla o observador com a lista trazida do BD; sempre que um novo cliente for cadastrado
        //ele também aparecerá na tela
        data = FXCollections.observableArrayList(listInstituicao);
        tabela.setItems(data);
    }
       
        private void selecionarItemTvInstituicao(Instituicao instituicao) {
         if (instituicao != null) {
            ///lbCodigo.setText(String.valueOf(pessoa.getCodCliente()));
            campoNome.setText(instituicao.getNome());
            campoEndereco.setText(instituicao.getEndereco());
            campoEmail.setText(instituicao.getEmail());
            campoTelefone.setText(instituicao.getTelefone());
        } else {
            //lbCodigo.setText("");
            campoNome.setText("");
            campoEndereco.setText("");
            campoEmail.setText("");
            campoTelefone.setText("");
        }
    }

  

    private boolean abrirInstituicao(Instituicao instituicao) {
        return false;
    }
    
    
}
