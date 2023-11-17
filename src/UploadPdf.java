package com.example.formflow;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.File;
import java.util.List;

public class UploadPdf extends Application {

    private final ListView<File> listView = new ListView<>();

    // Data Structure for the pdf files
    private final ObservableList<File> pdfFiles = FXCollections.observableArrayList();

    VBox formFieldsContainer = new VBox(10);
    VBox pdfListContainer = new VBox(10);

    public ListView<File> getListView() {
        return listView;
    }

    public VBox getFormFieldsContainer() {
        return formFieldsContainer;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FormFlow");

        // List view for selected PDF files
        listView.setItems(pdfFiles);

        //Design each cell so it only display the pdf file name
        listView.setCellFactory(new Callback<ListView<File>, ListCell<File>>() {
            @Override
            public ListCell<File> call(ListView<File> listView) {
                return new ListCell<File>() {
                    @Override
                    protected void updateItem(File file, boolean empty) {
                        super.updateItem(file, empty);
                        if (empty || file == null) {
                            setText(null);
                        } else {
                            setText(file.getName());
                        }
                    }
                };
            }
        });


        // Button to open file chooser and add PDFs
        Button addPdfButton = new Button("Add PDF");
        addPdfButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf")
            );
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);
            if (selectedFiles != null) {
                pdfFiles.addAll(selectedFiles);
            }
        });

        // Button to handle 'Generate' action
        GenerateFields generateFields = new GenerateFields(this);
        Button generateButton = new Button("Generate");
        generateButton.setOnAction(e -> generateFields.generateAction());

        // Layout for list of form fields
        Label headerLabel = new Label("Required Information:");
        headerLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        formFieldsContainer.getChildren().add(headerLabel);
        formFieldsContainer.setAlignment(Pos.CENTER);
        ScrollPane formFieldsScrollPane = new ScrollPane();
        formFieldsScrollPane.setContent(formFieldsContainer);
        formFieldsScrollPane.setFitToWidth(true);
        formFieldsScrollPane.setPrefWidth(500);

        // Layout for list of pdf files
        Label headerLabelPdf = new Label("List Of PDF");
        headerLabelPdf.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        pdfListContainer.getChildren().addAll(headerLabelPdf, listView, addPdfButton, generateButton);
        pdfListContainer.setAlignment(Pos.CENTER);
        ScrollPane pdfListScrollPane = new ScrollPane();
        pdfListScrollPane.setContent(pdfListContainer);
        pdfListScrollPane.setFitToWidth(true);
        pdfListScrollPane.setPrefWidth(300);

        // Layout with HBox for horizontal split
        HBox mainLayout = new HBox(10);
        //HBox.setHgrow(formFieldsScrollPane, Priority.ALWAYS); // This will allow the left side to grow
        //HBox.setHgrow(pdfListScrollPane, Priority.NEVER);
        mainLayout.getChildren().addAll(
                formFieldsScrollPane,  // Left column for form fields
                pdfListScrollPane      // Right column for PDF list and buttons
        );


        Scene scene = new Scene(mainLayout, 800, 450);

        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
