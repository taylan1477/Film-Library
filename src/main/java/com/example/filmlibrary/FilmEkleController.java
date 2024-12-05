package com.example.filmlibrary;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class FilmEkleController {

    @FXML
    private TextField filmNameField;

    @FXML
    private TextField filmYearField;

    @FXML
    private TextField filmDirectorField;

    public void addFilm(ActionEvent event) {
        String name = filmNameField.getText();
        String year = filmYearField.getText();
        String director = filmDirectorField.getText();

        System.out.println("Film Eklendi: " + name + ", " + year + ", " + director);

        // İlgili listeye veya dosyaya kaydetme işlemini buraya ekleyebilirsiniz
    }
}
