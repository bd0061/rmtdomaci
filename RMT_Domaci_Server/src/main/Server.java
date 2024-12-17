/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
import perzistentnost.DataBroker;
/**
 *
 * @author Djurkovic
 */
public class Server {
    private int port;
    private ExecutorService executor;
    private DataBroker db;
    private final Set<Integer> registeredUserIds = Collections.newSetFromMap(new ConcurrentHashMap<>());
    public Server(int port) {
        this.port = port;

    }

    
    public void shutdown() {
        shutdown(0);
    }
    
    public void shutdown(int code) {
        System.out.println("Gasim server...");
        if(executor != null)
            executor.shutdown();
        System.out.println("Server ugasen.");
        System.exit(code);
    }
    
    
    public void start() {
        System.out.println("Pripremam port za osluskivanje..");
        try(ServerSocket socket = new ServerSocket(port))
        {
            System.out.println("Pripremam konekciju sa bazom");
            db = new DataBroker();
            System.out.println("Uspesna konekcija sa bazom");
            executor = Executors.newCachedThreadPool();
            System.out.println("Server inicijalizovan, krecem da osluskujem za konekcije...");
            while(true) {
                Socket clientSocket = socket.accept();
                System.out.println("Uspostavljena nova konekcija sa klijentom [" + clientSocket.getInetAddress() + "], pocinjem procesiranje zahteva...");
                
                executor.submit(new ClientHandler(clientSocket,db,registeredUserIds));
            }
        } catch (IOException ex) {
            System.out.println("Server je naisao na neoporavljivu gresku: [" + ex + "], kraj rada...");
            shutdown(1);
        } catch (SQLException ex) {
            System.out.println("Server ne moze da se poveze sa bazom podataka, kraj rada. [" + ex + "]");
            shutdown(1);
        }
    }  
}
