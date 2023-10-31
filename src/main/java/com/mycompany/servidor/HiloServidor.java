/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.servidor;;
import java.util.stream.Collectors;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.util.ArrayList;

/**
 *
 * @author cayet
 */
public class HiloServidor extends Thread{
    
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private ArrayList<Socket> clientes;
    private ArrayList<Integer> coordenadas;

    public HiloServidor(Socket cliente, ArrayList<Socket> clientes, ArrayList<Integer> coordenadas) throws IOException {
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
                
                for (int i = 0; i < clientes.size(); i++) {
                    if (clientes.get(i).equals(socket)) {
                        coordenadas.set(i, Integer.parseInt(mensaje));
                        enviarMensajeATodos();
                    }
                }
                
            } catch (IOException ex) {
                Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    private void enviarMensajeATodos() throws IOException {
        
        String coordenadasStr = String.join(",", coordenadas.stream().map(Object::toString).collect(Collectors.toList()));
        for (Socket cliente : clientes) {
            output = new DataOutputStream(cliente.getOutputStream());
            output.writeUTF(coordenadasStr);
        }
    }
    
}
