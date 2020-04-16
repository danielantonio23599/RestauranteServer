/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.google.gson.GsonBuilder;
import com.server.restauranteserver.beans.DespesaBEAN;
import com.server.restauranteserver.beans.ExcluzaoBEAN;
import com.server.restauranteserver.controle.ControleDespesa;
import com.server.restauranteserver.controle.ControleExcluzao;
import com.server.restauranteserver.controle.ControleLogin;
import java.io.IOException;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;
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
@WebServlet(name = "IncluirDespesaDia", urlPatterns = {"/restaurante_server/IncluirDespesaDia"}, initParams = {
    @WebInitParam(name = "despesa", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class IncluirDespesasDia extends HttpServlet {

    ControleLogin l = new ControleLogin();
    ControleDespesa con_des = new ControleDespesa();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
         String n = new String(request.getParameter("nomeUsuario").getBytes("iso-8859-1"), "UTF-8");
        String s = new String(request.getParameter("senha").getBytes("iso-8859-1"), "UTF-8");
        int cod = l.autenticaEmpresa(n,s);
        if (cod > 0) {
            response.setHeader("auth", "1");
            Type type = new TypeToken<ArrayList<DespesaBEAN>>() {
            }.getType();
            String str = new String (request.getParameter("despesa").getBytes ("iso-8859-1"), "UTF-8");
            ArrayList<DespesaBEAN> c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, type);
            response.setHeader("sucesso", con_des.adicionarDespesaDia(c,cod));

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
