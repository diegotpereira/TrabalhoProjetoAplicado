/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.model;

import java.util.Objects;

/**
 *
 * @author Diego
 */
public class Instituicao {
    
    private int idinstituicao;
    private String nome;
    private String endereco;
    private String email;
    private String telefone;
    

    public Instituicao(int idinstituicao, String nome, String endereco, String email, String telefone) {
        this.idinstituicao = idinstituicao;
        this.nome = nome;
        this.endereco = endereco;
        this.email = email;
        this.telefone = telefone;
        
    }

    public Instituicao() {
         this.idinstituicao = 0;
    }

    public int getIdinstituicao() {
        return idinstituicao;
    }

    public void setIdinstituicao(int idinstituicao) {
        this.idinstituicao = idinstituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }


    @Override
    public String toString() {
        return getNome();
    }

    public boolean isChamada() {
        return false;
        
    }

   

   @Override
    public boolean equals(Object o) {
        if (o instanceof Instituicao) {
            Instituicao c = (Instituicao) o;
            if (c.getIdinstituicao() == this.getIdinstituicao()) {
                return true;
            }
        }
        return false;
    }
}
