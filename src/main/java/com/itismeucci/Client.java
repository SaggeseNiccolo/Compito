package com.itismeucci;

import java.io.*;
import java.net.*;

public class Client {
    String serverName = "localhost";
    int serverPort = 6789;
    Socket mysocket;
    BufferedReader keyboard;
    String userString;
    DataOutputStream out;
    BufferedReader in;
    ClientListener listener;

    public Socket connetti() {
        System.out.println("Client partito");
        try {
            keyboard = new BufferedReader(new InputStreamReader(System.in));
            mysocket = new Socket(serverName, serverPort);
            out = new DataOutputStream(mysocket.getOutputStream());
            in = new BufferedReader(new InputStreamReader(mysocket.getInputStream()));
            listener = new ClientListener(mysocket);
        } catch (UnknownHostException e) {
            System.err.println("Host sconosciuto");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la connessione!");
            System.exit(1);
        }
        return mysocket;
    }

    public void comunica() {
        try {
            listener.start();
            for (;;) {
                userString = keyboard.readLine();
                out.writeBytes(userString + "\n");
                if (userString.equals("FINE")) {
                    break;
                }
            }
            mysocket.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col server!");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connetti();
        client.comunica();
    }
}