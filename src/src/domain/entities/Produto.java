/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.domain.entities;

import java.time.LocalDateTime;

/**
 *
 * @author Rafael Martins
 */
public class Produto {
    
    
    //declarando os produtos presente na biscoitaria
    private String nome;
    private double preco;
    private int qtd_estoque;
    private LocalDateTime dataHora; //para ver a que horas foi vendido , para o relatorio
    
    //Adicionando campo de imagem aqui
    private String caminhoImagem;
        
        //vou colocar os codigo dela abaixo getts and setts
    
            public String getcaminhoImagem(){
                return caminhoImagem;
            }
            
            
            public void setcaminhoImagem(String caminhoImagem){
                this.caminhoImagem=caminhoImagem;
            }
    
    
    //Toda vez que declarar um novo produto vai ser obrigado a passar essas médias 
    //criando construtor para adicionar um produto do zero
    //Lembrar criar exeções para lidar com erros ao inserir dados 
    public Produto(String nome, double preco, int qtd_estoque ,LocalDateTime dataHora, String caminhoImagem){
        this.nome=nome;
        this.preco=preco;
        this.qtd_estoque=qtd_estoque;
        this.dataHora=LocalDateTime.now(); //pega a hora exata da venda
        this.caminhoImagem=caminhoImagem;  //quando formor declarar um produto temos que passar o caminho da imagem
    }
    
    
    //Criando método get e set
    //get para retornar o produto
    //set para "E se o produto ja existir e quiser mudar o preço ou alterar o estoque"

    //Nome
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //preço
    public double getPreco() {
        return preco;
    }
    public void setPreco(double preco) {
        this.preco = preco;
    }

    //Estoque-
    public int getQtd_estoque() {
        return qtd_estoque;
    }
    public void setQtd_estoque(int qtd_estoque) {
        this.qtd_estoque = qtd_estoque;
    }

    //Hora da venda
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }
    
      
}
