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
import java.util.Set;


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
    List<ListItem> fileList = new ArrayList<>();

    static Stage theStage;

    //Gets the stage from Container class
    //public static void giveStage(Stage stage) {theStage = stage;}

    private Initializer initializer = new Initializer();

    public Initializer getInitializer() {
        return initializer;
    }

    @FXML
    void initialize() {
        //Create a folder in the users files if there already isn't one

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


    protected void updateUIWithFields(Set<String> fields) {


        // Clear any existing fields in the UI
        fieldsBox.getChildren().clear();

        // Initialize vertical position for the first field
        double yPos = 10.0;

        for (String fieldName : fields) {
            // Create a new label for the field name
            Label label = new Label(fieldName);
            label.setLayoutX(10); // Set X position for label
            label.setLayoutY(yPos); // Set Y position for label

            // Create a new text field for the field value
            TextField textField = new TextField();
            textField.setLayoutX(150); // Set X position for text field
            textField.setLayoutY(yPos); // Set Y position for text field
            textField.setPrefWidth(200); // Set preferred width for text field

            // Increment the Y position for the next field
            yPos += 30;

            // Add the label and text field to the fieldsBox
            fieldsBox.getChildren().addAll(label, textField);
        }

        // Update the fieldsBox scrollable area if necessary
        fieldsBox.setPrefHeight(yPos);
    }
}