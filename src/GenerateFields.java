package src;


import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GenerateFields {

    // A set to store unique field names from the PDF
    private Set<String> uniqueFields = new HashSet<>();

    // Updates the set of unique fields based on the selection of the file
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

    // Adds fields from a given PDF file to the set of unique fields
    private void addFields(StructuredFile file) throws IOException {
        // Extract fields from the structured file and add them to a list
        List<PDField> fieldsList = new ArrayList<>();
        for (PDFieldWithLocation pdFieldWithLocation : file.fields) {
            fieldsList.add(pdFieldWithLocation.pdField);
        }
        // Process the list of fields
        addFieldsFromList(fieldsList);
    }

    // Adds fields from a list of PDFields to the set of unique fields
    private void addFieldsFromList(List<PDField> fieldsList) {
        for (PDField field : fieldsList) {
            try {
                // Get the fully qualified name of the field
                String fieldName = field.getFullyQualifiedName();
                System.out.println("Processing field " + fieldName);
                // Add the field name to the set of unique fields
                uniqueFields.add(fieldName);
                // If the field is a non-terminal field, process its children
                if (field instanceof PDNonTerminalField) {
                    PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
                    addFieldsFromList(nonTerminalField.getChildren());
                }
            } catch (Exception e) {
                System.out.println("Error processing field " + e.getMessage());
            }
        }
    }

    // Removes fields from the set of unique fields based on the given structured file
    private void removeFields(StructuredFile file) throws IOException {
        // Extract fields from the structured file and add them to a list
        List<PDField> fieldsList = new ArrayList<>();
        for (PDFieldWithLocation pdFieldWithLocation : file.fields) {
            fieldsList.add(pdFieldWithLocation.pdField);
        }
        // Process the list of fields for removal
        removeFieldsFromList(fieldsList);
    }

    // Removes fields from the set of unique fields based on a list of PDFields
    private void removeFieldsFromList(List<PDField> fieldsList) {
        for (PDField field : fieldsList) {
            try {
                // Get the fully qualified name of the field
                String fieldName = field.getFullyQualifiedName();
                System.out.println("Processing field " + fieldName);
                // Remove the field name from the set of unique fields
                uniqueFields.remove(fieldName);
                // If the field is a non-terminal field, process its children for removal
                if (field instanceof PDNonTerminalField) {
                    PDNonTerminalField nonTerminalField = (PDNonTerminalField) field;
                    removeFieldsFromList(nonTerminalField.getChildren());
                }
            } catch (Exception e) {
                System.out.println("Error processing field " + e.getMessage());
            }
        }
    }

    // Returns the set of unique fields
    public Set<String> getUniqueFields() {
        return uniqueFields;
    }
}
