package com.example.filmlibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

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
    private ListView<String> filmlist;

    @FXML
    private ListView<String> filmoyuncular;

    @FXML
    private Spinner<Integer> filmrate;

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
    private Font x3;

    @FXML
    private Color x4;

    @FXML
    private Text yapimci;

    @FXML
    void filmekle(MouseEvent event) {
        filmlist.getItems().add(ifilmekle.getText());
    }

    @FXML
    void filmsil(MouseEvent event) {
        int selectedID = filmlist.getSelectionModel().getSelectedIndex();
        filmlist.getItems().remove(selectedID);
    }
    public void initialize() {
//        // ListView'i ekleme için hazırlıyoruz
//        listoffilms.setItems(filmList);

        // ListView'den bir öğe seçildiğinde filmismi text'ini güncelle
        filmlist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                filmisim.setText(newValue);

            }
        });
    }
}