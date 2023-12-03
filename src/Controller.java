package src;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;


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
    private VBox pdfListVBox;

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


    private Initializer initializer = new Initializer();

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
            initializer.populateFileNameList(formListView); //display the list on the ui
        });
        downloadAllButton.setOnAction(actionEvent -> System.out.println("download all"));
        generateButton.setOnAction(actionEvent -> {
            try {
                generateAndDisplayPDF();
            } catch (Exception e) {
                e.printStackTrace(); // Handle exception appropriately
            }
        });

    }


    protected void updateUIWithFields(Set<String> fields) {


        // Clear any existing fields in the UI
        fieldsBox.getChildren().clear();

        // Initialize vertical position for the first field
        double yPos = 10.0;

        // List of descriptors to remove from the field names
        List<String> descriptorsToRemove = Arrays.asList("Formatted", "Field", "Box", "List", "Check", "Text");

        for (String fullFieldName : fields) {
            // Split the full field name into words
            String[] words = fullFieldName.split("\\s+"); // Split on one or more spaces
            StringBuilder cleanedFieldNameBuilder = new StringBuilder();

            // Filter out the descriptors
            for (String word : words) {
                if (!descriptorsToRemove.contains(word)) {
                    if (!cleanedFieldNameBuilder.isEmpty()) {
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
            textField.setId(fullFieldName);
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

    private void generateAndDisplayPDF() throws IOException {
        // Collecting data from all TextField elements in the fieldsBox
        Map<String, String> formData = new HashMap<>();
        for (Node node : fieldsBox.getChildren()) {
            // Check if the node is a TextField to extract data
            if (node instanceof TextField) {
                TextField textField = (TextField) node;
                // Store the field's ID and its text value in formData
                formData.put(textField.getId(), textField.getText());
            }
        }

        // Iterate through each selected file for PDF generation
        for (StructuredFile file : initializer.selectedFiles) {
            // Get the absolute path of the original PDF file
            String originalPdfPath = file.file.getAbsolutePath();
            // Extract the file name from the file path
            String fileName = file.file.getName();
            // Construct the path where the completed PDF will be saved
            String completedPdfPath = initializer.completedPdfsPath + File.separator + fileName;

            // Generate the PDF with the provided form data
            new Writer().generatePDF(formData, originalPdfPath, completedPdfPath);

            // Create a file object for the newly generated PDF
            File pdfFile = new File(completedPdfPath);
            // Create a label for the PDF file which will be displayed in the UI
            Label pdfLabel = new Label(pdfFile.getName());

            // Set an event handler for mouse click on the PDF label
            pdfLabel.setOnMouseClicked(event -> {
                try {
                    // Attempt to open the PDF when the label is clicked
                    openPDF(completedPdfPath);
                } catch (Exception e) {
                    // Print stack trace and display error message if PDF fails to open
                    e.printStackTrace();
                    System.out.println("Error opening this Pdf file " + pdfFile.getName() + ": " + e.getMessage());
                }
            });

            // Add the label to the VBox in the UI to display it
            pdfListVBox.getChildren().add(pdfLabel);
        }
    }


    private void openPDF(String pdfPath) throws IOException {
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists() && Desktop.isDesktopSupported()) {
            Desktop.getDesktop().open(pdfFile);
        } else {
            // File doesn't exist or Desktop is not supported
            throw new IOException("Unable to open file: " + pdfPath);
        }
    }


}