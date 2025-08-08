/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.domain.entities;

/**
 *
 * @author Rafael Martins
 */
public class Iten_Carrinho {
    
    //Citando o tipo produtos aqui , relação undirecional (apenas o carinho sabe de produtos)
    private Produto produto;
    
    //definindo a quantidade comprada de certo produto
    private int qtd_comprada;
    
    
    //declarando construtor 
    //quando for fazer uma compra ele é obrigado a falar quantos produtos comprou
    //Passamos que o contrutor tem que receber um produto ( não os dados , apenas objeto)
    public Iten_Carrinho(Produto produto, int qtd_comprada) {
        this.produto = produto;
        this.qtd_comprada = qtd_comprada;
    }
    
   
    
    
}
