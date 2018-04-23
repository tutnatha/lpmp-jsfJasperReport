/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.kodejava.example.net;

/**
 *
 * @author myssd
 */
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PortScanner {
    public PortScanner(){
        String host = "207.148.66.201";
        InetAddress inetAddress = null;
        try {
            inetAddress = InetAddress.getByName(host);
        } catch (UnknownHostException ex) {
            Logger.getLogger(PortScanner.class.getName()).log(Level.SEVERE, null, ex);
        }

        String hostName = inetAddress.getHostName();
//        for (int port = 0; port <= 65535; port++) { //walah ini luama ini..
          for (int port = 8080; port <= 8090; port++) { //tebak kira2in aja..!!!
            try {
                Socket socket = new Socket(hostName, port);
                String text = hostName + " is listening on port " + port;
                System.out.println(text);
                socket.close();
            } catch (IOException e) {
                String s = hostName + " is not listening on port " + port;
                System.out.println(s);
            }
        }    
    }
    
    public static void main(String[] args) throws Exception {
        PortScanner x = new PortScanner();
    }    
}
