/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.restauranteserver.beans.ProdutoBEAN;
import com.server.restauranteserver.controle.ControleFuncionario;
import com.server.restauranteserver.controle.ControleLogin;
import com.server.restauranteserver.controle.ControleProduto;
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
@WebServlet(name = "AdicionarProduto", urlPatterns = {"/restaurante_server/AdicionarProduto"}, initParams = {
    @WebInitParam(name = "produto", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class CadastrarProduto extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleProduto pro = new ControleProduto();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String usuario = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String senha = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(usuario,senha);
        if (cod > 0) {
            String str = new String (request.getParameter("produto").getBytes ("iso-8859-1"), "UTF-8");
            response.setHeader("auth", "1");
            ProdutoBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, ProdutoBEAN.class);
            System.out.println(c.getNome());
            response.setHeader("sucesso", pro.cadastrar(c, cod));

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
