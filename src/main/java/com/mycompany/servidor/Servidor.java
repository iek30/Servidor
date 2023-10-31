/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;

/**
 *
 * @author cayet
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        
        final Integer MAX_CLIENTES = 4;
        final int mitadPantalla = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/2)-150;
        
        Socket[] clientes = {null, null, null, null};
        Integer[] coordenadas = {mitadPantalla, mitadPantalla, mitadPantalla, mitadPantalla};
        
        try(ServerSocket server = new ServerSocket(90);)
            {
            
            while (true) {
                for (int i = 0; i < clientes.length; i++) {
                    if(clientes[i]==null) {
                        clientes[i] = server.accept();    
                        System.out.println("Cliente conectado");
                        coordenadas[i] = mitadPantalla ;//Mitad pantalla.
                        HiloServidor hs = new HiloServidor(clientes[i],clientes,coordenadas);
                        hs.start();
                    }
                }
                    
                    
                    
                }
                
            }
            
        catch(Exception e){
            System.out.println("Se ha liado.");
        }
        
    }
}
