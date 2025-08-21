package src.domain.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Venda {
    private Integer id;
    private LocalDateTime dataHora;
    private double desconto;
    private String formaPagamento;
    private String observacao;
    private List<ItemVenda> itens = new ArrayList<>();

    // getters/setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }
    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }
    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }
    public String getFormaPagamento() { return formaPagamento; }
    public void setFormaPagamento(String formaPagamento) { this.formaPagamento = formaPagamento; }
    public String getObservacao() { return observacao; }
    public void setObservacao(String observacao) { this.observacao = observacao; }
    public List<ItemVenda> getItens() { return itens; }
    public void setItens(List<ItemVenda> itens) { this.itens = itens; }

    public double getTotalBruto() {
        return itens.stream().mapToDouble(ItemVenda::getSubtotal).sum();
    }
    public double getTotalLiquido() {
        return Math.max(0, getTotalBruto() - desconto);
    }
}
