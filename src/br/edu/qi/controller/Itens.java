package br.edu.qi.controller;

import java.text.ParseException;

public interface Itens {

    public void adicionarItem() throws ParseException;

    public void removerItem(int codigoItem) throws ParseException;

}
