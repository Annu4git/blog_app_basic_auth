package com.anurag.blogapp;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        String fileName = "anurag.chaturvedi.png";

        String randomId = UUID.randomUUID().toString();

        System.out.println(randomId.concat(fileName.substring(fileName.lastIndexOf("."))));


    }
}
