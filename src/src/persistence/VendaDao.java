package src.persistence;

import src.domain.entities.Venda;
import src.domain.entities.ItemVenda;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class VendaDao {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:banco_de_dados/banco_sqlite.db";
        return DriverManager.getConnection(url);
    }

    // Finaliza a venda com transação: cria venda, cria itens e abate estoque.
    public int finalizarVenda(Venda venda) throws SQLException {
        String sqlVenda = "INSERT INTO venda (data_hora, desconto, forma_pagamento, observacao) VALUES (?, ?, ?, ?)";
        String sqlItem  = "INSERT INTO item_venda (id_venda, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        String sqlAbate = "UPDATE produto SET quantidade = quantidade - ? WHERE id_produto = ?";

        try (Connection conn = getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement psVenda = conn.prepareStatement(sqlVenda, Statement.RETURN_GENERATED_KEYS);
                 PreparedStatement psItem  = conn.prepareStatement(sqlItem);
                 PreparedStatement psAbate = conn.prepareStatement(sqlAbate)) {

                // venda
                String data = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(
                        venda.getDataHora() != null ? venda.getDataHora() : LocalDateTime.now());
                psVenda.setString(1, data);
                psVenda.setDouble(2, venda.getDesconto());
                psVenda.setString(3, venda.getFormaPagamento());
                psVenda.setString(4, venda.getObservacao());
                psVenda.executeUpdate();

                int idVenda;
                try (ResultSet rs = psVenda.getGeneratedKeys()) {
                    rs.next();
                    idVenda = rs.getInt(1);
                }

                // itens
                for (ItemVenda it : venda.getItens()) {
                    psItem.setInt(1, idVenda);
                    psItem.setInt(2, it.getIdProduto());
                    psItem.setInt(3, it.getQuantidade());
                    psItem.setDouble(4, it.getPrecoUnitario());
                    psItem.addBatch();

                    psAbate.setInt(1, it.getQuantidade());
                    psAbate.setInt(2, it.getIdProduto());
                    psAbate.addBatch();
                }
                psItem.executeBatch();

                psAbate.executeBatch();

                conn.commit();
                return idVenda;
            } catch (Exception ex) {
                conn.rollback();
                throw ex;
            } finally {
                conn.setAutoCommit(true);
            }
        }
    }
}
