package src;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDCheckBox;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

/**
 * Writer is a class that writes the information filled in the text
 * field to the new PDF document and saves it in the folder.
 */
public class Writer {

    /**
     * The generatePDF() method initially accesses the form fields (AcroForm)
     * of the PDF document. It then checks if the document contains an AcroForm.
     * It then iterates through formData Map where it retrieves the field from
     * the PDF using the field name. If the field is found in the PDF it checks
     * what kind of field it is and handles it accordingly. Once the fields have
     * been written to, it saves the modified document to the given path.
     * @param formData is a Map of Strings
     * @param originalFilePath is a String
     * @param completedFilePath is a String
     */
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
