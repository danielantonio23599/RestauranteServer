/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.servlets;

import com.google.gson.Gson;
import com.google.zxing.WriterException;
import com.server.restauranteserver.beans.Mesa;

import com.server.restauranteserver.controle.ControleLogin;
import com.server.restauranteserver.controle.ControleVenda;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "TranferirMesa", urlPatterns = {"/restaurante_server/TranferirMesa"}, initParams = {
    @WebInitParam(name = "mesaDestino", value = ""),
    @WebInitParam(name = "mesaOrigem", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class TransferirMesa extends HttpServlet {

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
        int cod = l.autenticaEmpresa(n, s);
        if (cod > 0) {
            response.setHeader("auth", "1");
            try {
                response.setHeader("sucesso", con.transferirMesa(request.getParameter("mesaOrigem"), request.getParameter("mesaDestino"), cod));
            } catch (WriterException ex) {
                Logger.getLogger(TransferirMesa.class.getName()).log(Level.SEVERE, null, ex);
            }

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
