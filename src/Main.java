package src;

/**
 * Main class sets up the rest of the application
 */
public class Main {
    /**
     * main method launches ContainerMain
     * @param args standard for Main class
     */
    public static void main(String[] args){
        ContainerMain.launch(ContainerMain.class, args);
        //ContainerSecondary.launch(ContainerSecondary.class, args);
    }
}
