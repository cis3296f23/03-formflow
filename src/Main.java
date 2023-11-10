package src;
public class Main {
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(""));
        Scene scene = new Scene(loader.load());

        stage.setScene(scene);
        stage.setTitle("Form FLow");
        stage.show();
    }
    public static void main(String[] args){
        launch(args);
    }
}