package com.company.chatserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
    //To Store users name and clients to broadcast and log purpose
    ArrayList<String> users = new ArrayList<String>();
    ArrayList<HandleClient> clients = new ArrayList<HandleClient>();

    //Code to start server and accept clients
    public void startServer() throws IOException {
        ServerSocket soc = new ServerSocket(6969);
        System.out.println("Server started at PORT:6969");
        while(true){
            Socket newClient = soc.accept();
            HandleClient handleClient = new HandleClient(newClient);
            clients.add(handleClient);



        }
    }

    //Main
    public static void main(String[] args) throws IOException {
       new ChatServer().startServer();

    }

    //Broadcasting msg to every connect client
    public void broadcast(String user, String message)  {
        // send message to all connected users
        for ( HandleClient c : clients )
            if ( ! c.name.equals(user) )
                c.sendMessage(user,message);
    }

    //Handling Each Client on diff Thread
    class HandleClient extends Thread{
        BufferedReader input;
        String name;
        PrintWriter output;
        public HandleClient(Socket soc) throws IOException{
            input = new BufferedReader( new InputStreamReader( soc.getInputStream())) ;
            output = new PrintWriter ( soc.getOutputStream(),true);
            name =input.readLine();
            users.add(name);
            System.out.println(name+" connected.");
            start();
        }
        public void sendMessage(String uname,String  msg)  {
            System.out.println(uname + ":" + msg);
            output.println(uname + ":" + msg);
        }
        @Override
        public void run(){
            String msg;
            try    {
                while(true)   {
                    msg = input.readLine();
                    if ( msg.equals("end") ) {
                    clients.remove(this);
                    users.remove(name);
                        break;
                    }
                    broadcast(name,msg); // method  of outer class - send messages to all
                } // end of while
            } // try
            catch(Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
}


}
