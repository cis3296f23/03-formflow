package src;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.util.List;

/**
 * StructuredFile is a datatype we use to handle the PDF files.
 * It contains a String fileName, a File file, and a List of
 * type PDFieldWithLocation named fields.
 */
public class StructuredFile {
    String fileName;
    File file;
    List<PDFieldWithLocation> fields;

    /**
     * Constructor that assigns passed information to the data structure
     * @param fileName is a String
     * @param file is a File
     * @param fields is a List<PDFieldsWithLocation>
     */
    public StructuredFile(String fileName, File file, List<PDFieldWithLocation> fields){
        this.fileName = fileName;
        this.file = file;
        this.fields = fields;
    }
}
