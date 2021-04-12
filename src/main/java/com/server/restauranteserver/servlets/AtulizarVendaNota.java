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
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.ServletContext;
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
@WebServlet(name = "AtualizaVendaNota", urlPatterns = {"/restaurante_server/AtualizaVendaNota"}, initParams = {
    @WebInitParam(name = "venda", value = ""),
    @WebInitParam(name = "nomeUsuario", value = ""),
    @WebInitParam(name = "senha", value = "")})
public class AtulizarVendaNota extends HttpServlet {

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
        ServletContext contexto = request.getServletContext();
        String str = new String(request.getParameter("venda").getBytes("iso-8859-1"), "UTF-8");
        VendaBEAN c = new GsonBuilder().setDateFormat("dd-MM-yyyy HH:mm:ss").create().fromJson(str, VendaBEAN.class);
        File filePath = pro.atualizaVendaNota(c, usuario, senha, contexto);
        if (filePath != null) {
            response.setHeader("auth", "1");
            response.setHeader("nome", filePath.getName());
            File downloadFile = filePath;

            FileInputStream inStream = new FileInputStream(downloadFile);

            // obtém ServletContext
            ServletContext context = getServletContext();

            // obtém o tipo MIME do arquivo
            String mimeType = context.getMimeType(filePath.getAbsolutePath());
            if (mimeType == null) {
                // definido como tipo binário se o mapeamento MIME não for encontrado
                mimeType = "application/octet-stream";
            }
            System.out.println("MIME type: " + mimeType);

            // modifica a resposta
            response.setContentType(mimeType);
            response.setContentLength((int) downloadFile.length());

            // força o download
            String headerKey = "Content-Disposition";
            String headerValue = String.format("attachment; filename=\"%s\"", downloadFile.getName());
            response.setHeader(headerKey, headerValue);

            // obtém o fluxo de saída da resposta
            OutputStream outStream = response.getOutputStream();

            byte[] buffer = new byte[4096];
            int bytesRead = -1;

            while ((bytesRead = inStream.read(buffer)) != -1) {
                outStream.write(buffer, 0, bytesRead);
            }
            inStream.close();
            outStream.close();
        } else {
            response.setHeader("auth", "0");
            response.setHeader("nome", "0");
            System.out.println("nulll");
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
