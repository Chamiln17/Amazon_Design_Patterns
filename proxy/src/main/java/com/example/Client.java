package com.example;

public class Client {
    private Contenu proxy;
    public Client(Contenu proxy) {
        this.proxy = proxy;
    }
    public void requestData(String url) {
        String data = proxy.getData(url);
        System.out.println("Données reçues : " + data);
    }
}

