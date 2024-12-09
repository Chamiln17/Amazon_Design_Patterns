package com.example;
import java.util.HashMap;
import java.util.Map;

public class ProxyCloudFront implements Contenu {
    private ServeurOrigine serveurOrigine = new ServeurOrigine();
    private Map<String, String> cache = new HashMap<>();

    public String getData(String url) {
        if (cache.containsKey(url)) {
            System.out.println("Cache hit pour : " + url);
            return cache.get(url);
        }
        System.out.println("Cache miss, récupération depuis l'origine...");
        String data = serveurOrigine.getData(url);
        cache.put(url, data);
        return data;
    }
}


