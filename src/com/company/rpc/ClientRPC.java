package com.company.rpc;

import java.io.*;
import java.net.Socket;

public class ClientRPC {
    public static void main(String[] args) throws IOException {

        Socket soc = new Socket("127.0.0.1",6969);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        OutputStream  outputStream =  soc.getOutputStream();
        InputStream inputStream =  soc.getInputStream();
        PrintWriter printWriter = new PrintWriter(outputStream,true);
        BufferedReader receivedRead  = new BufferedReader(new InputStreamReader(inputStream));
        String receiveMessage;
        System.out.println("RPC Available =>");
        while(true){
            System.out.println("1.add 2.subtract 3.echo 4.exit");
            String input = bufferedReader.readLine();
            switch(input){
                case "add": case "substract":
                printWriter.println(input);
                printWriter.println(bufferedReader.readLine());
                printWriter.println(bufferedReader.readLine());
                break;
                case "echo":
                    printWriter.println(input);
                    printWriter.println(bufferedReader.readLine());
                break;
                case "exit":
                    System.exit(0);
                    soc.close();
                    return;
                default:
                    printWriter.println(input);

                    break;


            }

//
//            printWriter.flush();
            if ((receiveMessage = receivedRead.readLine()) != null) {
                System.out.println(receiveMessage);
            }
        }



    }
}
