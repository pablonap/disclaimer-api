package com.fivvy.disclaimerapi.controller;

import com.fivvy.disclaimerapi.dto.AcceptanceRequestDto;
import com.fivvy.disclaimerapi.dto.AcceptanceResponseDto;
import com.fivvy.disclaimerapi.service.AcceptanceService;
import com.fivvy.disclaimerapi.utils.AcceptanceUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AcceptanceController.class)
@ExtendWith(SpringExtension.class)
public class AcceptanceControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcceptanceService acceptanceService;

    @InjectMocks
    private AcceptanceController acceptanceController;

    @Test
    void itShouldSaveAnAcceptance() throws Exception {
        // given
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();

        doNothing().when(acceptanceService).save(any(AcceptanceRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/acceptances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldNotSaveAnAcceptanceWhenDisclaimerIdIsEmpty() throws Exception {
        // given
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();
        dto.setDisclaimerId("");

        doNothing().when(acceptanceService).save(any(AcceptanceRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/acceptances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveAnAcceptanceWhenDisclaimerIdIsNull() throws Exception {
        // given
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();
        dto.setDisclaimerId(null);

        doNothing().when(acceptanceService).save(any(AcceptanceRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/acceptances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveAnAcceptanceWhenUserIdIsEmpty() throws Exception {
        // given
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();
        dto.setUserId("");

        doNothing().when(acceptanceService).save(any(AcceptanceRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/acceptances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveAnAcceptanceWhenUserIdIsNull() throws Exception {
        // given
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();
        dto.setUserId(null);

        doNothing().when(acceptanceService).save(any(AcceptanceRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/acceptances")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldReturnAllAcceptancesWithoutFiltering() throws Exception {
        // given
        AcceptanceResponseDto acceptanceResponseDto1 = AcceptanceUtils.acceptanceResponseDtoOf();
        AcceptanceResponseDto acceptanceResponseDto2 = AcceptanceUtils.acceptanceResponseDtoOf();
        List<AcceptanceResponseDto> allAcceptances = List.of(acceptanceResponseDto1, acceptanceResponseDto2);

        when(acceptanceService.findAll(null)).thenReturn(allAcceptances);

        // when & then
        mockMvc.perform(get("/api/v1/acceptances"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void itShouldReturnAcceptancesWithTextFiltering() throws Exception {
        // given
        String USER_ID_FROM_REQUEST = "userid1";

        AcceptanceResponseDto acceptanceResponseDto1 = AcceptanceUtils.acceptanceResponseDtoOf();
        AcceptanceResponseDto acceptanceResponseDto2 = AcceptanceUtils.acceptanceResponseDtoOf();
        List<AcceptanceResponseDto> filteredDisclaimers = List.of(acceptanceResponseDto1, acceptanceResponseDto2);

        when(acceptanceService.findAll(USER_ID_FROM_REQUEST)).thenReturn(filteredDisclaimers);

        // when & then
        mockMvc.perform(get("/api/v1/acceptances").param("userId", USER_ID_FROM_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void itShouldReturnEmptyListWhenNoAcceptancesFound() throws Exception {
        // given
        String USER_ID_FROM_REQUEST = "userid1";

        List<AcceptanceResponseDto> emptyList = List.of();

        when(acceptanceService.findAll(USER_ID_FROM_REQUEST)).thenReturn(emptyList);

        // when & then
        mockMvc.perform(get("/api/v1/acceptances").param("userId", USER_ID_FROM_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }
}
