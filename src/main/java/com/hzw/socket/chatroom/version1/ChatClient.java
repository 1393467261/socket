package com.hzw.socket.chatroom.version1;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 11:38
 * @Description: 聊天室客户端V1.0,双向通信，客户端发送后服务端才可以发送,客户端发送前服务器发送的话，
 * 消息存储在缓冲区中，等待客户端发送消息后，缓冲区的消息才会发送
 */
public class ChatClient {

    private Socket socket;

    public ChatClient(){
        try {
            this.socket = new Socket("127.0.0.1", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){
        try{
            OutputStream outputStream = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(outputStream);
            PrintWriter pw = new PrintWriter(osw, true);
            Scanner sc = new Scanner(System.in);

            InputStream inputStream = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader br = new BufferedReader(isr);

            while (true){
                pw.println(sc.nextLine());
                System.out.println(br.readLine());
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (socket != null){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args){

        ChatClient client = new ChatClient();
        client.start();
    }
}
