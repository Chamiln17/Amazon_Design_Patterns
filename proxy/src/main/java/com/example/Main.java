package com.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Contenu proxy = new ProxyCloudFront();
        Client client = new Client(proxy);

        client.requestData("https://amazon.com/image1.png");  // Cache miss
        client.requestData("https://amazon.com/image2.png");  // Cache miss
        client.requestData("https://amazon.com/image1.png");  // Cache hit
    }
}


