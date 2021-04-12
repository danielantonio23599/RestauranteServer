/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.controle;

import com.server.restauranteserver.persistencia.ConnectionFactory;
import com.server.restauranteserver.util.GeradorRelatorio;
import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import net.sf.jasperreports.engine.JasperPrint;

/**
 *
 * @author Daniel
 */
public class ControleRelatorio {

    private Connection conexao;
    private ControleCaixa c = new ControleCaixa();

    public ControleRelatorio() {
        this.conexao = ConnectionFactory.getConnection();
    }

    public File geraRelatorioProdutosDownload(ServletContext contexto, int cod, float qtd) {
        String jrxml = contexto.getRealPath("/relatorios/relatorio.jrxml");
        //exibe no terminal o local onde o arquivo se encontra
        // prepara parâmetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("empresa", cod);
        parametros.put("quantidade", qtd);

        // abre conexão com o banco
        // gera relatório
        GeradorRelatorio gerador = new GeradorRelatorio(conexao, contexto);
        File print = gerador.geraPdfDownload(jrxml, "relatorio", parametros);
        try {
            conexao.createStatement().close();
        } catch (SQLException ex) {
            System.out.println("erro inicio : " + ex.getMessage());
        }
        return print;
    }

    public File geraRelatorioTodosProdutosDownload(ServletContext contexto, int cod) {
        String jrxml = contexto.getRealPath("/relatorios/produtos.jrxml");
        //exibe no terminal o local onde o arquivo se encontra
        // prepara parâmetros
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("empresa", cod);
        // abre conexão com o banco
        // gera relatório
        GeradorRelatorio gerador = new GeradorRelatorio(conexao, contexto);
        File print = gerador.geraPdfDownload(jrxml, "produtos", parametros);
        try {
            conexao.createStatement().close();
        } catch (SQLException ex) {
            System.out.println("erro inicio : " + ex.getMessage());
        }
        return print;
    }

    public File geraRelatorioVendas(ServletContext contexto, String u, String s) {
        int caixa = c.getCaixa(u,s);
        String jrxml = contexto.getRealPath("/relatorios/relatorio_caixa.jrxml");
        //exibe no terminal o local onde o arquivo se encontra
        // prepara parâmetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("caixa", caixa);
        // abre conexão com o banco
        // gera relatório
        GeradorRelatorio gerador = new GeradorRelatorio(conexao, contexto);
        File print = gerador.geraPdfDownload(jrxml, "relatorio_caixa", parametros);
        try {
            conexao.createStatement().close();
        } catch (SQLException ex) {
            System.out.println("erro inicio : " + ex.getMessage());
        }
        return print;
    }

    public File geraRelatorioVendas(ServletContext contexto, int cod, String data) {
        String jrxml = contexto.getRealPath("/relatorios/relatorio_caixa_data.jrxml");
        //exibe no terminal o local onde o arquivo se encontra
        // prepara parâmetros
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("empresa", cod);
        parametros.put("data", data);
        // abre conexão com o banco
        // gera relatório
        GeradorRelatorio gerador = new GeradorRelatorio(conexao, contexto);
        File print = gerador.geraPdfDownload(jrxml, "relatorio_caixa_data", parametros);
        try {
            conexao.createStatement().close();
        } catch (SQLException ex) {
            System.out.println("erro inicio : " + ex.getMessage());
        }
        return print;
    }

    public File geraRelatorioMesa(ServletContext contexto, int cod, int venda) {
        String jrxml = contexto.getRealPath("/relatorios/relatorio_mesa.jrxml");
        //exibe no terminal o local onde o arquivo se encontra
        // prepara parâmetros
        Map<String, Object> parametros = new HashMap<>();

        parametros.put("empresa", cod);
        parametros.put("venda", venda);
        // abre conexão com o banco
        // gera relatório
        GeradorRelatorio gerador = new GeradorRelatorio(conexao, contexto);
        File print = gerador.geraPdfDownload(jrxml, "relatorio_mesa", parametros);
        try {
            conexao.createStatement().close();
        } catch (SQLException ex) {
            System.out.println("erro inicio : " + ex.getMessage());
        }
        return print;
    }

    public File geraRelatorioVenda(ServletContext contexto, int empresa, int venda) {
        String jrxml = contexto.getRealPath("/relatorios/relatorio_venda.jrxml");
        //exibe no terminal o local onde o arquivo se encontra
        // prepara parâmetros
        Map<String, Object> parametros = new HashMap<>();
        System.out.println(venda);
        parametros.put("empresa", empresa);
        parametros.put("venda", venda);
        // abre conexão com o banco
        // gera relatório
        GeradorRelatorio gerador = new GeradorRelatorio(conexao, contexto);
        File print = gerador.geraPdfDownload(jrxml, "relatorio_venda", parametros);
        try {
            conexao.createStatement().close();
        } catch (SQLException ex) {
            System.out.println("erro inicio : " + ex.getMessage());
        }
        return print;
    }

}
