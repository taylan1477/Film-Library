package com.example.filmlibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.*;
import java.util.List;

public class FilmController {

    @FXML
    private Text filmdil;

    @FXML
    private Text filmimdb;

    @FXML
    private Text filmisim;

    @FXML
    private ImageView filmkapak;

    @FXML
    private ListView<String> filmoyuncular;

    @FXML
    private Text filmrating;

    @FXML
    private TextField filmsearch;

    @FXML
    private Text filmsure;

    @FXML
    private Text filmtur;

    @FXML
    private Text filmulke;

    @FXML
    private Text filmyil;

    @FXML
    private Text filmyonetmen;

    @FXML
    private TextField ifilmekle;

    @FXML
    private Text yapimci;

    @FXML
    private ListView<Film> filmlist;

    private ObservableList<Film> filmList = FXCollections.observableArrayList();

    public static final String FILE_PATH = "filmler.txt";

    @FXML
    void filmekle(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FilmEkle.fxml"));
            Parent root = loader.load();
            // FilmEkleController referansını al
            FilmEkleController filmEkleController = loader.getController();
            filmEkleController.setFilmController(this);
            Stage stage = new Stage();
            stage.setTitle("Film Ekle");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void filmsil(MouseEvent event) {
        Film selectedFilm = filmlist.getSelectionModel().getSelectedItem();
        if (selectedFilm != null) {
            filmList.remove(selectedFilm);
            filmlist.setItems(filmList);
            saveFilmsToFile();
        }
    }

    @FXML
    void filmara(MouseEvent event) {
        String searchTerm = filmsearch.getText().toLowerCase();
        ObservableList<Film> filteredList = FXCollections.observableArrayList();

        for (Film film : filmList) {
            if (film.getAd().toLowerCase().contains(searchTerm)) {
                filteredList.add(film);
            }
        }

        filmlist.setItems(filteredList);
    }

    public void initialize() {
        // Dosyadan filmleri yükle
        loadFilmsFromFile();

        // Filmleri ListView ile bağla
        filmlist.setItems(filmList);

        // Liste boşsa mesaj veya başka bir eylem eklenebilir
        if (filmList.isEmpty()) {
            System.out.println("Kütüphane şu anda boş. Yeni filmler ekleyin.");
        }

        // Seçilen filmi detaylarla bağla
        filmlist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateFilmDetails(newValue);
            }
        });
    }

    private void updateFilmDetails(Film film) {
        filmisim.setText(film.getAd());
        filmyil.setText(String.valueOf(film.getYil()));
        filmtur.setText(film.getTur());
        filmsure.setText(film.getSure() + " dk");
        filmdil.setText(film.getDil());
        filmulke.setText(film.getUlke());
        filmrating.setText(String.valueOf(film.getDerecelendirme()));
        filmkapak.setImage(new Image("file:default_poster.png"));
        filmyonetmen.setText(film.getYonetmen());
        yapimci.setText(film.getYapimci());
        filmimdb.setText(String.valueOf(film.getImdbPuani()));

        filmoyuncular.getItems().clear();
        filmoyuncular.getItems().addAll(film.getOyuncular());
    }

    private void saveFilmsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Film film : filmList) {
                writer.write(film.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFilmsFromFile() {
        filmList.clear();
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            System.out.println("Film dosyası bulunamadı veya boş.");
            return;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    Film film = Film.fromString(line);
                    filmList.add(film);
                } catch (Exception e) {
                    System.err.println("Hatalı veri satırı atlandı: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showFilmEkleWindow(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FilmEkle.fxml"));
            Parent root = loader.load();
            // FilmEkleController referansını al
            FilmEkleController filmEkleController = loader.getController();
            filmEkleController.setFilmController(this);
            Stage stage = new Stage();
            stage.setTitle("Film Ekle");
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
