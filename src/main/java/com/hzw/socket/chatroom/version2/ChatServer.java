package com.hzw.socket.chatroom.version2;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 14:00
 * @Description:
 */
@SuppressWarnings("all")
public class ChatServer {

    private ServerSocket serverSocket;
    private Socket socket;

    public ChatServer(){
        try {
            serverSocket = new ServerSocket(8888);
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){

        Thread sendThread = new Thread(new SendThread());
        Thread receiveThread = new Thread(new ReceiveThread());
        sendThread.start();
        receiveThread.start();
    }

    public static void main(String[] args){

        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }

    public class SendThread implements Runnable{

        @Override
        public void run() {
            try {
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream);
                PrintWriter pw = new PrintWriter(osw, true);
                Scanner scanner = new Scanner(System.in);

                while(true){
                    pw.println(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ReceiveThread implements Runnable{

        @Override
        public void run() {
            try {
                InputStream inputStream = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                BufferedReader br = new BufferedReader(isr);

                while (true){
                    System.out.println(br.readLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}


