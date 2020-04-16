/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.server.restauranteserver.persistencia;

import com.server.restauranteserver.beans.Mesa;
import com.server.restauranteserver.beans.ProdutosGravados;
import com.server.restauranteserver.beans.VendaBEAN;
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
public class VendaDAO {

    private Connection connection;

    public VendaDAO() {
        this.connection = ConnectionFactory.getConnection();;
    }

    public int abrirMesa(VendaBEAN c) {
        int lastId = 0;
        String sql = "insert into venda(venCheckIn,venMesa,ven_caiCodigo, venStatus) values (?,?,?,?)";
        System.out.println(" check in " + c.getCheckIn() + " mesa " + c.getMesa() + " caixa " + c.getCaixa());
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, c.getCheckIn());
            stmt.setInt(2, c.getMesa());
            stmt.setInt(3, c.getCaixa());
            stmt.setString(4, "aberta");
            stmt.executeUpdate();
            final ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                lastId = rs.getInt(1);
            }

            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lastId;
    }

    //INSERE QRCODE NA VENDA
    public void inserirQRCode(byte[] qr, int cod) {
        String sql = "update venda set venQRcode = '" + qr + "'  where venCodigo = " + cod + ";";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    private int getVenda(String hora, int caixa) {
        int cod = 0;
        String sql = "select venCodigo from venda where venCheckIn = '" + hora + "' and ven_caiCodigo =" + caixa + " ;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cod = rs.getInt(1);

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return cod;
    }

    public VendaBEAN listarVenda(int venda) {
        VendaBEAN v = new VendaBEAN();
        String sql = "select * from venda where venCodigo = " + venda + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                v.setCodigo(rs.getInt(1));
                v.setQRcode(rs.getBytes(2));
                v.setCheckIn(rs.getString(3));
                v.setCheckOut(rs.getString(4));
                v.setValor(rs.getFloat(5));
                v.setCusto(rs.getFloat(6));
                v.setMesa(rs.getInt(7));
                v.setStatus(rs.getString(8));
                v.setPagamento(rs.getString(9));
                v.setCaixa(rs.getInt(10));

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return v;
    }

    public ArrayList<Mesa> listarMesaAberta(int caixa) {
        ArrayList<Mesa> c = new ArrayList<>();

        String sql = "select (venMesa) as mesa , venStatus, venCodigo,\n"
                + "                COALESCE((select  sum(pedQTD*proPreco) \n"
                + "                from venda join pedido join produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo \n"
                + "                and ven_caiCodigo = " + caixa + " and venStatus = 'aberta' and ped_excCodigo is null and mesa = venMesa ),0) \n"
                + "                as valor from \n"
                + "                caixa join venda  \n"
                + "                where  caiCodigo = ven_caiCodigo and ven_caiCodigo = " + caixa + " \n"
                + "				and caiStatus = 'aberto'and venStatus = 'aberta'  \n"
                + "                group by venMesa;";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mesa ca = new Mesa();
                ca.setMesa(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setVenda(rs.getInt(3));
                ca.setValor(rs.getFloat(4));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public ArrayList<Mesa> listarMesasAbertas(int caixa) {
        ArrayList<Mesa> c = new ArrayList<>();

        String sql = "select (venMesa) as mesa , venStatus,\n"
                + "COALESCE((select  sum(pedQTD*proPreco) \n"
                + "from venda join pedido join produto where  ped_venCodigo = venCodigo and ped_proCodigo = proCodigo "
                + "and ven_caiCodigo= " + caixa + " and venStatus = 'aberta' and ped_excCodigo is null and mesa = venMesa ),0) "
                + "as valor from\n"
                + "caixa join venda join pedido join produto \n"
                + "where \n"
                + "caiCodigo = ven_caiCodigo and ped_venCodigo = venCodigo and ped_proCodigo = proCodigo and ven_caiCodigo = " + caixa
                + " and caiStatus = 'aberto'and venStatus = 'aberta'  \n"
                + "group by venMesa;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Mesa ca = new Mesa();
                ca.setMesa(rs.getInt(1));
                ca.setStatus(rs.getString(2));
                ca.setValor(rs.getFloat(3));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public void atualizaVenda(VendaBEAN c) {
        String sql = "update venda set venCheckOut = '" + c.getCheckOut() + "' , venValor = " + c.getValor() + " , venPagamento = '" + c.getPagamento() + "' "
                + ", venStatus = 'fechada', venQRcode = '" + c.getQRcode() + "', venCusto = " + c.getCusto() + "  where venCodigo = " + c.getCodigo() + " and ven_caiCodigo = " + c.getCaixa() + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            int l = stmt.executeUpdate();
            stmt.close();
            if (l > 0) {
                System.out.println("Foram alterados " + l + " linhas");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean isPagamentoUtlizado(int pagamento, int caixa) {
        int cod = 0;
        String sql = "select venCodigo from venda where venPagamento= '" + pagamento + "' and ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cod = rs.getInt(1);

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        if (cod == 0) {
            return false;
        } else {
            return true;
        }
    }

    public int getVenda(int mesa, int caixa) {
        int cod = 0;
        String sql = "select venCodigo from venda where venMesa = " + mesa + " and venStatus = 'aberta' and ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cod = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        //retorna 0 se não tiver nenhuma venda naquela mesa
        return cod;
    }

    public void excluir(int venda) {
        String sql = "delete from venda where venCodigo = " + venda + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.execute();
            stmt.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public float getValorMesa(int venda) {
        float total = 0;
        String sql = "select sum(pedQTD*proPreco) \n"
                + "from\n"
                + " caixa join venda join pedido join produto \n"
                + "	where\n"
                + "    caiCodigo = ven_caiCodigo and ped_venCodigo = venCodigo \n"
                + "    and ped_proCodigo = proCodigo and caiStatus = 'aberto'and venStatus = 'aberta' and ped_excCodigo is null"
                + " and venCodigo =" + venda + " group by venMesa;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return total;
    }

    public ArrayList<VendaBEAN> listarVendasAbertas(int caixa) {
        ArrayList<VendaBEAN> vendas = new ArrayList<VendaBEAN>();
        String sql = "select * from venda where venStatus = 'aberta' and ven_caiCodigo = " + caixa + ";";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendaBEAN v = new VendaBEAN();
                v.setCodigo(rs.getInt(1));
                v.setQRcode(rs.getBytes(2));
                v.setCheckIn(rs.getString(3));
                v.setCheckOut(rs.getString(4));
                v.setValor(rs.getFloat(5));
                v.setCusto(rs.getFloat(6));
                v.setMesa(rs.getInt(7));
                v.setStatus(rs.getString(8));
                v.setPagamento(rs.getString(9));
                v.setCaixa(rs.getInt(10));
                vendas.add(v);

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return vendas;
    }
    public ArrayList<VendaBEAN> listarVendasFechadas(int caixa) {
        ArrayList<VendaBEAN> vendas = new ArrayList<VendaBEAN>();
        String sql = "select * from venda where venStatus = 'fechada' and ven_caiCodigo = " + caixa + ";";
        System.out.println(sql);
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                VendaBEAN v = new VendaBEAN();
                v.setCodigo(rs.getInt(1));
                v.setQRcode(rs.getBytes(2));
                v.setCheckIn(rs.getString(3));
                v.setCheckOut(rs.getString(4));
                v.setValor(rs.getFloat(5));
                v.setCusto(rs.getFloat(6));
                v.setMesa(rs.getInt(7));
                v.setStatus(rs.getString(8));
                v.setPagamento(rs.getString(9));
                v.setCaixa(rs.getInt(10));
                vendas.add(v);

            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return vendas;
    }

    public float getTotalVendido(int caixa) {
        float total = 0;
        String sql = "select COALESCE(sum(venValor),0)  "
                + "from venda where venStatus = 'fechada' and ven_caiCodigo = " + caixa + " group by ven_caiCodigo;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
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

    public ArrayList<ProdutosGravados> listarProdutosVendidosCaixa(int caixa) {
        ArrayList<ProdutosGravados> c = new ArrayList<ProdutosGravados>();

        String sql = "SELECT  proCodigo, proNome,sum(pedQTD) as unidades ,proPreco from \n"
                + "produto join pedido join venda where venCodigo = ped_venCodigo and ped_proCodigo = proCodigo and venStatus = 'fechada' and ped_excCodigo is null and\n"
                + "ven_caiCodigo = " + caixa + " group by proCodigo;";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ProdutosGravados ca = new ProdutosGravados();

                ca.setCodProduto(rs.getInt(1));
                ca.setNome(rs.getString(2));
                ca.setQuantidade(rs.getFloat(3));
                ca.setValor(rs.getFloat(4));
                c.add(ca);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return c;
    }

    public String isVendasAbertas(int caixa) {
        int total = 0;
        String sql = "SELECT count(venCodigo) FROM venda where ven_caiCodigo = " + caixa + " and venStatus = 'aberta';";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                total = rs.getInt(1);
            }
            stmt.close();

        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return total + "";
    }

}
