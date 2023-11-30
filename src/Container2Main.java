package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Container2Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assets/FormFlowComponent2.fxml"));
        Parent root = loader.load();
        Controller.giveStage(stage);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("FormFlowComponent2");
        stage.show();
    }
}