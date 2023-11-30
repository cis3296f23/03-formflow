package src;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class GenerateFields {

    // A Set to store unique field names found in PDF documents
    private Set<String> uniqueFields = new HashSet<>();

    /**
     * Updates the set of unique fields based on whether a file is selected or not.
     * If the file is selected, adds its fields to the set, otherwise removes them.
     *
     * @param filePath The path of the PDF file to process.
     * @param isSelected Flag indicating whether the file is selected.
     */
    public void updateFields(String filePath, boolean isSelected) {
        try {
            if (isSelected) {
                // Add fields from the selected file
                addFields(filePath);
            } else {
                // Remove fields from the deselected file
                removeFields(filePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds fields from a given PDF file to the unique fields set.
     *
     * @param filePath Path of the PDF file to extract fields from.
     * @throws IOException If an error occurs while loading the PDF.
     */
    private void addFields(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                // Iterate over each field in the PDF form and add its name to the set
                for (PDField field : acroForm.getFields()) {
                    uniqueFields.add(field.getPartialName());
                }
            }
        }
    }

    /**
     * Removes fields found in a given PDF file from the unique fields set.
     *
     * @param filePath Path of the PDF file to remove fields from.
     * @throws IOException If an error occurs while loading the PDF.
     */
    private void removeFields(String filePath) throws IOException {
        try (PDDocument document = PDDocument.load(new File(filePath))) {
            PDAcroForm acroForm = document.getDocumentCatalog().getAcroForm();
            if (acroForm != null) {
                // Iterate over each field in the PDF form and remove its name from the set
                for (PDField field : acroForm.getFields()) {
                    uniqueFields.remove(field.getPartialName());
                }
            }
        }
    }

    /**
     * Returns the set of unique fields.
     *
     * @return A set containing unique field names.
     */
    public Set<String> getUniqueFields() {
        return uniqueFields;
    }
}
