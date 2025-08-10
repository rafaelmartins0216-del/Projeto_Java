/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

//importando a tela principal
import src.presentation.views.Tela_Inicial;

/**
 *
 * @author Rafael Martins
 */
public class Main {

    public static void main(String[] args) {

        //Chama a tela no main
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Tela_Inicial().setVisible(true);
            }
        });
    }
}
