/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 *
 * @author Diego
 */
public class ControllerMenu implements Initializable {
    
    @FXML
    private TabPane tabPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }
    @FXML
    private void abrirClientes() {
        abrir("/br/edu/qi/view/PersonEdit.fxml", "Clientes");
    }
    
    @FXML
    private void abrirInstituição() {
        abrir("/br/edu/qi/view/InstitutionEdit.fxml", "Instituição");
    }
    
    @FXML
    private void abrirRefeições() {
        abrir("/br/edu/qi/view/FoodEdit.fxml", "Refeições");
    }
    
     @FXML
    private void abrirEmail() {
        abrir("/br/edu/qi/view/DocumentEdit.fxml", "Email");
    }
 
    private void abrir(String url, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent content = loader.load();
            Tab tab = new Tab(title);
            tab.setContent(content);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
