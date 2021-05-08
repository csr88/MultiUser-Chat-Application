package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Scanner;

public class ServerMain{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the listening port: ");
        int port = sc.nextInt();
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread t= new Thread(){
                    @Override
                    public void run() {
                        try {
                            handleClientSocket(clientSocket);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                };
                t.start();


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void handleClientSocket(Socket clientSocket) throws IOException, InterruptedException {
        OutputStream outputStream = clientSocket.getOutputStream();
        for (int i=0;i<10;i++){
            outputStream.write(("Datetime is " + new Date() + "\n").getBytes(StandardCharsets.UTF_8));
            Thread.sleep(1000);
        }
        clientSocket.close();
    }
}
