package com.company.rmi;
import java.rmi.*;
public class Main {


        public static void main(String args[])
        {
            String answer,value="Hi";
            try
            {
                // lookup method to find reference of remote object
                Search access =
                        (Search)Naming.lookup("rmi://localhost:1900"+
                                "/pranay");
                answer = access.query(value);
                System.out.println("Client:"+value);
                System.out.println("Server Method Invocation Response:"+answer);
            }
            catch(Exception ae)
            {
                System.out.println(ae);
            }
        }

}
