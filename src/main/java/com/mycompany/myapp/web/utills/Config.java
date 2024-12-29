package com.mycompany.myapp.web.utills;

import java.util.List;

public class Config {
    private List<Double> allowedStake;
    private double defaultBet;
    private double maxBet;
    private double maxWinCapping;
    private double minBet;

    // Getters and Setters
    public List<Double> getAllowedStake() { return allowedStake; }
    public void setAllowedStake(List<Double> allowedStake) { this.allowedStake = allowedStake; }
    public double getDefaultBet() { return defaultBet; }
    public void setDefaultBet(double defaultBet) { this.defaultBet = defaultBet; }
    public double getMaxBet() { return maxBet; }
    public void setMaxBet(double maxBet) { this.maxBet = maxBet; }
    public double getMaxWinCapping() { return maxWinCapping; }
    public void setMaxWinCapping(double maxWinCapping) { this.maxWinCapping = maxWinCapping; }
    public double getMinBet() { return minBet; }
    public void setMinBet(double minBet) { this.minBet = minBet; }
}
