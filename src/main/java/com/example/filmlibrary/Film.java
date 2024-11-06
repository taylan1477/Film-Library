package com.example.filmlibrary;

import java.util.List;

public class Film {
    private String ad;                // Film adı
    private String yonetmen;          // Yönetmen
    private String tur;               // Tür
    private int yil;                  // Yayın yılı
    private double derecelendirme;    // Derecelendirme
    private String ozet;              // Özet
    private boolean favori;           // Favori durumu
    private int sure;                 // Film süresi
    private List<String> oyuncular;   // Oyuncular
    private String dil;               // Dil
    private String ulke;              // Ülke
    private String yapimci;           // Yapımcı
    private double imdbPuani;         // IMDB puanı

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


}
