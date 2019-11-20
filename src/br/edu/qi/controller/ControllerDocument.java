/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;


import br.edu.qi.model.ArquivoModel;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;


public class ControllerDocument implements Initializable {

    @FXML
    private TextField emailid;
    @FXML
    private TextField subject;
    @FXML
    private PasswordField password;
    @FXML
    private TextArea message;
    @FXML
    private TextField recieverid;
    
    @FXML
    private Button botaoAnexar;
    
    ArquivoModel[] anexos;
    
    String conteudoMensagem;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void handleSendButton(ActionEvent event) {
        
        SendEmail(emailid.getText(), password.getText(), recieverid.getText(), subject.getText() ,message.getText());
    }
    
    private void   SendEmail(String user, String pass, String to, String sub, String msg)
    {
        Properties prop= new Properties();
        
         prop.put("mail.smtp.ssl.trust","smtp.gmail.com");
         prop.put("mail.smtp.auth",true);
         prop.put("mail.smtp.starttls.enable",true);
         prop.put("mail.smtp.host","smtp.gmail.com");
         prop.put("mail.smtp.port","587");
         
         
         Session session= Session.getInstance(prop, new javax.mail.Authenticator()
         {
             @Override
             protected javax.mail.PasswordAuthentication getPasswordAuthentication()
             {
             return new javax.mail.PasswordAuthentication(user, pass);
       
             }
             
         });
         
         try
         {
             Message message1= new MimeMessage(session);
             
             message1.setFrom( new InternetAddress("diegoteixeirapereira@gmail.com"));
             message1.setRecipients(Message.RecipientType.TO,InternetAddress.parse(to));
             message1.setSubject(sub);
             message1.setText(msg);
             //anexarArquivos(message1, anexos);
             Transport.send(message1);
             
              JOptionPane.showMessageDialog(null,"Mensagem Enviada com Sucesso");
         
         }   
         
         catch(Exception e)
         {
          JOptionPane.showMessageDialog(null,e);
         }
      
        
        
    }
    
    @FXML
    private void anexar(){
    //anexarArquivos(0);
    }

    private void anexarArquivos(Message mensagem, ArquivoModel[] arquivos) throws MessagingException {
        
        //Multipart é o container de partes da mensagem
		Multipart multipart = new MimeMultipart();		
		
		//uma parte específca da mensagem
		BodyPart messageBodyPart = new MimeBodyPart();
		
		//DataHandler para operações diversas nos diferentes tipos de dados
		DataHandler dataHandler = null;
		
		//partew do texto da mensagem
		messageBodyPart.setText(conteudoMensagem);
		
		//adicona o texto ao multipart
		multipart.addBodyPart(messageBodyPart);
							
		//loop: cria um BodyPart para cada anexo e adiciona-o no Multipart 
		for(ArquivoModel anexo : arquivos) {
			
			dataHandler = new DataHandler(anexo.getConteudo(), anexo.getMimeType());
			messageBodyPart = new MimeBodyPart();									
			messageBodyPart.setDataHandler(dataHandler);
			messageBodyPart.setFileName(anexo.getNomeArquivo());
			multipart.addBodyPart(messageBodyPart);
		}
		
		//vincula o Multipart na mensagem
		mensagem.setContent(multipart);
        
    }
    
}
