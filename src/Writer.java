package src;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

public class Writer {

    public void generatePDF(Map<String, String> formData, String originalFilePath, String completedFilePath) {
        try (PDDocument document = PDDocument.load(new File(originalFilePath))) {
            // Access the form fields (AcroForm) of the PDF document
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();

            // Check if the document contains an AcroForm
            if (acroForm != null) {
                // Iterate over each entry in the formData map
                for (Map.Entry<String, String> entry : formData.entrySet()) {
                    // Retrieve the field from the PDF using the field name
                    PDField field = acroForm.getField(entry.getKey());

                    // Check if the field was found in the PDF
                    if (field != null) {
                        // Skip setting the value if the provided data is empty or null
                        if (entry.getValue() == null || entry.getValue().isEmpty()) {
                            continue; // Skip to the next field
                        }

                        // Check if the field is a checkbox
                        if (field instanceof PDCheckBox) {
                            PDCheckBox checkBox = (PDCheckBox) field;
                            // Set the checkbox to "On" or "Off" based on the provided value
                            String value = entry.getValue().equalsIgnoreCase("true") ? checkBox.getOnValue() : "Off";
                            checkBox.setValue(value);
                        } else {
                            // For other field types, set the value directly
                            field.setValue(entry.getValue());
                        }
                    } else {
                        // If the field is not found in the PDF, log a message
                        System.out.println("Field not found in the PDF: " + entry.getKey());
                    }
                }
                // Save the modified document to the specified path
                document.save(completedFilePath);
            } else {
                // Log a message if the PDF does not contain an AcroForm
                System.out.println("No AcroForm found in the PDF.");
            }
        } catch (IOException e) {
            // Handle exceptions related to file I/O
            e.printStackTrace();
        }
    }

}
