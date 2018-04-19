package com.hzw.socket.chatroom.version2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 14:01
 * @Description: 由于版本1客户端和服务器都有收发功能，并且收发有相应的顺序，都对应相应的阻塞：缓冲区br.readLine(),
 *               键盘输入sc.nextLine(),版本2将改进，将收发分配给相应的线程处理，避免了阻塞
 */
@SuppressWarnings("all")
public class ChatClient {

    private Socket socket;

    public ChatClient(){

        try {
            socket = new Socket("127.0.0.1", 8888);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start(){

        Thread sendThread = new Thread(new SendThread());
        Thread receiveThread = new Thread(new ReceiceThread());
        sendThread.start();
        receiveThread.start();
    }

    public static void main(String[] args){

        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }


    public class SendThread implements Runnable{

        @Override
        public void run() {
            try {
                OutputStream outputStream = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(outputStream);
                PrintWriter pw = new PrintWriter(osw, true);
                Scanner scanner = new Scanner(System.in);

                while (true){
                    pw.println(scanner.nextLine());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public class ReceiceThread implements Runnable{

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
