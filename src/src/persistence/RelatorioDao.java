package src.persistence;

import java.sql.*;
import java.util.*;

public class RelatorioDao {

    private Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:banco_de_dados/banco_sqlite.db";
        return DriverManager.getConnection(url);
    }

    // Vendas (cabeçalho) por período
    public List<Map<String, Object>> listarVendasPorPeriodo(String inicioISO, String fimISO) throws SQLException {
        String sql = """
            SELECT v.id_venda,
                   v.data_hora,
                   COALESCE(SUM(iv.quantidade * iv.preco_unitario),0) AS total_bruto,
                   COALESCE(v.desconto,0) AS desconto,
                   (COALESCE(SUM(iv.quantidade * iv.preco_unitario),0) - COALESCE(v.desconto,0)) AS total_liquido
            FROM venda v
            LEFT JOIN item_venda iv ON iv.id_venda = v.id_venda
            WHERE v.data_hora BETWEEN ? AND ?
            GROUP BY v.id_venda, v.data_hora, v.desconto
            ORDER BY v.data_hora;
        """;
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, inicioISO);
            ps.setString(2, fimISO);
            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> out = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id_venda", rs.getInt("id_venda"));
                    row.put("data_hora", rs.getString("data_hora"));
                    row.put("total_bruto", rs.getDouble("total_bruto"));
                    row.put("desconto", rs.getDouble("desconto"));
                    row.put("total_liquido", rs.getDouble("total_liquido"));
                    out.add(row);
                }
                return out;
            }
        }
    }

    // Produtos mais vendidos (top N)
    public List<Map<String, Object>> produtosMaisVendidos(int limit, String inicioISO, String fimISO) throws SQLException {
        String sql = """
            SELECT p.id_produto, p.nome_produto,
                   SUM(iv.quantidade) AS total_qtd,
                   SUM(iv.quantidade * iv.preco_unitario) AS total_valor
            FROM item_venda iv
            JOIN venda v      ON v.id_venda = iv.id_venda
            JOIN produto p    ON p.id_produto = iv.id_produto
            WHERE v.data_hora BETWEEN ? AND ?
            GROUP BY p.id_produto, p.nome_produto
            ORDER BY total_qtd DESC
            LIMIT ?;
        """;
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, inicioISO);
            ps.setString(2, fimISO);
            ps.setInt(3, limit);
            try (ResultSet rs = ps.executeQuery()) {
                List<Map<String, Object>> out = new ArrayList<>();
                while (rs.next()) {
                    Map<String, Object> row = new LinkedHashMap<>();
                    row.put("id_produto", rs.getInt("id_produto"));
                    row.put("nome_produto", rs.getString("nome_produto"));
                    row.put("total_qtd", rs.getInt("total_qtd"));
                    row.put("total_valor", rs.getDouble("total_valor"));
                    out.add(row);
                }
                return out;
            }
        }
    }

    // Faturamento total no período
    public double faturamentoTotal(String inicioISO, String fimISO) throws SQLException {
        String sql = """
            SELECT COALESCE(SUM(iv.quantidade * iv.preco_unitario) - SUM(v.desconto),0) AS fat
            FROM venda v
            LEFT JOIN item_venda iv ON iv.id_venda = v.id_venda
            WHERE v.data_hora BETWEEN ? AND ?;
        """;
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, inicioISO);
            ps.setString(2, fimISO);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? rs.getDouble("fat") : 0.0;
            }
        }
    }
}
