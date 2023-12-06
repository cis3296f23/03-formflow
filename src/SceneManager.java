package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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



//    public  void start(Stage primaryStage){
//        // create first Scene
//        StackPane root = new StackPane();
//        Scene scene = new Scene(root, 300, 250);
//        Button btn = new Button("Edit Scene");
////        btn.setOnAction(e -> showSecondScene(scene));
//        root.getChildren().add(btn);
//
//
//        primaryStage.setTitle("First Scene");
//        primaryStage.setScene(scene);
//        primaryStage.show();
//
//    }

//    private static void showSecondScene(Runnable onButtonClick){
//        Platform.runLater(() -> {
//            // create second Scene
//            StackPane secondRoot = new StackPane();
//            Button secondBtn = new Button("Go to First Scene");
//            secondBtn.setOnAction(e -> onButtonClick.run());
//            secondRoot.getChildren().add(secondBtn);
//
//            Scene secondScene = new Scene(secondRoot, 300, 250);
//
//            Stage secondStage = new Stage();
//            secondStage.setTitle("Second Scene");
//            secondStage.setScene(secondScene);
//            secondStage.show();
//
//        });
//    }
//    public void loadInitialScene(){
//        primaryStage.setScene(scene1);
//    }
//    public void switchToScene2(){
//        primaryStage.setScene(scene2);
//    }
//    private void createScenes(){
//
//        scene1 = new Scene(new javafx.scene.layout.StackPane(),800,600);
//
//        scene2 = new Scene(new javafx.scene.layout.StackPane(),800,600);
//
//    }
}