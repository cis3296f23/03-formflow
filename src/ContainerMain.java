package src;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * ContainerMain is the container for component 1. The container
 * is used to divide the UI into regions and layout components
 * in each region.
 * Extends the Application class.
 */
public class ContainerMain extends Application {
    /**
     * The start method loads FormFlowComponent1.fxml creates and loads the
     * controller. The initializer and controller are set to work with each
     * other. Sets the stage to a Parent object named root. Then adds the
     * FormFlow logo to the Icon portion of the stage. The start method then
     * sets the scene, the title, and then shows the scene.
     * @param stage is the Stage for the UI
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("assets/FormFlowComponent1.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        Initializer initializer = controller.getInitializer();
        initializer.setController(controller);


        //Controller.giveStage(stage); //giving the controller the stage so that we can use popups like uploading pdfs

        Scene scene = new Scene(root);

        Image icon16 = new Image("src/assets/smile.png");
        stage.getIcons().add(icon16);
        stage.setScene(scene);
        stage.setTitle("FormFlow");
        stage.show();
    }
}