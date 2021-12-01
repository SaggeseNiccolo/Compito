package com.itismeucci;

import java.io.*;
import java.net.*;

public class Server {
    ServerSocket server;
    Socket client;
    String receivedString;
    String modifiedString;
    BufferedReader inFromClient;
    DataOutputStream outToClient;

    public Socket attendi() {
        try {
            System.out.println("1 SERVER partito in esecuzione ...");
            server = new ServerSocket(6789);
            client = server.accept();
            server.close();
            inFromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
            outToClient = new DataOutputStream(client.getOutputStream());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante l'istanza del server !");
            System.exit(1);
        }
        return client;
    }

    public void comunica() {
        try {
            System.out.println("3 benvenuto client, scrivi una frase e la trasformo in maisucolo. Attendo ...");
            receivedString = inFromClient.readLine();
            System.out.println("6 ricevuta la stringa dal client : " + receivedString);
            modifiedString = receivedString.toUpperCase();
            System.out.println("7 invio la stringa modificata al client ...");
            outToClient.writeBytes(modifiedString + '\n');
            System.out.println("9 SERVER: fine elaborazione ... buona notte!");
            client.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Errore durante la comunicazione col client!");
            System.exit(1);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.attendi();
        server.comunica();
    }
}