package com.example;

public class ServeurOrigine implements Contenu {
    public String getData(String url) {
        System.out.println("Récupération depuis le serveur d'origine: " + url);
        return "Contenu de " + url;
    }
}

