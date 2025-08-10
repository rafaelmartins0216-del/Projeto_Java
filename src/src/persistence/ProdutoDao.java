package src.persistence;

import src.domain.entities.Produto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDao {

    private Connection getConnection() throws SQLException {
        // Ajuste o caminho para o seu banco SQLite
        String url = "jdbc:sqlite:banco_de_dados/banco_sqlite.db";
        return DriverManager.getConnection(url);
    }

    // CREATE - Inserir novo produto
    public boolean salvar(Produto produto) {
        String sql = "INSERT INTO produto (nome_produto, preco, quantidade, caminho_imagem) VALUES (?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQtd_estoque());
            stmt.setString(4, produto.getcaminhoImagem());

            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao salvar produto: " + e.getMessage());
            return false;
        }
    }

    // READ - Buscar produto por ID
    public Produto buscarPorId(int id) {
        String sql = "SELECT * FROM produto WHERE id_produto = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome_produto"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade"),
                    rs.getString("caminho_imagem")
                );
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar produto: " + e.getMessage());
        }
        return null;
    }

    // READ - Listar todos os produtos
    public List<Produto> listarTodos() {
        List<Produto> produtos = new ArrayList<>();
        String sql = "SELECT * FROM produto";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                produtos.add(new Produto(
                    rs.getInt("id_produto"),
                    rs.getString("nome_produto"),
                    rs.getDouble("preco"),
                    rs.getInt("quantidade"),
                    rs.getString("caminho_imagem")
                ));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar produtos: " + e.getMessage());
        }
        return produtos;
    }

    // UPDATE - Atualizar produto
    public boolean atualizar(Produto produto) {
        String sql = "UPDATE produto SET nome_produto = ?, preco = ?, quantidade = ?, caminho_imagem = ? WHERE id_produto = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, produto.getNome());
            stmt.setDouble(2, produto.getPreco());
            stmt.setInt(3, produto.getQtd_estoque());
            stmt.setString(4, produto.getcaminhoImagem());
            stmt.setInt(5, produto.getId_produto());

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar produto: " + e.getMessage());
            return false;
        }
    }

    // DELETE - Remover produto
    public boolean deletar(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Erro ao deletar produto: " + e.getMessage());
            return false;
        }
    }
}
