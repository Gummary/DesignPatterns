package com.hepeng.main;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Main {

    public static void main(String[] args) {
	// write your code here
        int c;
        try {
            InputStream in = new LowerCaseInputStream(
                                new BufferedInputStream(
                                        new FileInputStream("test.txt")));

            while((c = in.read())>0){
                System.out.print((char)c);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
