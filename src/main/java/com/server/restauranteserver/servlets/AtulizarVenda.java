/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.restauranteserver.beans.FuncionarioBEAN;
import com.server.restauranteserver.beans.ProdutoBEAN;
import com.server.restauranteserver.beans.VendaBEAN;
import com.server.restauranteserver.controle.ControleFuncionario;
import com.server.restauranteserver.controle.ControleLogin;
import com.server.restauranteserver.controle.ControleProduto;
import com.server.restauranteserver.controle.ControleVenda;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Daniel
 */
@WebServlet(name = "AtualizaVenda", urlPatterns = {"/restaurante_server/AtualizaVenda"}, initParams = {
    @WebInitParam(name = "venda", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class AtulizarVenda extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleVenda pro = new ControleVenda();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(usuario, senha);
        if (cod > 0) {
            response.setHeader("auth", "1");
            String str = new String(request.getParameter("venda").getBytes("iso-8859-1"), "UTF-8");
           VendaBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, VendaBEAN.class);
            response.setHeader("sucesso", pro.atualizaVenda(c,cod));

        } else {
            response.setHeader("auth", "0");

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
}
