package com.hzdy.entity;

public class NoFlow {
    private int id;
    private String ruleName;
    private String originIp;
    private String goalIp;
    private String originIpMask;
    private String goalIpMask;
    private String goalADSL;
    private String protocol;
    private int interval;
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public NoFlow() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getOriginIp() {
        return originIp;
    }

    public void setOriginIp(String originIp) {
        this.originIp = originIp;
    }

    public String getGoalIp() {
        return goalIp;
    }

    public void setGoalIp(String goalIp) {
        this.goalIp = goalIp;
    }

    public String getOriginIpMask() {
        return originIpMask;
    }

    public void setOriginIpMask(String originIpMask) {
        this.originIpMask = originIpMask;
    }

    public String getGoalIpMask() {
        return goalIpMask;
    }

    public void setGoalIpMask(String goalIpMask) {
        this.goalIpMask = goalIpMask;
    }

    public String getGoalADSL() {
        return goalADSL;
    }

    public void setGoalADSL(String goalADSL) {
        this.goalADSL = goalADSL;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    @Override
    public String toString() {
        return "NoFlow [id=" + id + ", ruleName=" + ruleName + ", originIp=" + originIp + ", goalIp=" + goalIp
                + ", originIpMask=" + originIpMask + ", goalIpMask=" + goalIpMask + ", goalADSL=" + goalADSL
                + ", protocol=" + protocol + ", interval=" + interval + "]";
    }

}
