/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.shared;

//import da classe Produto para validação direta dos dados
import src.domain.entities.Produto;
        


/**
 *
 * @author Rafael Martins
 */
public class Cadastros_Exption {
    //classe criada para tratar exeções
    
    //Usando String pq os dados vem de um Campo Texto
    //Uso de throws IllegalArgumentException para lançar uma validação
    //Personalizada 
    public void ValidarCadastro(String nome, String preco , String qtd_estoque) throws IllegalArgumentException{
        
        //verificando Nome 
        //se a verificação falhar ele lança o Erro
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
        }
        
        //Criando variáveis para verificação
        double precoTemp;
        int qtd_estoqueTemp;
        
        //Verificando se podemos passsar o preço para valor numero
        //String -> Numero 
        //verificando se conseguimos converter de String para int
        //Transformando em Primitivo
        try {
            //Tentando converter a String numerica para numeral
            precoTemp=Double.parseDouble(preco);
        } catch (Exception e) {
            throw new IllegalArgumentException("Insira um dado Valido");
        }
        
        //Verificando se ele é negativo agora
        if (precoTemp<=0){
             throw new IllegalArgumentException("O valor do produto não pode ser negativo");
        }
        
        //fazendo a mesma coisa com o Estoque 
        //verificando se conseguimos converter de String para int
        //Transformando em Primitivo
        try {
            qtd_estoqueTemp=Integer.parseInt(qtd_estoque);
        } catch (Exception e) {
            throw new IllegalArgumentException("Insira um Dado Valido");
        }
        
        if(qtd_estoqueTemp<0){
            throw new IllegalArgumentException("O estoque não pode ser negativo");
        }
        
        
        //verificando imagem (se ela foi inserida)
        
    }

    
}
