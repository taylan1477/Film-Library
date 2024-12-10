package com.example.filmlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.List;

public class FilmEkleController {

    @FXML
    private TextField filmNameField;

    @FXML
    private TextField filmDirectorField;

    @FXML
    private TextField filmGenreField;

    @FXML
    private TextField filmYearField;

    @FXML
    private TextField filmImdbField;

    @FXML
    private TextField filmLenghtField;

    @FXML
    private TextField filmProducerField;

    @FXML
    private TextField filmLanguageField;

    @FXML
    private TextField filmCountryField;

    private FilmController filmController; // FilmController referansı

    public void setFilmController(FilmController controller) {
        this.filmController = controller;
    }


    public void addFilm(ActionEvent event) {

        String name = filmNameField.getText();
        String director = filmDirectorField.getText();
        String genre = filmGenreField.getText();
        String year = filmYearField.getText();
        String imdb = filmImdbField.getText();
        String length = filmLenghtField.getText();
        String producer = filmProducerField.getText();
        String language = filmLanguageField.getText();
        String country = filmCountryField.getText();

        if (!name.isEmpty() && !year.isEmpty() && !imdb.isEmpty()) {

            try {
                // Yeni film kaydı yapılabilir
                Film newFilm = new Film(name, director, genre, Integer.parseInt(year), Double.parseDouble(imdb),
                        "Özet", false, Integer.parseInt(length),
                        List.of("Oyuncu1", "Oyuncu2"), language, country, producer, Double.parseDouble(imdb));

                // Filme kaydetme kodlarını ekleyin
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(FilmController.FILE_PATH, true))) {
                    writer.write(newFilm.toString());
                    writer.newLine();
                }

                // FilmController üzerinden filmleri yeniden yükle
                if (filmController != null) {
                    filmController.loadFilmsFromFile();
                }

                // Pencereyi kapat
                Stage stage = (Stage) filmNameField.getScene().getWindow();
                stage.close();

            } catch (Exception e) {
                System.err.println("Film eklenirken hata oluştu: " + e.getMessage());
            }
        } else {
            System.out.println("Film adı, yıl ve IMDB puanı boş olamaz!");
        }
    }
}
