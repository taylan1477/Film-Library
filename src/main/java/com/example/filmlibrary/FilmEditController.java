package com.example.filmlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FilmEditController {

    @FXML
    private TextField filmCountryField;

    @FXML
    private TextField filmDirectorField;

    @FXML
    private TextField filmGenreField;

    @FXML
    private TextField filmImdbField;

    @FXML
    private TextField filmLanguageField;

    @FXML
    private TextField filmLenghtField;

    @FXML
    private TextField filmNameField;

    @FXML
    private TextField filmProducerField;

    @FXML
    private TextField filmYearField;


    private Film currentFilm;
    private Stage stage;
    private boolean isSaved = false;

    // Filmi düzenlemek için bilgi yükleme
    public void setFilm(Film film, Stage stage) {
        this.currentFilm = film;
        this.stage = stage;
        filmNameField.setText(film.getAd());
        filmGenreField.setText(film.getTur());
        filmDirectorField.setText(film.getYonetmen());
        filmYearField.setText(String.valueOf(film.getYil()));
        filmLenghtField.setText(String.valueOf(film.getSure()));
        filmImdbField.setText(String.valueOf(film.getImdbPuani()));
        filmCountryField.setText(film.getUlke());
        filmProducerField.setText(film.getYapimci());
        filmLanguageField.setText(film.getDil());
    }

    @FXML
    void saveEdit(ActionEvent event) {
        // Güncellenmiş bilgileri film objesine kaydet
        currentFilm.setAd(filmNameField.getText());
        currentFilm.setTur(filmGenreField.getText());
        currentFilm.setYonetmen(filmDirectorField.getText());
        try {
            currentFilm.setYil(Integer.parseInt(filmYearField.getText()));
        } catch (NumberFormatException e) {
            System.err.println("Geçersiz yıl formatı!");
            return;
        }
        try {
            currentFilm.setSure(Integer.parseInt(filmLenghtField.getText()));
        } catch (NumberFormatException e) {
            System.err.println("Geçersiz süre formatı!");
            return;
        }
        try {
            currentFilm.setImdbPuani(Double.parseDouble(filmImdbField.getText()));
        } catch (NumberFormatException e) {
            System.err.println("Geçersiz puan formatı!");
            return;
        }
        currentFilm.setUlke(filmCountryField.getText());
        currentFilm.setYapimci(filmProducerField.getText());
        currentFilm.setDil(filmLanguageField.getText());

        isSaved = true;
        stage.close(); // Pencereyi kapat
    }

    public boolean isSaved() {
        return isSaved;
    }
}
