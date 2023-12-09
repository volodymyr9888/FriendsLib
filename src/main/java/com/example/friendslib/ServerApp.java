package com.example.friendslib;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class ServerApp {
    private static final int PORT = 12345; // Choose a port number

    public static void main(String[] args) {
        try {
            LibraryServer libraryServer = new LibraryServerImpl();
            // Start RMI server
            LocateRegistry.createRegistry(1098);
            Naming.rebind("rmi://localhost:1098/LibraryServer", libraryServer);
            System.out.println("RMI Server is running...");

            // Start a separate thread to handle client connections
            new Thread(ServerApp::handleClientConnections).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleClientConnections() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                // Handle each client connection in a separate thread
                new Thread(() -> handleClient(clientSocket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream())
        ) {
            // Example: Send a welcome message to the client
            out.writeObject("Welcome to the server!");

            // Example: Receive data from the client
            Object receivedData = in.readObject();
            System.out.println("Server: Received from client: " + receivedData);

            // Handle the received data (you can implement your logic here)
            // For example, you can pass the received data to your library server
            // libraryServer.processData(receivedData);

            // Example: Send data back to the client
            String responseData = "Hello, client! I received your data: " + receivedData;
            out.writeObject(responseData);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}