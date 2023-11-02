/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servidor;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import javax.swing.JFrame;

/**
 *
 * @author cayet
 */
public class Servidor {

    public static void main(String[] args) throws IOException {
        
        final Integer MAX_CLIENTES = 4;
        final int MITAD_PANTALLA = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/2)-150;
        final int TIEMPO = 90;
        final int PUERTO = 90;
        
        Socket[] clientes = {null, null, null, null};
        Integer[] coordenadasPuntos = {MITAD_PANTALLA, MITAD_PANTALLA, MITAD_PANTALLA, MITAD_PANTALLA, 0, 0, 0, 0, 100};
        
        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        
        long startTime = System.currentTimeMillis();
        long duration = TIEMPO * 1000; // 90 segundos en milisegundos

        executor.scheduleWithFixedDelay(() -> {
            long currentTime = System.currentTimeMillis();
            long elapsed = currentTime - startTime;
            long remaining = Math.max(duration - elapsed, 0);
            
            // Actualizar la última posición del array con los segundos restantes
            coordenadasPuntos[coordenadasPuntos.length - 1] = (int) (remaining / 1000);
            
            if(coordenadasPuntos[coordenadasPuntos.length - 1]==((TIEMPO/3)*2)-1) {
                
                for (int i = 0; i < coordenadasPuntos.length; i++) {
                    
                    if(i<clientes.length) coordenadasPuntos[i] = MITAD_PANTALLA;
                    
                    else if(i!=coordenadasPuntos.length-1) coordenadasPuntos[i] = 0;
                    
                }
                
            }
            
        }, 0, 1, TimeUnit.SECONDS);

        try (ServerSocket server = new ServerSocket(PUERTO);) {
            boolean finalizar = true;
            while (finalizar) {
                for (int i = 0; i < clientes.length; i++) {
                    if(clientes[i]==null) {
                        clientes[i] = server.accept();    
                        System.out.println("Cliente conectado");
                        coordenadasPuntos[i] = MITAD_PANTALLA ;//Mitad pantalla.
                        HiloServidor hs = new HiloServidor(clientes[i],clientes,coordenadasPuntos);
                        hs.start();
                    }
                }
                if(coordenadasPuntos[coordenadasPuntos.length-1]==0) finalizar=false; 
            }

        }
            
        catch(Exception e){
            System.out.println("Se ha liado.");
        }
        
    }
}
