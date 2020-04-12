/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.google.gson.Gson;
import com.server.restauranteserver.beans.Mesa;

import com.server.restauranteserver.controle.ControleLogin;
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
@WebServlet(name = "ListarMesaAberta", urlPatterns = {"/restaurante_server/ListarMesaAberta"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class ListarMesasApp extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleVenda con = new ControleVenda();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int codE = l.autenticaEmpresa(request.getParameter("nomeUsuario"), request.getParameter("senha"));
        int cod = l.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"));
        if (cod > 0 || codE > 0) {
            response.setHeader("auth", "1");
            ArrayList<Mesa> u = con.getMesaAberta(codE);
            if (u != null) {
                //sucesso
                response.setHeader("sucesso", "1");
            } else {
                //caixa não aberto
                response.setHeader("sucesso", "0");
            }
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));

        } else {
            response.setHeader("auth", "0");
            ArrayList<Mesa> u = null;
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));
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
