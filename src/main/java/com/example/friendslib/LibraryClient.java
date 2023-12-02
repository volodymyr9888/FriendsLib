package com.example.friendslib;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface LibraryClient extends Remote {
    boolean registerUser(String username, String password) throws RemoteException;
    boolean loginUser(String username, String password) throws RemoteException;

    boolean addBook(String bookTitle, String author) throws RemoteException;
    List<Book> getAllBooks() throws RemoteException;

    boolean addLibraryAdmin(String username, String password) throws RemoteException;
    boolean deleteBook(String bookTitle) throws RemoteException;
}
