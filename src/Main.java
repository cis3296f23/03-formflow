package src;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args){
//        launch(EditContainerMain.class, args);
          Application.launch();
//        Stage primaryStage = ;
//        SceneManager sceneManager = new SceneManager(primaryStage);
//        sceneManager.loadInitialScene();
//
//        primaryStage.setTitle("Form Flow component 1");
//        primaryStage.show();

//        launch(args);
    }

    @Override
    public void start(Stage primaryStage){
        SceneManager sceneManager = new SceneManager(primaryStage);
        sceneManager.setActiveScene(sceneManager.getSceneOne(),"Component One");
        SharedData.getInstance().setSceneManager(sceneManager);

    }
}