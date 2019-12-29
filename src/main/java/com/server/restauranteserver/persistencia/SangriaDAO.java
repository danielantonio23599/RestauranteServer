/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.persistencia;

import com.server.restauranteserver.beans.SangriaBEAN;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Daniel
 */
public class SangriaDAO {

    private Connection connection;

    private PreparedStatement stmt;

    public SangriaDAO() {
        this.connection = ConnectionFactory.getConnection();
    }

    public ArrayList<SangriaBEAN> buscar(int caixa) {

        ArrayList<SangriaBEAN> p = new ArrayList<>();
        String sql = "SELECT * FROM sangria WHERE san_caiCodigo = " + caixa + ";";

        try {
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                SangriaBEAN pp = new SangriaBEAN();
                pp.setCodigo(rs.getInt(1));
                pp.setValor(rs.getFloat(2));
                pp.setTime(rs.getString(3));
                pp.setCaixa(rs.getInt(4));
                p.add(pp);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return p;

    }

    public boolean adicionar(SangriaBEAN c) {
        String sql = "INSERT INTO sangria (sanValor, sanTime, san_caiCodigo)"
                + " VALUES (?, ?, ?);";
        try {
            stmt = connection.prepareStatement(sql);
            stmt.setFloat(1, c.getValor());
            stmt.setString(2, c.getTime());
            stmt.setInt(3, c.getCaixa());
            stmt.execute();
            stmt.close();
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float getTotalSangriasCaixa(int caixa) {
        float total = 0;
        String sql = "SELECT COALESCE(sum(sanValor),0) FROM sangria WHERE san_caiCodigo = " + caixa + ";";

        try {
            stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {

                total = rs.getFloat(1);

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }

        return total;
    }

}
