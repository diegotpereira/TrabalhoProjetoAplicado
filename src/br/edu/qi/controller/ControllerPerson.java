/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;

import br.edu.qi.dao.ClienteRepository;
import br.edu.qi.model.Pessoa;
import java.io.IOException;
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
public class ControllerPerson implements Initializable{
    
     private Pessoa pessoa;
    
    @FXML
    private Parent consulta;
    
    
    @FXML
    private TableView <Pessoa> tabela;
    
    @FXML
    private TableColumn<Pessoa, Boolean> selectCol;
    
    @FXML
    private TableColumn<Pessoa, String> colunaNome;
    
     @FXML
    private TableColumn<Pessoa, String> telefoneColumn;
    
    @FXML
    private TableColumn<Pessoa, String> emailColumn;
    
    @FXML
    private Parent formulario;
    
     @FXML
    private Label rotuloNome;

    @FXML
    private TextField campoNome;

    @FXML
    private Label rotuloCelular;

    @FXML
    private TextField campoCelular;

    @FXML
    private Label rotuloEmail;

    @FXML
    private TextField campoEmail;
    
     @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoCancelar;
    
    private List<Pessoa> listClientes;
    
    ObservableList<Pessoa> data;
    
    private final ClienteRepository personJDBC = new ClienteRepository();
    
    private final int QUANTIDADE_PAGINA = 9;
    
     @FXML
    private Hyperlink mostrarLink;
       
    private StringProperty usuarioLogado = new SimpleStringProperty();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
          try {
            preencherTableViewClientes();
           
        } catch (Exception ex) {
            Logger.getLogger(ControllerPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTvClientes(newValue));
     
     trocar(false);
    }
    
     @FXML
    private void showSelected() {
        pessoa = new Pessoa();
        System.out.println(pessoa);
        for (Pessoa instituicao : tabela.getItems()) {
            System.out.printf("%s (Faltou: %s)%n", pessoa.getNome(), !pessoa.isChamada()? "Sim" : "Não");
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
        this.pessoa = new Pessoa();
        campoNome.setText("");
        campoCelular.setText("");
        campoEmail.setText("");
        trocar(true);
    }
    
    @FXML
    private void salvar() {
    
        Pessoa pessoa = new Pessoa();
        rotuloNome.setTextFill(Paint.valueOf("#333333"));
        rotuloCelular.setTextFill(Paint.valueOf("#333333"));
        rotuloEmail.setTextFill(Paint.valueOf("#333333"));

        boolean erro = false;

        try {
            pessoa.setNome(campoNome.getText().trim());
        } catch (Exception e) {
            rotuloNome.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        try {
            pessoa.setCelular(campoCelular.getText());
        } catch (Exception e) {
            rotuloCelular.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        try {
            pessoa.setEmail(campoEmail.getText());
        } catch (Exception e) {
            rotuloEmail.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        if (erro) {
            return;
        }
        try {
            ClienteRepository dao = new ClienteRepository();
            dao.adcionar(pessoa);
            
            System.out.println("Cliente salvo com sucesso");
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
    public void alterar() throws IOException, Exception {
        
        Pessoa pessoa = tabela.getSelectionModel().getSelectedItem();
        boolean btConfirmarClicked = abrirClientes(pessoa);
         if (pessoa != null) {
            if (btConfirmarClicked) {
                ClienteRepository.alterar(pessoa);
                preencherTableViewClientes();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha um cliente na tabela");
            alert.show();
        }

    }

    @FXML
    public void excluir(int codigo) {        
           try {
            this.pessoa = ClienteRepository.recuperar(codigo);
            //PersonJDBC.excluir(pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //atualizarGrade(0);        
    }

    private void preencherTableViewClientes() throws Exception {
          //Cria um vínculo entre as colunas na tela e os atributos da classe Cliente
        colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        telefoneColumn.setCellValueFactory(new PropertyValueFactory<>("celular"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        //Traz os clientes cadastrados no BD
        listClientes = personJDBC.listar();
        
        //vincla o observador com a lista trazida do BD; sempre que um novo cliente for cadastrado
        //ele também aparecerá na tela
        data = FXCollections.observableArrayList(listClientes);
        tabela.setItems(data);
    }

    private void selecionarItemTvClientes(Pessoa pessoa) {
         if (pessoa != null) {
            ///lbCodigo.setText(String.valueOf(pessoa.getCodCliente()));
            campoNome.setText(pessoa.getNome());
            campoCelular.setText(pessoa.getCelular());
            campoEmail.setText(pessoa.getEmail());
        } else {
            //lbCodigo.setText("");
            campoCelular.setText("");
            campoCelular.setText("");
            campoEmail.setText("");
        }
    }
    
    private boolean abrirClientes(Pessoa pessoa) throws IOException {
        return false;
       
    }

   
    
}
