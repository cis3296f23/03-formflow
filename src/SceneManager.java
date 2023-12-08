package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {
    private Stage primaryStage;
    private Scene scene1;
    private Scene scene2;

    public SceneManager(Stage stage) {
        primaryStage = stage;
        scene1 = createScene("assets/FormFlowComponent1.fxml");
        scene2 = createScene("assets/FormFlowComponent2.fxml");
    }
    private Scene createScene(String res){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(res));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        return scene;
    }
    public void setActiveScene(Scene scene, String title){
        primaryStage.setScene(scene);
        primaryStage.setTitle(title);
        primaryStage.show();
    }
    public Scene getSceneOne(){
        return scene1;
    }
    public Scene getSceneTwo(){
        return scene2;
    }

    public void switchSceneOne() {
        this.setActiveScene(scene1,"Main Component");
    }
    public void switchSceneTwo() {
        this.setActiveScene(scene2,"Edit Component");
    }
}