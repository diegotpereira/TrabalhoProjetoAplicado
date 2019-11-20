/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.dao;

import java.util.ArrayList;

/**
 *
 * @author Diego
 */
public interface IDao <T> {
    
   public void editar(int codigo);

    public void excluir(int codigo);
    
}
