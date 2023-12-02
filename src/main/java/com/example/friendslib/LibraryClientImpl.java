package com.example.friendslib;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.List;

public class LibraryClientImpl implements LibraryClient {
    private LibraryServer libraryServer;

    public LibraryClientImpl() {
        try {
            libraryServer = (LibraryServer) Naming.lookup("rmi://localhost:1098/LibraryServer");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean registerUser(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean loginUser(String username, String password) throws RemoteException {
        return false;
    }

    @Override
    public boolean addBook(String bookTitle, String author) throws RemoteException {
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

    // Implement methods from LibraryClient interface
}
