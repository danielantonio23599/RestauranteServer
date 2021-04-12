/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.persistencia;

import com.server.restauranteserver.beans.ProdutoBEAN;
import com.server.restauranteserver.beans.Produtos;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class ProdutoDAO {

    private Connection connection;

    public ProdutoDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public ArrayList<Produtos> buscar(String produto, String u, String s) {

        ArrayList<Produtos> p = new ArrayList<>();
        String sql = "SELECT proCodigo,proNome, proPreco,proQuantidade,proDescricao FROM empresa join produto "
                + "WHERE pro_empCodigo = empCodigo and empEmail = '" + u + "' and empSenha = '" + s + "' and "
                + "(proCodigo LIKE '" + produto + "%' or proNome LIKE '" + produto + "%');";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produtos pp = new Produtos();
                pp.setCodigo(rs.getInt(1));
                pp.setNome(rs.getString(2));
                pp.setPreco(rs.getFloat(3));
                pp.setQuantidade(rs.getFloat(4));
                pp.setDescricao(rs.getString(5));
                p.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return p;

    }

    public boolean adicionar(ProdutoBEAN c, String u, String s) {
        String sql = "INSERT INTO produto (proNome, proPreco, proCusto,proQuantidade, proDescricao, proArmonizacao,"
                + "proPreparo,proTipo,proFoto,pro_empCodigo)"
                + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, (select empCodigo from empresa where empEmail ='" + u + "' and empSenha ='" + s + "'));";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setFloat(2, c.getPreco());
            stmt.setFloat(3, c.getCusto());
            stmt.setFloat(4, c.getQuantidade());
            stmt.setString(5, c.getDescricao());
            stmt.setString(6, c.getArmonizacao());
            stmt.setString(7, c.getPreparo());
            stmt.setString(8, c.getTipo());
            stmt.setBytes(9, c.getFoto());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public ArrayList<ProdutoBEAN> listarALl(String u, String s) {
        ArrayList<ProdutoBEAN> c = new ArrayList<ProdutoBEAN>();

        String sql = "select * from produto join empresa where empCodigo = pro_empCodigo and empEmail ='" + u + "' and empSenha ='" + s + "';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProdutoBEAN ca = new ProdutoBEAN();
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setPreco(rs.getFloat(3));
                ca.setCusto(rs.getFloat(4));
                ca.setQuantidade(rs.getFloat(5));
                ca.setArmonizacao(rs.getString(6));
                ca.setFoto(rs.getBytes(7));
                ca.setPreparo(rs.getString(8));
                ca.setDescricao(rs.getString(9));
                ca.setTipo(rs.getString(10));
                ca.setEmpresa(rs.getInt(14));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public ProdutoBEAN localizar(int produto) {
        ProdutoBEAN ca = new ProdutoBEAN();

        String sql = "select * from produto where proCodigo = " + produto + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setPreco(rs.getFloat(3));
                ca.setCusto(rs.getFloat(4));
                ca.setQuantidade(rs.getFloat(5));
                ca.setArmonizacao(rs.getString(6));
                ca.setFoto(rs.getBytes(7));
                ca.setPreparo(rs.getString(8));
                ca.setDescricao(rs.getString(9));
                ca.setTipo(rs.getString(10));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public ProdutoBEAN localizar(String produto, String u, String s) {
        ProdutoBEAN ca = new ProdutoBEAN();
        ca.setCodigo(0);
        String sql = "select * from produto join empresa where proNome = '" + produto + "' and pro_empCodigo = empCodigo and empEmail ='" + u + "' and empSenha ='" + s + "';";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setPreco(rs.getFloat(3));
                ca.setCusto(rs.getFloat(4));
                ca.setQuantidade(rs.getFloat(5));
                ca.setArmonizacao(rs.getString(6));
                ca.setFoto(rs.getBytes(7));
                ca.setPreparo(rs.getString(8));
                ca.setDescricao(rs.getString(9));
                ca.setTipo(rs.getString(10));
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return ca;
    }

    public void editar(ProdutoBEAN c) {
        String sql = "update produto set proNome = ? , proPreco = ? , proCusto = ? , proQuantidade = ?, proDescricao = ?, proArmonizacao = ?"
                + ", proPreparo = ? , proTipo = ?, proFoto = ? where proCodigo = " + c.getCodigo() + ";";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setFloat(2, c.getPreco());
            stmt.setFloat(3, c.getCusto());
            stmt.setFloat(4, c.getQuantidade());
            stmt.setString(5, c.getDescricao());
            stmt.setString(6, c.getArmonizacao());
            stmt.setString(7, c.getPreparo());
            stmt.setString(8, c.getTipo());
            stmt.setBytes(9, c.getFoto());

            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public void alteraQuantidade(int pro, float qtd) {
        String sql = "update produto set proQuantidade = " + qtd + " where proCodigo = " + pro + ";";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public ArrayList<Produtos> listarProdutos(String u, String s) {
        ArrayList<Produtos> c = new ArrayList<Produtos>();

        String sql = "select * from produto join empresa where pro_empCodigo = empCodigo and empEmail ='" + u + "' and empSenha ='" + s + "';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produtos ca = new Produtos();
                ca.setCodigo(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setPreco(rs.getFloat(3));
                ca.setDescricao(rs.getString(9));
                ca.setTipo(rs.getString(10));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return c;
    }

    public void excluir(int cod) {
        String sql = "delete from produto where proCodigo = ? ";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, cod);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public float quantidadeEstoque(int produto) {
        String sql = "select proQuantidade from produto where proTipo != 'Cozinha' and proCodigo = " + produto + ";";
        System.out.println(sql);
        float quantidade = -1;
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                quantidade = rs.getFloat(1);
            }
            stmt.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return quantidade;
    }

    public ResultSet consulta(String strSql) {
        try {
            //criando o objeto Statement para que seja possivel enviar as consultas
            Statement stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //objeto do ResulSet para receber o resultado da consulta
            ResultSet rs = stmt.executeQuery(strSql);
            return rs;
        } catch (SQLException erro) {
            System.err.println(erro.getMessage());
            return null;
        }
    }

}
