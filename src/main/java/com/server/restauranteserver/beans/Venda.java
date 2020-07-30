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
public class Venda {

    private int codigo;
    private String hora;
    private float valor;
    private float valorFinal;
    private float custo;
    private float desconto;
    private float frete;
    private String pagamento;
    private int caixa;
    private String status;
    private int entrega;
    private String cliente;
    private String data;
    private Float devolucao;
    private byte[] cliFoto;
    private byte[] proFoto;

    public byte[] getCliFoto() {
        return cliFoto;
    }

    public void setCliFoto(byte[] cliFoto) {
        this.cliFoto = cliFoto;
    }

    public byte[] getProFoto() {
        return proFoto;
    }

    public void setProFoto(byte[] proFoto) {
        this.proFoto = proFoto;
    }
    

    public Float getDevolucao() {
        return devolucao;
    }

    public void setDevolucao(Float devolucao) {
        this.devolucao = devolucao;
    }

    public float getValorFinal() {
        return valorFinal;
    }

    public void setValorFinal(float valorFinal) {
        this.valorFinal = valorFinal;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public float getCusto() {
        return custo;
    }

    public void setCusto(float custo) {
        this.custo = custo;
    }

    public float getDesconto() {
        return desconto;
    }

    public void setDesconto(float desconto) {
        this.desconto = desconto;
    }

    public float getFrete() {
        return frete;
    }

    public void setFrete(float frete) {
        this.frete = frete;
    }

    public String getPagamento() {
        return pagamento;
    }

    public void setPagamento(String pagamento) {
        this.pagamento = pagamento;
    }

    public int getCaixa() {
        return caixa;
    }

    public void setCaixa(int caixa) {
        this.caixa = caixa;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getEntrega() {
        return entrega;
    }

    public void setEntrega(int entrega) {
        this.entrega = entrega;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
