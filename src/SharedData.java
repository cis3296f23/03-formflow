package src;

import java.util.ArrayList;
import java.util.List;

public class SharedData {
    private static SharedData instance;
    private SceneManager sceneManager ;

    private List<String> assets;

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


    public List<String> getAssets() {
        return assets;
    }

    public void setAssets(ArrayList<String> assets) {
        this.assets = assets;
    }

}