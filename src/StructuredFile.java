package src;

import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.File;
import java.util.List;

public class StructuredFile {
    String fileName;
    File file;
    List<PDFieldWithLocation> fields;
    public StructuredFile(String fileName, File file, List<PDFieldWithLocation> fields){
        this.fileName = fileName;
        this.file = file;
        this.fields = fields;
    }
}
