package com.mycompany.myapp.web.utills;

import java.util.List;

public class SpinRequest {
    private GameState gameState;
    private List<Object> command;
    private String forceConfig;
    private String currency;
    private Config config;

    // Getters and Setters
    public GameState getGameState() { return gameState; }
    public void setGameState(GameState gameState) { this.gameState = gameState; }
    public List<Object> getCommand() { return command; }
    public void setCommand(List<Object> command) { this.command = command; }
    public String getForceConfig() { return forceConfig; }
    public void setForceConfig(String forceConfig) { this.forceConfig = forceConfig; }
    public String getCurrency() { return currency; }
    public void setCurrency(String currency) { this.currency = currency; }
    public Config getConfig() { return config; }
    public void setConfig(Config config) { this.config = config; }
}

