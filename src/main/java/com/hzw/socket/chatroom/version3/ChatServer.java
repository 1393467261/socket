package com.hzw.socket.chatroom.version3;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 14:48
 * @Description: 改进：版本2中由于serverSocket.accept()只执行一次，所以只能一对一通信，一对多通信必须有多个socket，并存储在server属性中作为集合存储
 *                  ReceiveThread将获取的socket存入集合中，send都遍历这个集合，实现群发功能
 *                  但是由于阻塞，所以一个线程不能处理多个socket，所以必须一个线程处理一个socket，创建线程的时候必须传入socket,main中每次接收一个，就创建对应的接收线程
 *                  群发消息流程是创建对应的接收线程后，将socket加入集合，遍历集合socket，给每个回话发送消息，而且输入scanner只输入一次，所以在main中输入
 *                  由于这个输入会阻塞，后面有连接到来的时候服务端accept执行不到，所以将输入做成线程，防止阻塞.
 */
@SuppressWarnings("all")
public class ChatServer {

    private ServerSocket serverSocket;
    private List<Socket> socketList = new ArrayList<>();

    public ChatServer(){

        try {
            serverSocket = new ServerSocket(8889);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){

    }

    public static void main(String[] args){

        ChatServer chatServer = new ChatServer();
        while (true){
            try {
                Socket socket = chatServer.serverSocket.accept();
                chatServer.socketList.add(socket);
                System.out.println("新的连接" + socket.getPort());

                Thread receiveThread = new Thread(new ReceiveThread(socket));
                receiveThread.start();

                SendMessage sendMessage = new SendMessage(chatServer.socketList);
                Thread thread = new Thread(sendMessage);
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SendMessage implements Runnable{

        private List<Socket> socketList;

        public SendMessage(List<Socket> socketList){
            this.socketList = socketList;
        }

        @Override
        public void run() {
            Scanner scanner = new Scanner(System.in);
            String s = scanner.nextLine();
            for (Socket socket1 : socketList) {
                Thread sendThread = new Thread(new SendThread(socket1, s));
                sendThread.start();
            }
        }
    }

    public static class SendThread implements Runnable{

        private Socket socket;
        private String s;

        public SendThread(Socket socket){
            this.socket = socket;
        }

        public SendThread(Socket socket, String s){
            this.socket = socket;
            this.s = s;
        }

        @Override
        public void run() {
            try {
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream);
                PrintWriter pw = new PrintWriter(osw, true);
                pw.println(s);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static class ReceiveThread implements Runnable{

        private Socket socket;

        public ReceiveThread(Socket socket){
            this.socket = socket;
        }

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
