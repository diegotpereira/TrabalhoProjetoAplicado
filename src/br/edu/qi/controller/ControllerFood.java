/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;


import br.edu.qi.dao.ClienteRepository;
import br.edu.qi.dao.ArquivoRepository;
import br.edu.qi.dao.FoodRepository;
import br.edu.qi.model.Arquivo;


import br.edu.qi.model.Pessoa;
import br.edu.qi.model.Refeicao;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Properties;
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
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;



/**
 *
 * @author Diego
 */
public class ControllerFood implements Initializable{
    
    private Refeicao refeicao;
     
     
     @FXML
    private Parent consulta;
     
    
    @FXML
    private TableView <Refeicao> tabela;
    
    @FXML
    private TableColumn colunaNome;

    @FXML
    private TableColumn colunaDataCadastro;

    @FXML
    private TableColumn colunaDataFabricacao;
    
    @FXML
    private Parent formulario;
    
     @FXML
    private Label rotuloPrato;

    @FXML
    private TextField campoPrato;

    @FXML
    private Label rotuloData;

    @FXML
    private TextField campoData;

    @FXML
    private Label rotuloFabricacao;

    @FXML
    private TextField campoFabricacao;
    
    @FXML
    private Button botaoSalvar;

    @FXML
    private Button botaoCancelar;
    
    @FXML
    private ImageView lblImagem;
    
    private List<Refeicao> listRefeicao;
    
    ObservableList<Refeicao> data;
    
    private final FoodRepository personJDBC = new FoodRepository();
    
    BufferedImage imagem;
    
        
 
    
     @FXML
    private Hyperlink mostrarLink;
       
    private StringProperty usuarioLogado = new SimpleStringProperty();
 

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        try{
                  preencherTableViewRefeicao();
                   //preencherCampos();
        } catch (Exception ex) {
            Logger.getLogger(ControllerPerson.class.getName()).log(Level.SEVERE, null, ex);
        }
     
     tabela.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selecionarItemTvRefeicao(newValue));
     
     trocar(false);
    }
    
      private void trocar(boolean form) {
        formulario.setVisible(form);
        consulta.setVisible(!form);
    }
      
        @FXML
    private void novo() {
        this.refeicao = new Refeicao();
        campoPrato.setText("");
        campoData.setText("");
        campoFabricacao.setText("");
        trocar(true);
    }
     @FXML
    private void salvar() {
    
        Refeicao refeicao = new Refeicao();
        rotuloPrato.setTextFill(Paint.valueOf("#333333"));
        rotuloData.setTextFill(Paint.valueOf("#333333"));
        rotuloFabricacao.setTextFill(Paint.valueOf("#333333"));

        boolean erro = false;

        try {
            refeicao.setDescricaoref(campoPrato.getText().trim());
        } catch (Exception e) {
            rotuloPrato.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        try {
            refeicao.setDatacadastro(campoData.getText());
        } catch (Exception e) {
            rotuloData.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        try {
            refeicao.setDatarefeicao(campoFabricacao.getText());
        } catch (Exception e) {
            rotuloFabricacao.setTextFill(Paint.valueOf("red"));
            erro = true;
        }
        if (erro) {
            return;
        }
        try {
            FoodRepository dao = new FoodRepository();
            dao.adcionar(refeicao);
            
            
            
            System.out.println("Refeição salva com sucesso");
        } catch (Exception e) {
            e.printStackTrace();
        }
        trocar(false); 
        salvarImagem();
        EnviarEmail enviarEmail = new EnviarEmail();
        
    
    
    }
     private void salvarImagem() {
        
        try {
             Arquivo obj = new Arquivo();
             obj.setImagem(ControllerImagem.getImgBytes(imagem));
             ArquivoRepository dao = new ArquivoRepository();
             Boolean foi = dao.inserir(obj);
             if(foi)
             {
                 JOptionPane.showMessageDialog(null, "Imagem enviada com sucesso");
         
             }
             else
             {
                JOptionPane.showMessageDialog(null, "Imagem não enviada");
         
             }
             
             } catch (Exception ex) {
             Logger.getLogger(ControllerFood.class.getName()).log(Level.SEVERE, null, ex);
         }
        
    }
    
     @FXML
    private void cancelar() {
    trocar(false);
    }
     @FXML
    public void alterar(int codigo) throws Exception {
        
         Refeicao refeicao = tabela.getSelectionModel().getSelectedItem();
        boolean btConfirmarClicked = abrirRefeicao(refeicao);
         if (refeicao != null) {
            if (btConfirmarClicked) {
                FoodRepository.alterar(refeicao);
                preencherTableViewRefeicao();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Por favor, escolha uma refeição da tabela");
            alert.show();
        }
        
    }
    @FXML
    private void carregaImagem(){
    JFileChooser fc = new JFileChooser();
    int res = fc.showOpenDialog(null);
    if (res == JFileChooser.APPROVE_OPTION) {
            File arquivo = fc.getSelectedFile();

            try {
                imagem = ControllerImagem.setImagemDimensao(arquivo.getAbsolutePath(), 160, 160);

               // lblImagem.setImage(new ImageIcon(imagem));

            } catch (Exception ex) {
               // System.out.println(ex.printStackTrace().toString());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Voce nao selecionou nenhum arquivo.");
        }
    
    }
    
    public void excluir(int codigo) {
        
           try {
            this.refeicao = FoodRepository.recuperar(codigo);
            //PersonJDBC.excluir(pessoa);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        //atualizarGrade(0); 
        
    }
     

    private void preencherTableViewRefeicao() throws Exception {
         colunaNome.setCellValueFactory(new PropertyValueFactory<>("descricaoref"));
        colunaDataCadastro.setCellValueFactory(new PropertyValueFactory<>("datacadastro"));
        colunaDataFabricacao.setCellValueFactory(new PropertyValueFactory<>("datarefeicao"));
        
        listRefeicao = personJDBC.listar();
        data = FXCollections.observableArrayList(listRefeicao);
        tabela.setItems(data);
    }

    private void selecionarItemTvRefeicao(Refeicao refeicao) {
         if (refeicao != null) {
            ///lbCodigo.setText(String.valueOf(pessoa.getCodCliente()));
            campoPrato.setText(refeicao.getDescricaoref());
            campoData.setText(refeicao.getDatacadastro());
            campoFabricacao.setText(refeicao.getDatarefeicao());
        } else {
            //lbCodigo.setText("");
            campoPrato.setText("");
            campoData.setText("");
            campoFabricacao.setText("");
        } 
    }
  
    private boolean abrirRefeicao(Refeicao refeicao) throws IOException {
        return false;
       
    }

   
    public void editar(int codigo) {
        
    }

   
        
    }

   

   
