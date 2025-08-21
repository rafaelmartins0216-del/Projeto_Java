/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package src.presentation.views;

import javax.swing.DefaultListModel;
import java.util.List;

/**
 *
 * @author Rafael Martins
 */
public class Alterações extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Alterações.class.getName());

    // Model dinâmico para a JList (mostra "id - nome")
    private DefaultListModel<String> listaModel = new DefaultListModel<>();
    private Integer produtoSelecionadoId = null;

    public Alterações() {
        initComponents();
        setLocationRelativeTo(null);

        jList1_listaProdutos.setModel(listaModel);
        carregarListaProdutos();

        jToggleButton1_Buscar.addActionListener(e -> filtrarListaProdutos(jTextField1_Buscar.getText()));

        // Ao clicar em um item da lista, preencher os campos da direita
        jList1_listaProdutos.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            String selecionado = jList1_listaProdutos.getSelectedValue();
            if (selecionado == null || selecionado.isBlank()) return;

            // Espera formato "id - nome"
            try {
                String idStr = selecionado.split(" - ", 2)[0].trim();
                int id = Integer.parseInt(idStr);
                produtoSelecionadoId = id;

                var dao = new src.persistence.ProdutoDao();
                var p = dao.buscarPorId(id);  
                
                if (p != null) {
                    jTextField1_Nome.setText(p.getNome());
                    jTextField1_Preco.setText(String.valueOf(p.getPreco()));
                    jTextField1_estoque.setText(String.valueOf(p.getQtd_estoque()));
                    // if (p.getCaminhoImagem() != null) { jLabel6_Imagem_Produto.setIcon(new ImageIcon(p.getCaminhoImagem())); }
                }
            } catch (Exception ex) {
                logger.log(java.util.logging.Level.SEVERE, "Erro ao carregar produto selecionado", ex);
                javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar produto: " + ex.getMessage());
            }
        });

        jButton1_Deletar.addActionListener(e -> {
            if (produtoSelecionadoId == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Selecione um produto na lista primeiro.");
                return;
            }
            int opc = javax.swing.JOptionPane.showConfirmDialog(
                    this,
                    "Tem certeza que deseja deletar o produto ID " + produtoSelecionadoId + "?",
                    "Confirmar exclusão",
                    javax.swing.JOptionPane.YES_NO_OPTION
            );
            if (opc != javax.swing.JOptionPane.YES_OPTION) return;

            try {
                var dao = new src.persistence.ProdutoDao();
                boolean ok = dao.deletar(produtoSelecionadoId);
                if (ok) {
                    javax.swing.JOptionPane.showMessageDialog(this, "Produto deletado com sucesso!");
                    produtoSelecionadoId = null;
                    limparCampos();
                    carregarListaProdutos();
                } else {
                    javax.swing.JOptionPane.showMessageDialog(this, "Não foi possível deletar.");
                }
            } catch (Exception ex) {
                javax.swing.JOptionPane.showMessageDialog(this, "Erro ao deletar: " + ex.getMessage());
            }
        });
    }

    // Lista todos os produtos do banco e joga no model
    private void carregarListaProdutos() {
        listaModel.clear();
        try {
            var dao = new src.persistence.ProdutoDao();
            List<src.domain.entities.Produto> produtos = dao.listarTodos(); // ← implemente no ProdutoDao caso não exista
            for (var p : produtos) {
                // Mostra "id - nome" na lista
                listaModel.addElement(p.getId_produto() + " - " + p.getNome());
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao carregar produtos", e);
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao carregar produtos: " + e.getMessage());
        }
    }

    // Busca por nome parcial; se termo vazio, lista tudo
    private void filtrarListaProdutos(String termo) {
        listaModel.clear();
        try {
            var dao = new src.persistence.ProdutoDao();
            List<src.domain.entities.Produto> produtos =
                    (termo == null || termo.isBlank())
                            ? dao.listarTodos()
                            : dao.buscarPorNome(termo);

            for (var p : produtos) {
                listaModel.addElement(p.getId_produto() + " - " + p.getNome());
            }
        } catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao buscar produtos", e);
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao buscar: " + e.getMessage());
        }
    }

    private void limparCampos() {
        jTextField1_Nome.setText("");
        jTextField1_Preco.setText("");
        jTextField1_estoque.setText("");
        jLabel6_Imagem_Produto.setIcon(null);
        jList1_listaProdutos.clearSelection();
    }

    /**
     * Creates new form Alterações
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jButton1_Concluir_alteracoes = new javax.swing.JButton();
        jButton1_Deletar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jTextField1_Nome = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField1_Preco = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jTextField1_estoque = new javax.swing.JTextField();
        jLabel6_Imagem_Produto = new javax.swing.JLabel();
        jButton1_selecionar_Imagem = new javax.swing.JButton();
        jTextField1_Buscar = new javax.swing.JTextField();
        jToggleButton1_Buscar = new javax.swing.JToggleButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1_listaProdutos = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Alterações");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel1.setText("Produto que deseja buscar:");

        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));

        jButton1_Concluir_alteracoes.setBackground(new java.awt.Color(102, 255, 51));
        jButton1_Concluir_alteracoes.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton1_Concluir_alteracoes.setForeground(new java.awt.Color(0, 0, 0));
        jButton1_Concluir_alteracoes.setText("Concluir Alterações");
        jButton1_Concluir_alteracoes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jButton1_Deletar.setBackground(new java.awt.Color(204, 0, 0));
        jButton1_Deletar.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jButton1_Deletar.setForeground(new java.awt.Color(0, 0, 0));
        jButton1_Deletar.setText("Deletar Produto");
        jButton1_Deletar.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel2.setText("Nome:");

        jTextField1_Nome.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jLabel3.setText("Preço:");

        jTextField1_Preco.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jLabel4.setText("Quantidade em Estoque:");

        jTextField1_estoque.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jLabel6_Imagem_Produto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Imagem", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.DEFAULT_POSITION));

        jButton1_selecionar_Imagem.setText("Selecionar Imagem...");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1_Nome))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1_Preco, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jButton1_selecionar_Imagem, javax.swing.GroupLayout.PREFERRED_SIZE, 214, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jTextField1_estoque)))
                            .addComponent(jLabel6_Imagem_Produto, javax.swing.GroupLayout.PREFERRED_SIZE, 314, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 52, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jButton1_Deletar, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1_Concluir_alteracoes, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jTextField1_Nome, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(7, 7, 7)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1_Preco, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1_estoque, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6_Imagem_Produto, javax.swing.GroupLayout.DEFAULT_SIZE, 207, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1_selecionar_Imagem)
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1_Deletar, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1_Concluir_alteracoes, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jTextField1_Buscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jTextField1_Buscar.setToolTipText("Digite o nome do Produto");

        jToggleButton1_Buscar.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jToggleButton1_Buscar.setText("Buscar");

        jScrollPane1.setViewportView(jList1_listaProdutos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jTextField1_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 269, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jToggleButton1_Buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(25, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextField1_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
                    .addComponent(jToggleButton1_Buscar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Alterações().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1_Concluir_alteracoes;
    private javax.swing.JButton jButton1_Deletar;
    private javax.swing.JButton jButton1_selecionar_Imagem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6_Imagem_Produto;
    private javax.swing.JList<String> jList1_listaProdutos;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1_Buscar;
    private javax.swing.JTextField jTextField1_Nome;
    private javax.swing.JTextField jTextField1_Preco;
    private javax.swing.JTextField jTextField1_estoque;
    private javax.swing.JToggleButton jToggleButton1_Buscar;
    // End of variables declaration//GEN-END:variables
}
