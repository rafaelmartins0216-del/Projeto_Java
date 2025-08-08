/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.domain.entities;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;


/**
 *
 * @author Rafael Martins
 */
public class Carrinho {
    
    //criando um contador para não ter que passar o id carrinho (contador global)
    private static int contador=0;
    
    private static LocalDateTime ResetarT = LocalDateTime.now();
    
    
    //criando o id do carrinho para localizar o carrinho e suas compras
    private int id_Carrinho;
    
    //Lista de Itens no carrinho 
    //Um carrinho pode conter vários itens (Por isso a lista)
    private ArrayList<Iten_Carrinho> iten_carrinho;
    
    
    //Criando um construtor
    //toda vez que chamar um carrinho temos que passar os itens no carrinho 
    public Carrinho(Iten_Carrinho item_carrinho ){
        //quero fazer com que o programa não peça o id do carrinho (gerar automatico)
        //toda vez que iniciarmos um carrinho ele vai receber um id sequencial automaticamente 
        this.id_Carrinho=++contador;
        
        this.iten_carrinho=iten_carrinho;
    }
    
    //adicionando gets and setter para ussar nos casos de uso
     public static int getContador() {
        return contador;
    }

    public static void setContador(int contador) {
        Carrinho.contador = contador;
    }

    public static LocalDateTime getResetarT() {
        return ResetarT;
    }

    public static void setResetarT(LocalDateTime ResetarT) {
        Carrinho.ResetarT = ResetarT;
    }

    public int getId_Carrinho() {
        return id_Carrinho;
    }

    public void setId_Carrinho(int id_Carrinho) {
        this.id_Carrinho = id_Carrinho;
    }
    
    //Getts e Setts dos Itens Carrinhos (usaremos isso daqui para passar os dados 
    //E adicionar um Carrinho com itens)
    public ArrayList<Iten_Carrinho> getIten_carrinho() {
        return iten_carrinho;
    }

    public void setIten_carrinho(ArrayList<Iten_Carrinho> iten_carrinho) {
        this.iten_carrinho = iten_carrinho;
    }

    
}
