/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src.domain.usecases;
import src.domain.entities.Carrinho;
import java.time.Duration;
import java.time.LocalDateTime;

/**
 *
 * @author Rafael Martins
 */
public class Resetar_id_Carrinho {
    
    //duração do dia para computar (peguei o codigo do git hub)
    //precisa importar as classes para funcionar 
    public static void executar() {
        LocalDateTime resetarT = Carrinho.getResetarT(); // usando getter
        Duration duracao = Duration.between(resetarT, LocalDateTime.now());

        if (duracao.toHours() >= 24) {
            Carrinho.setContador(0);
            Carrinho.setResetarT(LocalDateTime.now());
        }
    }
}
