package src;

// javaFX imports

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EditController {
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
    private ScrollPane assetListScrollPane;

    @FXML
    private ImageView pdfView;

    static Stage pdfViewStage;
    //Gets the stage from Container class
    public static void giveStage(Stage stage) {
        pdfViewStage = stage;
    }
    public ImageView getPdfView() {
        return pdfView;
    }
//    //Create a folder in the users files if there already isn't one
//        Initializer initializer = new Initializer();
//        // setup scene
//        //load the files in the folders into the list
//            File pdfFile = new File("res/OoPdfFormExample.pdf");
//            displayPDFPages(pdfFile, pdfView);
//            initializer.loadFiles(loadExistingDocument());
//        //display the list on the
//        // ui
//        try {
//            loadExistingDocument();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }

    public void loadExistingDocument() throws IOException {
        //Load existing pdf doc
        File pdfFile = new File("res/OoPdfFormExample.pdf");
        displayPDFPages(pdfFile, pdfView);
        PDDocument document = PDDocument.load(pdfFile);
        System.out.println("PDF Loaded");
        //Closing the pdf doc
        document.close();
    }
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
}