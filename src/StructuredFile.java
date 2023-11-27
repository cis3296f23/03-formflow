package src;

import java.io.File;

public class StructuredFile {
    String fileName;
    File file;
    String fields;
    public StructuredFile(String fileName, File file, String fields){
        this.fileName = fileName;
        this.file = file;
        this.fields = fields;
    }
}
