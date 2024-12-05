package com.example.filmlibrary;

import java.util.ArrayList;
import java.util.List;

public class Film {
    private String ad;                // Film adı
    private String yonetmen;          // Yönetmen
    private String tur;               // Tür
    private int yil;                  // Yayın yılı
    private double imdbPuani;         // IMDB puanı
    private int sure;                 // Film süresi
    private String yapimci;           // Yapımcı
    private String dil;               // Dil
    private String ulke;              // Ülke
    private String ozet;              // Özet
    private boolean favori;           // Favori durumu
    private List<String> oyuncular;   // Oyuncular
    private double derecelendirme;    // Derecelendirme

    // Yapıcı metod
    public Film(String ad, String yonetmen, String tur, int yil, double derecelendirme,
                String ozet, boolean favori, int sure, List<String> oyuncular,
                String dil, String ulke, String yapimci, double imdbPuani) {
        this.ad = ad;
        this.yonetmen = yonetmen;
        this.tur = tur;
        this.yil = yil;
        this.derecelendirme = derecelendirme;
        this.ozet = ozet;
        this.favori = favori;
        this.sure = sure;
        this.oyuncular = oyuncular;
        this.dil = dil;
        this.ulke = ulke;
        this.yapimci = yapimci;
        this.imdbPuani = imdbPuani;
    }

    // Getter ve Setter metodları
    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public String getYonetmen() {
        return yonetmen;
    }

    public void setYonetmen(String yonetmen) {
        this.yonetmen = yonetmen;
    }

    public String getTur() {
        return tur;
    }

    public void setTur(String tur) {
        this.tur = tur;
    }

    public int getYil() {
        return yil;
    }

    public void setYil(int yil) {
        this.yil = yil;
    }

    public double getDerecelendirme() {
        return derecelendirme;
    }

    public void setDerecelendirme(double derecelendirme) {
        this.derecelendirme = derecelendirme;
    }

    public String getOzet() {
        return ozet;
    }

    public void setOzet(String ozet) {
        this.ozet = ozet;
    }

    public boolean isFavori() {
        return favori;
    }

    public void setFavori(boolean favori) {
        this.favori = favori;
    }

    public int getSure() {
        return sure;
    }

    public void setSure(int sure) {
        this.sure = sure;
    }

    public List<String> getOyuncular() {
        return oyuncular;
    }

    public void setOyuncular(List<String> oyuncular) {
        this.oyuncular = oyuncular;
    }

    public String getDil() {
        return dil;
    }

    public void setDil(String dil) {
        this.dil = dil;
    }

    public String getUlke() {
        return ulke;
    }

    public void setUlke(String ulke) {
        this.ulke = ulke;
    }

    public String getYapimci() {
        return yapimci;
    }

    public void setYapimci(String yapimci) {
        this.yapimci = yapimci;
    }

    public double getImdbPuani() {
        return imdbPuani;
    }

    public void setImdbPuani(double imdbPuani) {
        this.imdbPuani = imdbPuani;
    }

    // Dosya yazma/okuma için toString ve fromString metotları
    @Override
    public String toString() {
        return ad + ";" + yonetmen + ";" + tur + ";" + yil + ";" + derecelendirme + ";" +
                ozet + ";" + favori + ";" + sure + ";" + String.join(",", oyuncular) + ";" +
                dil + ";" + ulke + ";" + yapimci + ";" + imdbPuani;
    }

    public static Film fromString(String line) {
        String[] parts = line.split(";");

        // Hatalı veri satırı kontrolü
        if (parts.length != 13) { // Beklenen 13 alan (12 film bilgisi ve 1 imdb puanı)
            throw new IllegalArgumentException("Veri formatı hatalı: " + line);
        }

        // Veriyi parçalayıp Film nesnesine dönüştürme işlemi
        String ad = parts[0];
        String yonetmen = parts[1];
        String tur = parts[2];
        int yil = Integer.parseInt(parts[3]);
        double derecelendirme = Double.parseDouble(parts[4]);
        String ozet = parts[5];
        boolean favori = Boolean.parseBoolean(parts[6]);
        int sure = Integer.parseInt(parts[7]);
        List<String> oyuncular = new ArrayList<>();  // Hatalı kısmı burada düzelttik
        for (String oyuncu : parts[8].split(",")) {
            oyuncular.add(oyuncu);
        }
        String dil = parts[9];
        String ulke = parts[10];
        String yapimci = parts[11];
        double imdbPuani = Double.parseDouble(parts[12]);

        return new Film(ad, yonetmen, tur, yil, derecelendirme, ozet, favori, sure, oyuncular, dil, ulke, yapimci, imdbPuani);
    }
}
