package com.example.formflow;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.List;

public class UploadPdf extends Application {

    private final ListView<File> listView = new ListView<>();
    private final ObservableList<File> pdfFiles = FXCollections.observableArrayList();

    public ListView<File> getListView() {
        return listView;
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("FormFlow");

        // List view for selected PDF files
        listView.setItems(pdfFiles);

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
        GenerateFields generateFields = new GenerateFields();
        Button generateButton = new Button("Generate");
        generateButton.setOnAction(e -> generateFields.generateAction());

        // Layout
        VBox layout = new VBox(10, listView, addPdfButton, generateButton);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout, 500, 450);

        primaryStage.setScene(scene);
        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
