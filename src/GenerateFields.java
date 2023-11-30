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
     * @param isSelected Flag indicating whether the file is selected.
     */
    public void updateFields(StructuredFile file, boolean isSelected) {
        try {
            if (isSelected) {
                // Add fields from the selected file
                addFields(file);
            } else {
                // Remove fields from the deselected file
                removeFields(file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds fields from a given PDF file to the unique fields set.
     *
     *
     * @throws IOException If an error occurs while loading the PDF.
     */
    private void addFields(StructuredFile file) throws IOException {
        // Iterate over each field in the PDF form and add its name to the set
        for (PDFieldWithLocation field : file.fields) {
            uniqueFields.add(field.pdField.getPartialName());
        }
    }

    /**
     * Removes fields found in a given PDF file from the unique fields set.
     *
     * @throws IOException If an error occurs while loading the PDF.
     */
    private void removeFields(StructuredFile file) throws IOException {
        // Iterate over each field in the PDF form and remove its name from the set
        for (PDFieldWithLocation field : file.fields) {
            uniqueFields.remove(field.pdField.getPartialName());

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
