package com.example.friendslib;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

// ServerApp.java
public class ServerApp {
    public static void main(String[] args) {
        try {
            LibraryServer libraryServer = new LibraryServerImpl();
            LocateRegistry.createRegistry(1098);
            Naming.rebind("rmi://localhost:1098/LibraryServer", libraryServer);
            System.out.println("Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
