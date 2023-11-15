package src;

import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Controller {
    @FXML
    private VBox rootVBox;

    @FXML
    private MenuBar menuBar;

    @FXML
    private SplitPane splitPane;

    // Fields Pane
    @FXML
    private Pane fieldsPane;

    @FXML
    private Label fieldsLabel;

    @FXML
    private ScrollPane fieldsScrollPane;

    @FXML
    private AnchorPane fieldsBox;

    @FXML
    private ScrollPane downloadableFilesScrollPane;

    @FXML
    private AnchorPane downloadableFilesBox;

    @FXML
    private Button generateButton;

    @FXML
    private Button downloadAllButton;

    // Forms Pane
    @FXML
    private Pane formsPane;

    @FXML
    private AnchorPane formsBox;

    @FXML
    private Label formsLabel;

    @FXML
    private Button uploadButton;

    @FXML
    private ListView formListView;

    @FXML
    private ScrollPane fileNameListScrollPane;

    @FXML
    private AnchorPane fileNameList;

    // Status Bar
    @FXML
    private HBox statusBar;

    @FXML
    private Label leftStatusLabel;

    @FXML
    private Pane statusPane;

    @FXML
    private Label rightStatusLabel;

    List<String> fileNames = new ArrayList<>();
    List<ListItem> fileList= new ArrayList<>();
    @FXML
    private void initialize() {
        //Create a folder in the users files if there already isn't one
        Initializer initializer = new Initializer();
        initializer.createFolders();
        initializer.loadFiles();
        uploadButton.setOnAction(actionEvent -> {
            //add a new file to the list
            initializer.uploadNewFile();
            initializer.populateFileNameList(formListView);
        });
        downloadAllButton.setOnAction(actionEvent -> System.out.println("download all"));
        generateButton.setOnAction(actionEvent -> System.out.println("generate"));
        initializer.populateFileNameList(formListView);
    }
}
