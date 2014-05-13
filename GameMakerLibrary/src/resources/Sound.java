package resources;

public class Sound {
    private String id;
    //private XXX sound;
    
    public Sound(String id, String filePath) {
        loadSound(filePath);
    }
    
    public String getId() {
        return id;
    }
    
    /*public XXX getSound() {
        return sound;
    }*/
    
    private void loadSound(String filePath) {
        // TODO
    }
}
