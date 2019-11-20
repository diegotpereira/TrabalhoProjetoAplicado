/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.edu.qi.model;


public class Arquivo {
    
    private int id;
    private Refeicao refeicao;
    private byte[] imagem;

    public Arquivo(int id, Refeicao refeicao, byte[] imagem) {
        this.id = id;
        this.refeicao = refeicao;
        this.imagem = imagem;
    }

    public Arquivo() {
        
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Refeicao getRefeicao() {
        return refeicao;
    }

    public void setRefeicao(Refeicao refeicao) {
        this.refeicao = refeicao;
    }

    public byte[] getImagem() {
        return imagem;
    }

    public void setImagem(byte[] imagem) {
        this.imagem = imagem;
    }
    
   
}
