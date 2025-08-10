/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.domain.usecases;


//importe da classe Produto para passar a variável 
import javax.swing.JOptionPane;
import src.domain.entities.Produto;
import src.persistence.ProdutoDao;
import src.Exceptions.Validador_Produto_Exptions;


/**
 *
 * @author Rafael Martins
 */

//Mandar para o banco de dados 
//a classe do tipo produto , ela retorna um produto se der certo ou um nullo para indicar falha 
public class CadastrarProduto {
    
     Validador_Produto_Exptions validar=new  Validador_Produto_Exptions();
  
    //Método para cadastrar os produtos 
    public void Cadastros(String nome, String preco, String qtd_estoque, String url_imagem){
        
        try {
            validar.ValidarCadastro(nome, preco, qtd_estoque, url_imagem);
             //convertendo os dados que precisam ser convertidos
            //variáveis temporárias so existem quando chamo a função
            double precoTemp=Double.parseDouble(preco);
            int qtd_estoqueTemp=Integer.parseInt(qtd_estoque);
               
            Produto produto = new Produto(nome, precoTemp, qtd_estoqueTemp, url_imagem);
            
            //Depois de instanciar o produto , passa ele para o banco com Produtodao
            ProdutoDao produtoDao=new ProdutoDao();
            produtoDao.salvar(produto);
       
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ;
        }
          
        
    }
    
   
}