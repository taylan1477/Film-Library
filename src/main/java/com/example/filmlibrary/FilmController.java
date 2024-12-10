package com.example.filmlibrary;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

public class FilmController {

    @FXML
    private AnchorPane anchorpane;

    @FXML
    private ScrollPane bigscrollpane;

    @FXML
    private VBox allscene;

    @FXML
    private SplitPane splitpane;

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
    private Text yapimci;

    @FXML
    private ListView<Film> filmlist;

    private final ObservableList<Film> filmList = FXCollections.observableArrayList();


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
    void filmfavekle(ActionEvent event) {

    }

    @FXML
    void filmara(ActionEvent event) {
        String searchTerm = filmsearch.getText().toLowerCase().trim();
        ObservableList<Film> filteredList = FXCollections.observableArrayList();

        if (searchTerm.isEmpty()) {
            // Arama terimi boşsa tüm filmleri göster
            filteredList.addAll(filmList);
        } else {
            for (Film film : filmList) {
                if (film.getAd().toLowerCase().contains(searchTerm)) {
                    filteredList.add(film);
                }
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

            // Seçilen film bilgisine eriş (örneğin bir ListView'dan)
            Film selectedFilm = filmlist.getSelectionModel().getSelectedItem();

            if (selectedFilm != null) {
                // Hedef dosyanın adını belirle (film adı ile taşınır)
                File destinationFile = new File(destinationDir, selectedFilm.getAd() + ".png");

                try {
                    // Dosyayı "kapaklar" klasörüne kopyala
                    Files.copy(selectedFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    System.out.println("Kapak resmi başarıyla taşındı: " + destinationFile.getAbsolutePath());

                    // Yeni kapak resmini göster
                    filmkapak.setImage(new Image("file:" + destinationFile.getAbsolutePath()));
                } catch (IOException e) {
                    System.err.println("Kapak resmi taşınırken bir hata oluştu: " + e.getMessage());
                }
            } else {
                System.err.println("Film seçilmedi. Kapak atanamadı.");
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
        } else {
            // ListView'deki ilk elemanı seçili yap
            filmlist.getSelectionModel().select(0);
            // İlk film detaylarını güncelle
            updateFilmDetails(filmList.get(0));
        }

        // Seçilen filmi detaylarla bağla
        filmlist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                updateFilmDetails(newValue);
            }
        });
    }

    private void applyThemeFromImage(Image image) {
        PixelReader pixelReader = image.getPixelReader();
        if (pixelReader == null) {
            return; // Resim yüklenemezse varsayılan tema kullanılabilir
        }

        int width = (int) image.getWidth();
        int height = (int) image.getHeight();

        double totalRed = 0, totalGreen = 0, totalBlue = 0;
        int pixelCount = 0;

        // Piksel okumayı adım adım yaparak performansı artırın
        for (int x = 0; x < width; x += 10) {
            for (int y = 0; y < height; y += 10) {
                Color color = pixelReader.getColor(x, y);
                totalRed += color.getRed();
                totalGreen += color.getGreen();
                totalBlue += color.getBlue();
                pixelCount++;
            }
        }

        Color averageColor = Color.color(totalRed / pixelCount, totalGreen / pixelCount, totalBlue / pixelCount);

        // Beyazlatma faktörü belirleyin (0 ile 1 arasında)
        double brightenFactor = 0.1;

        // Renk bileşenlerini beyazlatın
        double brightenedRed = Math.min(averageColor.getRed() + brightenFactor, 1.0);
        double brightenedGreen = Math.min(averageColor.getGreen() + brightenFactor, 1.0);
        double brightenedBlue = Math.min(averageColor.getBlue() + brightenFactor, 1.0);

        // Beyazlatılmış renk
        Color brightenedColor = Color.color(brightenedRed, brightenedGreen, brightenedBlue);

        // Renk değerlerini CSS formatına dönüştürün
        String colorStyle = String.format("-fx-background-color: rgb(%d, %d, %d);",
                (int) (brightenedColor.getRed() * 255),
                (int) (brightenedColor.getGreen() * 255),
                (int) (brightenedColor.getBlue() * 255));

        // Temayı uygulayın
        allscene.setStyle(colorStyle);
        splitpane.setStyle(colorStyle);
        anchorpane.setStyle(colorStyle);
        bigscrollpane.setStyle(colorStyle);
        anchorpane.setStyle(colorStyle);

        filmoyuncular.setStyle(colorStyle); // ListView'ın arka planı
        filmoyuncular.setCellFactory(lv -> new ListCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        // Hücreye stil uygula
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item);
                            setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 12)); // Font: Arial, Bold, 14pt
                            setStyle("-fx-text-fill: #000000;"); // Siyah renk (isteğe bağlı)
                        }
                        setStyle(colorStyle); // Varsayılan stil
                    }
                });

        filmlist.setStyle(colorStyle); // ListView'ın arka planı
        filmlist.setCellFactory(lv -> new ListCell<>() {
            @Override
            protected void updateItem(Film item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getAd());
                    setFont(javafx.scene.text.Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 14.5));
                    setStyle("-fx-text-fill: #000000;"); // Siyah renk (isteğe bağlı)
                }
                setStyle(colorStyle);
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

        // Temayı değiştir
        applyThemeFromImage(filmkapak.getImage());

        filmoyuncular.getItems().clear();
        filmoyuncular.getItems().addAll(film.getOyuncular());
    }

    // .fklib uzantısını kullanacak şekilde dosya yolunu ayarlayın
    public static final String FILE_PATH = "filmler.fklib";

    // Film dosyasını kaydederken uzantıyı kullan
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

    // Film dosyasını yüklerken uzantıyı kontrol et
    public void loadFilmsFromFile() {
        filmList.clear();
        File file = new File(FILE_PATH);
        if (!file.exists() || file.length() == 0) {
            System.out.println(".fklib dosyası bulunamadı veya boş.");
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
    @FXML
    void filmBilgileriniGuncelle() {
        // Seçili filmi al
        Film selectedFilm = filmlist.getSelectionModel().getSelectedItem();
        if (selectedFilm == null) {
            System.out.println("Film seçilmedi!");
            return;
        }

        try {
            // Yeni pencere oluştur
            FXMLLoader loader = new FXMLLoader(getClass().getResource("FilmEdit.fxml"));
            Parent root = loader.load();

            // Controller'ı al ve bilgileri gönder
            FilmEditController controller = loader.getController();
            Stage stage = new Stage();
            controller.setFilm(selectedFilm, stage);

            // Yeni pencereyi ayarla
            stage.setScene(new Scene(root));
            stage.setTitle("Film Bilgilerini Güncelle");
            stage.initModality(Modality.APPLICATION_MODAL); // Ana pencereyle etkileşim engellenir
            stage.showAndWait();

            // Değişiklik kaydedildiyse ListView'i yenile ve dosyaya yaz
            if (controller.isSaved()) {
                guncelleVeDosyayaKaydet(selectedFilm);
                filmlist.refresh(); // ListView güncellenir
                System.out.println("Film bilgileri güncellendi ve dosyaya kaydedildi!");
            }
        } catch (IOException e) {
            System.err.println("Yeni pencere açılamadı: " + e.getMessage());
        }
    }

    private void guncelleVeDosyayaKaydet(Film updatedFilm) {
        File dosya = new File("filmler.fklib");
        List<String> satirlar = new ArrayList<>();

        // Dosyayı satır satır oku
        try (BufferedReader reader = new BufferedReader(new FileReader(dosya))) {
            String satir;
            while ((satir = reader.readLine()) != null) {
                // Film ismine göre karşılaştırma yaparak güncelle
                String[] bilgiler = satir.split(";"); // Veriler noktalı virgülle ayrılmış
                if (bilgiler[0].equals(updatedFilm.getAd())) { // Film ismi kontrolü
                    // Güncellenmiş film bilgilerini oluştur
                    satir = updatedFilm.getAd() + ";" +
                            updatedFilm.getYonetmen() + ";" +
                            updatedFilm.getTur() + ";" +
                            updatedFilm.getYil() + ";" +
                            updatedFilm.getDerecelendirme() + ";" +
                            updatedFilm.getOzet() + ";" +
                            updatedFilm.isFavori() + ";" +
                            updatedFilm.getSure() + ";" +
                            updatedFilm.getOyuncular() + ";" +
                            updatedFilm.getDil() + ";" +
                            updatedFilm.getUlke() + ";" +
                            updatedFilm.getYapimci() + ";" +
                            updatedFilm.getImdbPuani();
                }
                satirlar.add(satir); // Güncellenmiş veya orijinal satırı ekle
            }
        } catch (IOException e) {
            System.err.println("Dosya okunurken bir hata oluştu: " + e.getMessage());
            return;
        }

        // Güncellenmiş verileri dosyaya yaz
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dosya))) {
            for (String satir : satirlar) {
                writer.write(satir);
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Dosya yazılırken bir hata oluştu: " + e.getMessage());
        }
    }
}