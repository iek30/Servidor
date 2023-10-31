/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

/**
 *
 * @author cayet
 */
public class HiloServidor extends Thread{
    
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private Socket[] clientes;
    private Integer[] coordenadas;
    final int mitadPantalla = (java.awt.Toolkit.getDefaultToolkit().getScreenSize().width/2)-150;

    public HiloServidor(Socket cliente, Socket[] clientes, Integer[] coordenadas) throws IOException {
        this.socket = cliente;
        this.input = new DataInputStream(socket.getInputStream());
        this.output = new DataOutputStream(socket.getOutputStream());
        this.clientes = clientes;
        this.coordenadas = coordenadas;
    }
    
    public void run(){
        
        while (true) {
            try {
                String mensaje = input.readUTF();
                
                for (int i = 0; i < clientes.length; i++) {
                    if(clientes[i]!=null) {
                        if (clientes[i].equals(socket)) {
                        coordenadas[i] = Integer.parseInt(mensaje);
                        enviarMensajeATodos();
                        }
                    }
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
                for (int i = 0; i < clientes.length; i++) {
                    if(clientes[i]!=null) {
                    
                        if (clientes[i].equals(socket)) {

                            clientes[i] = null;
                            coordenadas[i] = mitadPantalla;

                        }
                    }
                    
                }
                
                this.stop();
                
                }
        }
    }
    
    private void enviarMensajeATodos() throws IOException {
        
        StringBuilder coordenadasStrBuilder = new StringBuilder();
        for (Integer coordenada : coordenadas) {
            coordenadasStrBuilder.append(coordenada);
            coordenadasStrBuilder.append(",");
        }

        String coordenadasStr = coordenadasStrBuilder.toString();

        // Elimina la coma final si es necesario
        if (coordenadasStr.endsWith(",")) {
            coordenadasStr = coordenadasStr.substring(0, coordenadasStr.length() - 1);
        }
        for (Socket cliente : clientes) {
            if(cliente!=null) {
            output = new DataOutputStream(cliente.getOutputStream());
            output.writeUTF(coordenadasStr);
            }
        }
    }
    
}
