package com.example.friendslib;

// LibraryServerImpl.java
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryServerImpl extends UnicastRemoteObject implements LibraryServer {
    private Map<String, String> users; // username, password
    private Map<String, List<Book>> userBooks;
    private List<Book> allBooks;

    public LibraryServerImpl() throws RemoteException {
        super();
        users = new HashMap<>();
        userBooks = new HashMap<>();
        allBooks = new ArrayList<>();
    }

    @Override
    public boolean registerUser(String username, String password) throws RemoteException {
        System.out.println("I AM HERE = " + getClass().getName());
        return false;
    }

    @Override
    public boolean loginUser(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean addBook(String username, String bookTitle, String author) throws RemoteException {
        return false;
    }

    @Override
    public List<Book> getAllBooks() throws RemoteException {
        return null;
    }

    @Override
    public boolean addLibraryAdmin(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean deleteBook(String bookTitle) throws RemoteException {
        return false;
    }

    // Implement methods from LibraryServer interface
}
