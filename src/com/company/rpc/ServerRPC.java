package com.company.rpc;

import java.io.*;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerRPC {
    public static void main(String[] args) throws IOException {

        ServerSocket serverSocket = new ServerSocket(6969);
        System.out.println("Server started at 6969..");
        Socket sock =serverSocket.accept(); //accept only one

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        OutputStream ostream = sock.getOutputStream();
        PrintWriter pwrite = new PrintWriter(ostream, true);
        InputStream istream = sock.getInputStream();
        BufferedReader receiveRead = new BufferedReader(new InputStreamReader(istream));
        String out="";
        int a= 0,b=0;
        while(true){
            String str = receiveRead.readLine();
            if (str != null) {
                System.out.println("Called Procedure : " + str);
            }

            switch(str){

                case "echo":
                    String echoableStr = receiveRead.readLine();
                    out=echo(echoableStr);
                    pwrite.println(out);
                    System.out.println("Output of Procedure:"+echoableStr);
                    break;
                case "add":
                    a = Integer.parseInt(receiveRead.readLine());
                    b = Integer.parseInt(receiveRead.readLine());
                    out=add(a,b);
                    pwrite.println(out);
                    System.out.println("Output of Procedure:"+out);
                    break;
                case "subtract":
                     a = Integer.parseInt(receiveRead.readLine());
                     b = Integer.parseInt(receiveRead.readLine());
                    out=sub(a,b);
                    pwrite.println(out);
                    System.out.println("Output of Procedure:"+out);
                    break;

                default:
                    System.out.println("Undefined method: " + str);
                    break;

            }
//            pwrite.flush();


        }

    }

    public static String add(int a ,int b){
        return "Addition is "+ (a + b);
    }
    public static String sub(int a ,int b){
        return "Subtraction is "+ (a - b);
    }
    public static String echo(String str){
        return "Echoed:"+str;
    }
}
