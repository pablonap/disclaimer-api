package com.fivvy.disclaimerapi.service.utils;

import com.fivvy.disclaimerapi.dto.AcceptanceRequestDto;
import com.fivvy.disclaimerapi.dto.AcceptanceResponseDto;
import com.fivvy.disclaimerapi.model.Acceptance;

public final class AcceptanceUtils {
    private AcceptanceUtils() {}

    public static AcceptanceRequestDto acceptanceRequestDtoOf() {
        AcceptanceRequestDto dto = new AcceptanceRequestDto();
        dto.setDisclaimerId("id1");
        dto.setUserId("user1");

        return dto;
    }


    public static Acceptance mappedAcceptanceFromRequestOf() {
        Acceptance acceptance = new Acceptance();
        acceptance.setDisclaimerId("id1");
        acceptance.setUserId("user1");

        return acceptance;
    }

    public static Acceptance acceptanceFromDbOf() {
        Acceptance acceptance = new Acceptance();
        acceptance.setDisclaimerId("id1");
        acceptance.setUserId("user1");
        acceptance.setCreateAt("2023-07-19 12:00:00");

        return acceptance;
    }
}
