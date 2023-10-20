package com.company.rmi;

import java.rmi.*;
import java.rmi.registry.*;
public class SearchServer
{
    public static void main(String args[])
    {
        try
        {
            // Create an object of the interface
            // implementation class
            Search obj = new SearchQuery();

            // rmiregistry within the server JVM with
            // port number 1900
            LocateRegistry.createRegistry(6969);

            // Binds the remote object by the name
            // pranay
            Naming.rebind("rmi://localhost:6969"+
                    "/pranay",obj);
            System.out.println("Server started at 6969..");
        }
        catch(Exception ae)
        {
            System.out.println(ae);
        }
    }
}