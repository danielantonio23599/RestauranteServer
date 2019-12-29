/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.server.restauranteserver.beans.FuncionarioBEAN;
import com.google.gson.Gson;
import com.server.restauranteserver.beans.SharedPreferencesBEAN;
import com.server.restauranteserver.controle.ControleLogin;
import com.server.restauranteserver.controle.FuncionarioControle;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Guilherme
 */
@WebServlet(name = "FazLogin", urlPatterns = {"/restaurante_server/FazLogin"}, initParams = {
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class FazLogin extends HttpServlet {

    ControleLogin l = new ControleLogin();
    FuncionarioControle f = new FuncionarioControle();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int cod = l.autenticaUsuario(request.getParameter("nomeUsuario"), request.getParameter("senha"));
        if (cod > 0) {
            response.setHeader("auth", "1");
            SharedPreferencesBEAN u = l.listarSharedPreferences(cod);
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().println(new Gson().toJson(u));

        } else {
            response.setHeader("auth", "0");
            SharedPreferencesBEAN u = null;
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
