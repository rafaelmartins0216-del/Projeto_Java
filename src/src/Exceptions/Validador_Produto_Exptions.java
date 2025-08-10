/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.Exceptions;

/**
 *
 * @author Rafael Martins
 */
public class Validador_Produto_Exptions {
    //classe criada para tratar exeções
    
    //Usando String pq os dados vem de um Campo Texto
    //Uso de throws IllegalArgumentException para lançar uma validação
    //Personalizada 
    public void ValidarCadastro(String nome, String preco , String qtd_estoque , String url_imagem) throws IllegalArgumentException{
        
        //fazendo verificação de é nulo ou está vazio
        //varificações padrões
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome do produto não pode estar vazio.");
        }
        
        if(!nome.matches("^[a-zA-Z\\s]+$")){
            throw new IllegalArgumentException("Cadastre um nome Válido, apenas letras");
        }
        
        if(nome.trim().length()<3){
            throw new IllegalArgumentException("O nome tem que ter pelo menos 3 letras");
        }
        
 
        if(preco==null || preco.trim().isEmpty()){
            throw new IllegalArgumentException("O preço do produto não pode estar vazio.");
        }
        
        if(qtd_estoque==null || qtd_estoque.trim().isEmpty()){
            throw new IllegalArgumentException("Por favor informe a quantidade em estoque");
        }
        
        //verificando imagem
        if( url_imagem ==null || url_imagem.trim().isEmpty()){
             throw new IllegalArgumentException("É necessário seletionar uma imagem");
        }
        
        
        //Criando variáveis para verificação temporárias
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
            throw new IllegalArgumentException("Insira um dado Valido para o preço");
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
            throw new IllegalArgumentException("Insira um Dado Valido para o estoque");
        }
        
        if(qtd_estoqueTemp<0){
            throw new IllegalArgumentException("O valor dos intens em estoque não pode ser negativo");
        } 
        
    }

    
}
