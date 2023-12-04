package src;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class SharedData {
    private static SharedData instance;

    private final StringProperty messageProperty = new SimpleStringProperty();

    private SharedData(){
        // making private constructor to ensure singleton

    }

    public static synchronized SharedData getInstance(){
        if(instance == null){
            instance = new SharedData();
        }
        return instance;
    }

    public String getMessage(){
        return messageProperty.get();
    }

    public void setMessage(String message){
        messageProperty.set(message);

    }

    public StringProperty messageProperty(){
        return messageProperty;
    }




}
