package src;

// javaFX imports

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditController implements Initializable {

    @FXML
    private VBox rootVBox;

    @FXML
    private MenuBar menuBar;

    @FXML
    private SplitPane splitPane;

    // Assets Pane
    @FXML
    private Pane assetPane;

    @FXML
    private Label assetLabel;

    @FXML
    private ScrollPane assetScrollPane;

    @FXML
    private AnchorPane assetsBox;

    // Forms Pane
    @FXML
    private Pane formPane;
    @FXML
    private AnchorPane assetBox;
    @FXML
    private Label formName;
    @FXML
    private Button saveButton;
    @FXML
    private Button showPDFButton;
    @FXML
    private Button switchSceneButton;
    @FXML
    private ScrollPane assetListScrollPane;

    @FXML
    private WebView webView;

    ObservableList<String> assetsList = FXCollections.observableArrayList(
            "name",
            "address",
            "DOB",
            "SSN"
    );

    ObservableList<String> names = FXCollections.observableArrayList(
            "Julia", "Ian", "Sue", "Matthew", "Hannah", "Stephan", "Denise");
    @FXML
    ListView<String> listView = new ListView<String>(names);

//    @FXML
//    private ListView<String> listView = new ListView<>(assetsList);;

//    public void loadExistingDocument() throws IOException {
//        File pdfFile = new File("res/OoPdfFormExample.pdf");
//        displayPDFPages(pdfFile, webView);
//        PDDocument document = PDDocument.load(pdfFile);
//        System.out.println("PDF Loaded");
//        //Closing the pdf doc
//        document.close();
//    }
    private static void displayPDFPages(File pdfFile, ImageView imageView) {
        try {
            PDDocument document = PDDocument.load(pdfFile);
            PDFRenderer pdfRenderer = new PDFRenderer(document);
            // display each page as img
            for (int pageIndex = 0; pageIndex < document.getNumberOfPages(); pageIndex++) {
                BufferedImage bufferedImage = pdfRenderer.renderImage(pageIndex);
                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
                imageView.setImage(image);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    // pathToHTMLFile = outputPath + pdfFilename

    public void showPDF() {
        Initializer initializer = new Initializer();
        File folder = new File(initializer.mainFolder, initializer.subFolder1);
        File folder2 = new File(initializer.mainFolder, initializer.subFolder4);

        File[] files = folder.listFiles(); //get all the files in the folder
        if(files.length <= 1){return;}
        PDFManipulation pdfManipulation = new PDFManipulation();
        pdfManipulation.generateHTMLFromPDF(files[1].getAbsolutePath());

        String fileName = files[1].getName();
        String pathToHTMLFile = folder2.getAbsolutePath() + "/" + fileName.substring(0, fileName.lastIndexOf('.')) + ".html";
        System.out.println(fileName);
        System.out.println(pathToHTMLFile);

//        URL url = getClass().getResource(pathToHTMLFile);
        String url = new File(pathToHTMLFile).toURI().toString();
        webView.getEngine().load(url);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        SharedData.getInstance().setAssets(assetsList);

        showPDF();
    }
}