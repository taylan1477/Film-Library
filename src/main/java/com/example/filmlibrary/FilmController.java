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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

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

    @FXML
    void kapakekle(MouseEvent event) {
        // Kullanıcıdan bir resim seçmesini iste
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Kapak Resmi Seç");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        // FileChooser penceresini aç
        Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            // "kapaklar" klasörüne taşınacak yolu belirle
            File destinationDir = new File("kapaklar");
            if (!destinationDir.exists()) {
                destinationDir.mkdirs(); // Eğer klasör yoksa oluştur
            }

            // Hedef dosyanın adını belirle (orijinal adıyla taşınır)
            File destinationFile = new File(destinationDir, selectedFile.getName());

            try {
                // Dosyayı "kapaklar" klasörüne kopyala
                Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                System.out.println("Kapak resmi başarıyla taşındı: " + destinationFile.getAbsolutePath());
            } catch (IOException e) {
                System.err.println("Kapak resmi taşınırken bir hata oluştu: " + e.getMessage());
            }
        } else {
            System.out.println("Hiçbir dosya seçilmedi.");
        }
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
        filmyonetmen.setText(film.getYonetmen());
        yapimci.setText(film.getYapimci());
        filmimdb.setText(String.valueOf(film.getImdbPuani()));

        // Kapak resmi için dosya kontrolü
        String kapakDosyasi = "kapaklar/" + film.getAd() + ".png";
        File kapakFile = new File(kapakDosyasi);

        // Varsayılan görüntü yolu
        String varsayilanKapak = "file:kapaklar/default_poster.png";
        File varsayilanKapakFile = new File("kapaklar/default_poster.png");

        // Varsayılan görüntünün varlığını kontrol edin
        if (!varsayilanKapakFile.exists()) {
            System.err.println("Varsayılan görüntü dosyası bulunamadı: default_poster.png");
            return; // Varsayılan dosya olmadan devam edemeyiz.
        }

        if (kapakFile.exists()) {
            // Kapak dosyası mevcutsa görüntüyü ayarla
            filmkapak.setImage(new Image("file:" + kapakDosyasi));
            filmkapak.setDisable(true); // Tıklanabilirliği kapat
        } else {
            // Kapak dosyası yoksa varsayılanı göster
            filmkapak.setImage(new Image(varsayilanKapak));
            filmkapak.setDisable(false); // Tıklanabilirliği açık bırak
        }

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
