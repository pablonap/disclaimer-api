package com.fivvy.disclaimerapi.dto;

public class AcceptanceResponseDto {
    private String disclaimerId;

    private String userId;

    private String createAt;

    public AcceptanceResponseDto() {
    }

    public String getDisclaimerId() {
        return disclaimerId;
    }

    public void setDisclaimerId(String disclaimerId) {
        this.disclaimerId = disclaimerId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }
}
