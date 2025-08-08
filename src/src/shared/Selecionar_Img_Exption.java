/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.shared;

//importando a classe prodeuto
import src.domain.entities.Produto;
/**
 *
 * @author Rafael Martins
 */
public class Selecionar_Img_Exption {
    
    
    public void validarImagem(String url_imagem) throws IllegalArgumentException {
        
        //verificando se a imagem é valida
        if( url_imagem ==null || url_imagem.trim().isEmpty()){
             throw new IllegalArgumentException("E necessário selecionar uma imagem para cadastrar o produto");
        }
    }
    
    
}
