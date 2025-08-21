/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package src.presentation.views;

/**
 *
 * @author Rafael Martins
 */
public class Vendas extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Vendas.class.getName());

    /**
     * Creates new form Alterações
     */
    
 // ===== Campos da tela de Vendas =====
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtQuantidade;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtObs;
    private javax.swing.JComboBox<String> cmbFormaPg;
    private javax.swing.JTable tblCarrinho;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnFinalizar;

    private javax.swing.table.DefaultTableModel carrinhoModel;
    // Lista do carrinho
    private java.util.List<src.domain.entities.ItemVenda> carrinhoItens = new java.util.ArrayList<>();

    public Vendas() {
        initComponents();
        setLocationRelativeTo(null);

    }

    private void montarUIVendas() {
        // Componentes
        txtCodigo = new javax.swing.JTextField(8);
        txtQuantidade = new javax.swing.JTextField(5);
        txtDesconto = new javax.swing.JTextField(6);
        txtObs = new javax.swing.JTextField(20);
        cmbFormaPg = new javax.swing.JComboBox<>(new String[]{"Dinheiro", "Crédito", "Débito", "PIX"});
        btnAdd = new javax.swing.JButton("Adicionar");
        btnFinalizar = new javax.swing.JButton("Finalizar venda");
        lblTotal = new javax.swing.JLabel("Total: 0,00");

        // Tabela do carrinho
        carrinhoModel = new javax.swing.table.DefaultTableModel(
            new Object[]{"ID", "Produto", "Qtd", "Preço", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tblCarrinho = new javax.swing.JTable(carrinhoModel);

        // Layout simples usando BoxLayout dentro do jPanel1
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        // Linha 1: código + qtd + add
        javax.swing.JPanel linha1 = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        linha1.add(new javax.swing.JLabel("Cód produto:"));
        linha1.add(txtCodigo);
        linha1.add(new javax.swing.JLabel("Qtd:"));
        linha1.add(txtQuantidade);
        linha1.add(btnAdd);

        // Linha 2: tabela
        javax.swing.JScrollPane scroll = new javax.swing.JScrollPane(tblCarrinho);

        // Linha 3: desconto, formaPg, obs, total, finalizar
        javax.swing.JPanel linha3 = new javax.swing.JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        linha3.add(new javax.swing.JLabel("Desconto:"));
        linha3.add(txtDesconto);
        linha3.add(new javax.swing.JLabel("Forma pag.:"));
        linha3.add(cmbFormaPg);
        linha3.add(new javax.swing.JLabel("Obs.:"));
        linha3.add(txtObs);
        linha3.add(lblTotal);
        linha3.add(btnFinalizar);

        jPanel1.add(linha1);
        jPanel1.add(scroll);
        jPanel1.add(linha3);

        // Ações
        btnAdd.addActionListener(e -> adicionarItemAoCarrinho());
        btnFinalizar.addActionListener(e -> finalizarVenda());
    }

    private void adicionarItemAoCarrinho() {
        try {
            int idProduto = Integer.parseInt(txtCodigo.getText().trim());
            int qtd = Integer.parseInt(txtQuantidade.getText().trim());

            if (qtd <= 0) throw new IllegalArgumentException("Quantidade deve ser > 0");

            // Carrega produto pelo DAO
            src.persistence.ProdutoDao pdao = new src.persistence.ProdutoDao();
            src.domain.entities.Produto p = pdao.buscarPorId(idProduto); 

            if (p == null) {
                javax.swing.JOptionPane.showMessageDialog(this, "Produto não encontrado.");
                return;
            }
            
            if (p.getQtd_estoque() < qtd) {
                javax.swing.JOptionPane.showMessageDialog(this, "Estoque insuficiente. Em estoque: " + p.getQtd_estoque());
                return;
            }
            
            double preco = p.getPreco();

            // Monta ItemVenda
            src.domain.entities.ItemVenda it = new src.domain.entities.ItemVenda();
            it.setIdProduto(idProduto);
            it.setNomeProduto(p.getNome());
            it.setQuantidade(qtd);
            it.setPrecoUnitario(preco);

            carrinhoItens.add(it);
            carrinhoModel.addRow(new Object[]{
                    it.getIdProduto(), it.getNomeProduto(), it.getQuantidade(),
                    String.format("%.2f", it.getPrecoUnitario()),
                    String.format("%.2f", it.getSubtotal())
            });

            atualizarTotal();
            txtCodigo.setText(""); txtQuantidade.setText("");
            txtCodigo.requestFocus();
        } catch (NumberFormatException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Informe código e quantidade válidos.");
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao adicionar item", ex);
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao adicionar item: " + ex.getMessage());
        }
    }

    private void atualizarTotal() {
        double total = carrinhoItens.stream().mapToDouble(src.domain.entities.ItemVenda::getSubtotal).sum();
        double desconto = 0.0;
        try { if (!txtDesconto.getText().isBlank()) desconto = Double.parseDouble(txtDesconto.getText().trim()); } catch (Exception ignored) {}
        double liquido = Math.max(0, total - desconto);
        lblTotal.setText(String.format("Total: %.2f", liquido));
    }

    private void finalizarVenda() {
        if (carrinhoItens.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(this, "Carrinho vazio.");
            return;
        }
        try {
        	src.domain.entities.Venda venda = new src.domain.entities.Venda();
        	venda.setFormaPagamento(String.valueOf(cmbFormaPg.getSelectedItem()));
        	try { venda.setDesconto(Double.parseDouble(txtDesconto.getText().trim())); } catch (Exception ignored) {}
        	venda.setObservacao(txtObs.getText());
        	venda.setItens(new java.util.ArrayList<>(carrinhoItens));

            int idGerado = new src.persistence.VendaDao().finalizarVenda(venda);

            javax.swing.JOptionPane.showMessageDialog(this, "Venda finalizada! Nº " + idGerado);

            // Limpa UI
            carrinhoItens.clear();
            carrinhoModel.setRowCount(0);
            txtDesconto.setText("");
            txtObs.setText("");
            atualizarTotal();
        } catch (Exception ex) {
            logger.log(java.util.logging.Level.SEVERE, "Erro ao finalizar venda", ex);
            javax.swing.JOptionPane.showMessageDialog(this, "Erro ao finalizar: " + ex.getMessage());
        }
    }
	
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Vendas");
        setPreferredSize(new java.awt.Dimension(800, 600));

        jPanel1.setBorder(javax.swing.BorderFactory.createCompoundBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED)));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 761, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 464, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        montarUIVendas();
    
        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
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
        java.awt.EventQueue.invokeLater(() -> new Vendas().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}
