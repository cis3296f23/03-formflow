package com.example.formflow;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateFields {

    private final UploadPdf uploadPdf;

    public GenerateFields(UploadPdf uploadPdf) {
        this.uploadPdf = uploadPdf;

    }


    //  'Generate' action
    protected void generateAction() {

        // Data Structure for unique fields
        Set<String> uniqueFields = new HashSet<>();
        List<PDField> fields = null;

        // Extracting all fields from each pdf file
        for (File pdfFile : uploadPdf.getListView().getItems()) {
            try (PDDocument document = PDDocument.load(pdfFile)) {
                PDAcroForm acroform = document.getDocumentCatalog().getAcroForm();
                fields = acroform.getFields();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        // Adding unique fields from each pdf file
        for (PDField field : fields) {
            uniqueFields.add(field.getFullyQualifiedName());
        }

        // Layout for form fields
        uploadPdf.getFormFieldsContainer().getChildren().remove(1, uploadPdf.getFormFieldsContainer().getChildren().size());// Clear previous fields if any
        for (String fieldName : uniqueFields) {
            Label label = new Label(fieldName);
            TextField textField = new TextField();
            uploadPdf.getFormFieldsContainer().getChildren().addAll(label, textField);

        }


    }
}
