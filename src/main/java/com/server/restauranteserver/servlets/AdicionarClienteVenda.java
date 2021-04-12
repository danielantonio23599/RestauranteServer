/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.server.restauranteserver.servlets.*;
import com.google.gson.Gson;
import com.server.restauranteserver.beans.Mesa;
import com.server.restauranteserver.beans.Venda;
import com.server.restauranteserver.beans.VendaBEAN;

import com.server.restauranteserver.controle.ControleLogin;
import com.server.restauranteserver.controle.ControleLogin;
import com.server.restauranteserver.controle.ControleVenda;
import com.server.restauranteserver.controle.ControleVenda;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "AdicionarClienteVenda", urlPatterns = {"/restaurante_server/AdicionarClienteVenda"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = ""),
    @WebInitParam(name = "venda", value = ""),
    @WebInitParam(name = "cliente", value = "")})
public class AdicionarClienteVenda extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleVenda con = new ControleVenda();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int venda = Integer.parseInt(new String(request.getParameter("venda").getBytes("iso-8859-1"), "UTF-8"));
        int clente = Integer.parseInt(new String(request.getParameter("cliente").getBytes("iso-8859-1"), "UTF-8"));
        int codE = l.autenticaEmpresa(n, s);
        if (codE > 0) {
            response.setHeader("auth", "1");
            response.setHeader("sucesso", con.adicionarClienteVenda(venda, clente));

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