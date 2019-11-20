/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.model;





import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

/**
 *
 * @author Diego
 */
public class Pessoa {
   
    private int idpessoa; 
    private Instituicao instituicao;
    private String nome;
    private String celular;
    public String email;    
    private BooleanProperty chamada = new SimpleBooleanProperty(true);

    public Pessoa(int idpessoa, Instituicao instituicao, String celular, String email) {
        this.idpessoa = idpessoa;
        this.instituicao = instituicao;
        this.celular = celular;
        this.email = email;
    }

    public Pessoa() {
        
    }

    public int getIdpessoa() {
        return idpessoa;
    }

    public void setIdpessoa(int idpessoa) {
        this.idpessoa = idpessoa;
    }

    public Instituicao getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(Instituicao instituicao) {
        this.instituicao = instituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BooleanProperty getChamada() {
        return chamada;
    }

    public void setChamada(BooleanProperty chamada) {
        this.chamada = chamada;
    }

   @Override
    public String toString() {
        return getNome();
    }


    public boolean isChamada() {
        return false;
    }

    public boolean IsValid() {
        return false;
    }

}