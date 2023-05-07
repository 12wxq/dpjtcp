package com.example.myapplication.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class InputStreamUtils {
    public static String parseIsToString(InputStream is)throws IOException{
        StringBuilder builder=new StringBuilder();
        String line;
        BufferedReader br=new BufferedReader(new InputStreamReader(is));
        while ((line=br.readLine())!=null){
            builder.append(line);
        }
        String str=builder.toString();
        return str;




    }
}