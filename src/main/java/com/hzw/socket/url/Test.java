package com.hzw.socket.url;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @Author: Hzw
 * @Time: 2018/4/19 16:51
 * @Description:
 */
public class Test {

    public static void main(String[] args) throws IOException {

        URL url = new URL("http://112.74.36.19/");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

    }
}
