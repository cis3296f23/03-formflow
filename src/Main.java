package src;

import javafx.application.Application;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Main extends Application {
    public static void main(String[] args){
        Application.launch();
    }
    @Override
    public void start(Stage primaryStage){
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.setActiveScene(sceneManager.getSceneOne(),"Component One");
        SharedData.getInstance().setSceneManager(sceneManager);
    }
}