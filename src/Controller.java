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
    int i = 0;
    List<ListItem> fileList= new ArrayList<>();
    @FXML
    private void initialize() {
        uploadButton.setOnAction(actionEvent -> {
            fileNames.add("file" + i + ".pdf");
            populateFileNameList();
            i++;
        });
        downloadAllButton.setOnAction(actionEvent -> System.out.println("download all"));
        generateButton.setOnAction(actionEvent -> System.out.println("generate"));
        populateFileNameList();
    }



    private void populateFileNameList() {

        formListView.getItems().clear();
        for (String fileName : fileNames) {


            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(actionEvent -> handleCheckBox(checkBox, fileName));
            Label label = new Label(fileName);

            HBox hbox = new HBox(checkBox, label);
            ListItem newListItem = new ListItem(checkBox,label);
            fileList.add(newListItem);
            formListView.getItems().add(hbox);

        }
    }

    private void handleCheckBox(CheckBox checkBox, String fileName) {
        if (checkBox.isSelected()) {
            System.out.println(fileName + " Selected");
        } else {
            System.out.println(fileName + " Un-Selected");
        }
    }
}
