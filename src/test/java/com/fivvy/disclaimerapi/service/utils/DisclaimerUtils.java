package com.fivvy.disclaimerapi.service.utils;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;
import com.fivvy.disclaimerapi.model.Disclaimer;

public final class DisclaimerUtils {
    private DisclaimerUtils() {}

    public static DisclaimerRequestDto disclaimerRequestDtoOf() {
        DisclaimerRequestDto dto = new DisclaimerRequestDto();
        dto.setName("luca prodan");
        dto.setText("disc 1");
        dto.setVersion("1.0");

        return dto;
    }

    public static DisclaimerResponseDto disclaimerResponseDtoOf() {
        DisclaimerResponseDto dto = new DisclaimerResponseDto();
        dto.setDisclaimerId("id1");
        dto.setName("luca prodan");
        dto.setText("disc 1");
        dto.setVersion("1.0");

        return dto;
    }

    public static Disclaimer disclaimerOf() {
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setDisclaimerId("id1");
        disclaimer.setName("luca prodan");
        disclaimer.setText("disc 1");
        disclaimer.setVersion("1.0");

        return disclaimer;
    }

    public static Disclaimer mappedDisclaimerFromRequestOf() {
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setName("luca prodan");
        disclaimer.setText("disc 1");
        disclaimer.setVersion("1.0");

        return disclaimer;
    }

    public static Disclaimer disclaimerFromDbOf() {
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setDisclaimerId("id1");
        disclaimer.setName("luca prodan");
        disclaimer.setText("disc 1");
        disclaimer.setVersion("1.0");
        disclaimer.setCreateAt("2023-07-19 12:00:00");

        return disclaimer;
    }

    public static Disclaimer disclaimerOf(String disclaimerId) {
        Disclaimer disclaimer = new Disclaimer();
        disclaimer.setDisclaimerId(disclaimerId);
        disclaimer.setName("luca prodan");
        disclaimer.setText("disc 1");
        disclaimer.setVersion("1.0");

        return disclaimer;
    }
}
