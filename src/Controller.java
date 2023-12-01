package src;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;


public class Controller {

    @FXML
    private AnchorPane fieldsBox;

    @FXML
    private Button generateButton;

    @FXML
    private Button downloadAllButton;

    @FXML
    private Button uploadButton;

    @FXML
    private ListView formListView;

    static Stage theStage;

    private final Initializer initializer = new Initializer();

    @FXML
    private TextField searchBar;



    public Initializer getInitializer() {
        return initializer;
    }





    @FXML
    void initialize() throws IOException {
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
            try {
                initializer.loadFiles(); //load the files in the folder into the list
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            // Display the updated list on the UI
            initializer.populateFileNameList(formListView);
        });
        searchBar.setOnKeyReleased(event -> {
            String searchText = searchBar.getText();
            // Display the filtered list on the UI
            initializer.populateFileNameList(formListView, searchText);
        });
        downloadAllButton.setOnAction(actionEvent -> System.out.println("download all"));
        generateButton.setOnAction(actionEvent -> System.out.println("generate"));
    }



    protected void updateUIWithFields(Set<String> fields) {


        // Clear any existing fields in the UI
        fieldsBox.getChildren().clear();

        // Initialize vertical position for the first field
        double yPos = 10.0;

        // List of descriptors to remove from the field names
        List<String> descriptorsToRemove = Arrays.asList("Formatted", "Field", "Box", "List", "Check","Text");

        for (String fullFieldName : fields) {
            // Split the full field name into words
            String[] words = fullFieldName.split("\\s+"); // Split on one or more spaces
            StringBuilder cleanedFieldNameBuilder = new StringBuilder();

            // Filter out the descriptors
            for (String word : words) {
                if (!descriptorsToRemove.contains(word)) {
                    if (cleanedFieldNameBuilder.length() > 0) {
                        cleanedFieldNameBuilder.append(" "); // add a space before appending the next word
                    }
                    cleanedFieldNameBuilder.append(word);
                }
            }

            String cleanedFieldName = cleanedFieldNameBuilder.toString();
            // Create a new label for the field name
            Label label = new Label(cleanedFieldName);
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