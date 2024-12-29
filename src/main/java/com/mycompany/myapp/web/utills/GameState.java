package com.mycompany.myapp.web.utills;

import java.util.Map;

public class GameState {
    private boolean isComplete;
    private Map<String, Object> privateData;
    private Map<String, Object> publicData;

    // Getters and Setters
    public boolean isComplete() { return isComplete; }
    public void setComplete(boolean isComplete) { this.isComplete = isComplete; }
    public Map<String, Object> getPrivateData() { return privateData; }
    public void setPrivateData(Map<String, Object> privateData) { this.privateData = privateData; }
    public Map<String, Object> getPublicData() { return publicData; }
    public void setPublicData(Map<String, Object> publicData) { this.publicData = publicData; }
}
