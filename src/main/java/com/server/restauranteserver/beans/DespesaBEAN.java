/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.beans;

/**
 *
 * @author Daniel
 */
public class DespesaBEAN {
    private int codigo;
    private String nome;
    private String descricao;
    private float preco;
    private boolean despesaCaixa;

    public boolean isDespesaCaixa() {
        return despesaCaixa;
    }

    public void setDespesaCaixa(boolean despesaCaixa) {
        this.despesaCaixa = despesaCaixa;
    }
    

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }
    

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    
    
}
