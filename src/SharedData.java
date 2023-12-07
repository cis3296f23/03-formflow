package src;

public class SharedData {
    private static SharedData instance;
    private SceneManager sceneManager ;

    public static synchronized SharedData getInstance(){
        if(instance == null){
            instance = new SharedData();
        }
        return instance;
    }

    public SceneManager getSceneManager(){
        return sceneManager;
    }

    public void setSceneManager(SceneManager manager){
        sceneManager = manager;
    }


}