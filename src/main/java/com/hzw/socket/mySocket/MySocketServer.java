package com.hzw.socket.mySocket;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 10:16
 * @Description: socket服务端
 */
public class MySocketServer {

    public static void main(String[] args) throws IOException {

                ServerSocket server=new ServerSocket(55533);

                Socket client=server.accept();

                BufferedReader in=new BufferedReader(new InputStreamReader(client.getInputStream()));

                PrintWriter out=new PrintWriter(client.getOutputStream());

                while(true){

                    String str=in.readLine();

                    System.out.println(str);

                    out.println("has receive....");

                    out.flush();

                    if(str.equals("end"))

                        break;

                }

                client.close();

            }

        }
