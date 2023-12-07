package com.company.rmi;

import java.rmi.*;
public interface Search extends Remote
{
    // Declaring the method prototype
    String query(String search) throws RemoteException;
}