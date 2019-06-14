package com.flchen.practice;

import com.flchen.practice.starter.MiniApplication;

/**
 * author fl.chen
 * Date 2019-06-14
 * Time 09:28
 **/
public class Application {

    public static void main(String[] args) {
        System.out.println("Hello, world");

        MiniApplication.run(Application.class, args);
    }
}
