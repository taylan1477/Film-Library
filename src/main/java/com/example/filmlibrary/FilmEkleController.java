package com.example.filmlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

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

    public void addFilm(ActionEvent event) {
        String name = filmNameField.getText();
        String director = filmDirectorField.getText();
        String genre = filmGenreField.getText();
        String year = filmYearField.getText();
        String imdb = filmImdbField.getText();
        String lenght = filmLenghtField.getText();
        String producer = filmProducerField.getText();
        String language = filmLanguageField.getText();
        String country = filmCountryField.getText();

        // Filmi belirtilen formatta dosyaya ekleme
        String filmDetails = String.format(
                "%s;%s;%s;%s;%s;Film açıklaması buraya eklenebilir.;false;%s;Oyuncular buraya eklenebilir.;%s;%s;%s;%s",
                name, director, genre, year, imdb, lenght, language, country, producer, imdb
        );

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("filmler.txt", true))) {
            writer.write(filmDetails);
            writer.newLine();
            System.out.println("Film başarıyla kaydedildi: " + filmDetails);
        } catch (IOException e) {
            System.err.println("Dosyaya yazılırken bir hata oluştu: " + e.getMessage());
        }
    }
}
