/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author cayet
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        
        final Integer MAX_CLIENTES = 4;
        ArrayList<Socket> clientes = new ArrayList<>();
        ArrayList<Integer> coordenadas = new ArrayList<>();
        
        try(ServerSocket server = new ServerSocket(90)){
            
            while (clientes.size() <= MAX_CLIENTES) {                
                Socket cliente = server.accept();
                System.out.println("Cliente conectado");
                clientes.add(cliente);
                coordenadas.add(170);//Mitadad pantalla.
                HiloServidor hs = new HiloServidor(cliente,clientes,coordenadas);
                hs.start();
            }
            
        }
        catch(Exception e){
            System.out.println("Se ha liado.");
        }
        
    }
}
