package com.fivvy.disclaimerapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class AcceptanceRequestDto {
    @NotNull
    @NotBlank
    private String disclaimerId;

    @NotNull
    @NotBlank
    private String userId;

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
}
