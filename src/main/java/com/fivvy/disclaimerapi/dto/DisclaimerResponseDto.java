package com.fivvy.disclaimerapi.dto;

public class DisclaimerResponseDto {
    private String disclaimerId;

    private String name;

    private String text;

    private String version;

    private String createAt;

    public String getDisclaimerId() {
        return disclaimerId;
    }

    public void setDisclaimerId(String disclaimerId) {
        this.disclaimerId = disclaimerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
