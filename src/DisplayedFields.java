package src;

import javafx.scene.control.TextField;

public class DisplayedFields {
    String actualName;

    String cleanName;

    TextField textFieldReference;

    public DisplayedFields(String actualName, String cleanName, TextField textFieldReference){
        this.actualName = actualName;
        this.cleanName = cleanName;
        this.textFieldReference = textFieldReference;
    }
}
