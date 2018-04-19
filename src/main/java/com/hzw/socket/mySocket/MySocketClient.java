package com.hzw.socket.mySocket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 10:25
 * @Description:
 */
public class MySocketClient {

    public static void main(String[] args) throws Exception {

        Socket server=new Socket(InetAddress.getLocalHost(),55533);

        BufferedReader in=new BufferedReader(new InputStreamReader(server.getInputStream()));

        PrintWriter out=new PrintWriter(server.getOutputStream());

        BufferedReader wt=new BufferedReader(new InputStreamReader(System.in));



        while(true){

            String str=wt.readLine();

            out.println(str);

            out.flush();

            if(str.equals("end")){

                break;

            }

            System.out.println(in.readLine());

        }

        server.close();
    }
}
