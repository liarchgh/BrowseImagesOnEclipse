package com.example.Utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by neo on 2017/12/9.
 */

public class NetUtil {
//    public static String doGetBingApi(String address, String word) throws IOException{
//        URL url = new URL(address);
//        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
//        conn.setRequestProperty(field, word);
//        conn.setConnectTimeout(10000);
////        conn.setRequestProperty("Accept", "image/jepg,*/*");
////        conn.setRequestProperty("Connection", "close");
//        conn.setRequestMethod("GET");
//        String msg = null; 
//        try {
//            InputStream in = conn.getInputStream();
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            byte[] buffer = new byte[1024];
//
//            while(true){
//                int len = in.read(buffer);
//                if(len == -1){
//                    break;
//                }
//                out.write(buffer, 0, len);
//            }
//            byte[] arr = out.toByteArray();
//            msg = new String(arr, "UTF-8");
//            in.close();
//            out.close();
//        }
//        catch(Exception e){
////        	msg = "e";
//        }
//		return msg;
//    }
    public static String doGet(String address) throws IOException{
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(10000);
//        conn.setConnectTimeout(10);
//        conn.setRequestProperty("Accept", "image/jepg,*/*");
//        conn.setRequestProperty("Connection", "close");
        conn.setRequestMethod("GET");
        String msg = null; 
        try {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            while(true){
                int len = in.read(buffer);
                if(len == -1){
                    break;
                }
                out.write(buffer, 0, len);
            }
            byte[] arr = out.toByteArray();
            msg = new String(arr, "UTF-8");
            in.close();
            out.close();
        }
        catch(Exception e){
        	msg = "e";
        }
		return msg;
    }

    public static byte[] doGetImage(String address) throws IOException{
        byte[] res = null;
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(10000);
        conn.setRequestProperty("Accept", "image/jepg,*/*");
        conn.setRequestProperty("Connection", "close");
        conn.setRequestMethod("GET");
        int length = conn.getContentLength();
        int total = 0;
        try {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            while(true){
                int len = in.read(buffer);
                if(len == -1){
                    break;
                }
                total += len;
                int present = (total * 100 / length);
                out.write(buffer, 0, len);
            }
            byte[] arr = out.toByteArray();
            out.close();
            return arr;
        }
        catch(Exception e){
            System.out.println("E");
        }
        return res;
    }

	public static Bitmap doGetBitmap(String address) throws IOException{
        Bitmap res = null;
        URL url = new URL(address);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setConnectTimeout(10000);
//        conn.setRequestProperty("Accept", "image/jepg,*/*");
//        conn.setRequestProperty("Connection", "close");
        conn.setRequestMethod("GET");
        int total = 0;
        try {
            InputStream in = conn.getInputStream();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];

            while(true){
                int len = in.read(buffer);
                if(len == -1){
                    break;
                }
                total += len;
                out.write(buffer, 0, len);
            }
            byte[] arr = out.toByteArray();
            out.close();
            in.close();
            res = BitmapFactory.decodeByteArray(arr, 0, arr.length);
        }
        catch(Exception e){
        }
		return res;
    }
}
