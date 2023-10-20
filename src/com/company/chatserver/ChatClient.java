package com.company.chatserver;

import java.io.*;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws IOException {
        Socket soc = new Socket("127.0.0.1",6969);

        BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
        OutputStream outputStream = soc.getOutputStream();
        InputStream inputStream = soc.getInputStream();
        PrintWriter output = new PrintWriter(outputStream,true);
        BufferedReader received = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("Enter your name:");
        output.println(input.readLine());
        System.out.println("Connected to server..TYPE 'EXIT' TO LEAVE");
        final String[] receivedMsg = new String[1];


        //Thread to receive msg from chat server
        new Thread(() -> {  // runnable as lambda expression
            try {
                if((receivedMsg[0] =received.readLine()) != null){
                    System.out.println(receivedMsg[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


        //main Thread will take care of input msg that needed to be sent on server
        while(true){
            String in = input.readLine();
            if(in.equals("EXIT")){

                output.println("EXIT");

                break;
            }

            output.println(in);


        }


    }
}
