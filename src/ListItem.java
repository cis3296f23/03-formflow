package src;

import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;

public class ListItem {
    CheckBox checkBox;
    Label fileName;
    public ListItem(CheckBox checkBox, Label fileName) {
        this.checkBox = checkBox;
        this.fileName = fileName;
    }

    public String getFileName(){
        return fileName.getText();
    }
}
