package src;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
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

    static Stage theStage;

    //Gets the stage from Container class
    public static void giveStage(Stage stage) {
        theStage = stage;
    }

    @FXML
    private void initialize() {
        //Create a folder in the users files if there already isn't one
        Initializer initializer = new Initializer();
        initializer.createFolders();
        //load the files in the folders into the list
        initializer.loadFiles();
        //display the list on the ui
        initializer.populateFileNameList(formListView);
        //upload button should prompt user to enter files
        uploadButton.setOnAction(actionEvent -> {
            //add a new file to the list
            initializer.uploadNewFile(theStage); //add new files to the folder
            initializer.loadFiles(); //load the files in the folder into the list
            initializer.populateFileNameList(formListView); //display the list on the ui
        });
        downloadAllButton.setOnAction(actionEvent -> System.out.println("download all"));
        generateButton.setOnAction(actionEvent -> System.out.println("generate"));
    }
}
