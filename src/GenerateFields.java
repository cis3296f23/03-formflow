package src;


import org.apache.pdfbox.pdmodel.interactive.form.PDField;
import org.apache.pdfbox.pdmodel.interactive.form.PDNonTerminalField;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * GenerateFields is where the fields of StructuredFiles are processed.
 */
public class GenerateFields {

    /**
     * A set to store unique field names from the PDF
     */
    private Set<String> uniqueFields = new HashSet<>();

    /**
     * Updates the set of unique fields if the file is selected. Checks the boolean
     * parameter isSelected and if it is selected it calls addFields passing the
     * StructuredFile file and if it is not selected it calls removeFields passing file.
     * @param file is a StructuredFile
     * @param isSelected is a boolean
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
     * Adds fields from StructuredFile file to the set of unique fields. It iterates
     * through file.fields and adds to List fieldsList and then calls addFieldsFromList().
     * @param file is a StructuredFile
     * @throws IOException
     */
    private void addFields(StructuredFile file) throws IOException {
        // Extract fields from the structured file and add them to a list
        List<PDField> fieldsList = new ArrayList<>();
        for (PDFieldWithLocation pdFieldWithLocation : file.fields) {
            fieldsList.add(pdFieldWithLocation.pdField);
        }
        // Process the list of fields
        addFieldsFromList(fieldsList);
    }

    /**
     * Adds fields from a list of PDFields to the set of unique fields. It iterates
     * through the parameter fieldList and gets the fully qualified name of the field.
     * Then it adds the field name to the set of unique fields. It then checks if
     * the field is a non-terminal field, and if it is non-terminal the method processes
     * the field's children.
     * @param fieldsList is a List of PDFields
     */
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

    /**
     * Removes fields from the set of unique fields based on the given structured file.
     * It iterates through file.fields and adds the fields to a List named fieldsList
     * then calls the method removeFieldsFromList() passing fieldsList.
     * @param file is a StructuredFile
     * @throws IOException
     */
    private void removeFields(StructuredFile file) throws IOException {
        // Extract fields from the structured file and add them to a list
        List<PDField> fieldsList = new ArrayList<>();
        for (PDFieldWithLocation pdFieldWithLocation : file.fields) {
            fieldsList.add(pdFieldWithLocation.pdField);
        }
        // Process the list of fields for removal
        removeFieldsFromList(fieldsList);
    }

    /**
     * Removes fields from the set of unique fields based on a list of PDFields. It iterates
     * through the parameter fieldList and gets the fully qualified name of the field.
     * Then it removes the field name from the set of unique fields. It then checks if
     * the field is a non-terminal field, and if it is non-terminal the method removes
     * the field's children.
     * @param fieldsList is a List of PDFields
     */
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

    /**
     * Returns the set of unique fields
     * @return the set of unique fields
     */
    public Set<String> getUniqueFields() {
        return uniqueFields;
    }
}
