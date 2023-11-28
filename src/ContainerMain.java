package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ContainerMain extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assets/FormFlowComponent1.fxml"));
        Parent root = loader.load();
        Controller.giveStage(stage); //giving the controller the stage so that we can use popups like uploading pdfs

        Scene scene = new Scene(root);

        Image icon16 = new Image("src/assets/smile.png");
        stage.getIcons().add(icon16);
        stage.setScene(scene);
        stage.setTitle("FormFlow");
        stage.show();
    }
}
