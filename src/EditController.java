package src;

// javaFX imports

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.awt.event.ActionEvent;
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
    private ScrollPane assetListScrollPane;

    static Stage pdfViewStage;
    //Gets the stage from Container class
    public static void giveStage(Stage stage) {
        pdfViewStage = stage;
    }
    @FXML
    private void initialize() {
        //Create a folder in the users files if there already isn't one
        Initializer initializer = new Initializer();
        // setup scene


        //load the files in the folders into the list
//        initializer.loadFiles(loadingExistingDocument);
        //display the list on the ui
    }

    public static void loadExistingDocument() throws IOException {
        //Load existing pdf doc
        File pdfFile = new File("res/OoPdfFormExample.pdf");
        PDDocument document = PDDocument.load(pdfFile);
        System.out.println("PDF Loaded");
        //Closing the pdf doc
        document.close();
    }

    private Parent root;


    public void switchScene(ActionEvent event) throws IOException {
//        Parent root = FXMLLoader.load(getClass().getResource("FormFlowComponent2.fxml"));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}