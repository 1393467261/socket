package com.hzw.socket.chatroom.version1;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 11:59
 * @Description:
 */
public class ChatServer {

    private ServerSocket serverSocket;

    public ChatServer(){
        try {
            this.serverSocket = new ServerSocket(8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try {
            //接收客户端发送的消息
            Socket socket = serverSocket.accept();
            InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream, "utf-8");
            BufferedReader br = new BufferedReader(isr);

            //发送消息给客户端
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            PrintWriter pw = new PrintWriter(outputStream, true);
            Scanner scanner = new Scanner(System.in);

            while (true){
                System.out.println(br.readLine());
                pw.println(scanner.nextLine());
            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){

        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
