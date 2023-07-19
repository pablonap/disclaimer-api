package com.fivvy.disclaimerapi.controller;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;
import com.fivvy.disclaimerapi.exception.ResourceNotFoundException;
import com.fivvy.disclaimerapi.service.DisclaimerService;
import com.fivvy.disclaimerapi.utils.DisclaimerUtils;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(DisclaimerController.class)
@ExtendWith(SpringExtension.class)
public class DisclaimerControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DisclaimerService disclaimerService;

    @InjectMocks
    private DisclaimerController disclaimerController;

    @Test
    void itShouldSaveADisclaimer() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldNotSaveADisclaimerWhenNameIsEmpty() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setName("");

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveADisclaimerWhenNameIsNull() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setName(null);

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveADisclaimerWhenTextIsEmpty() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setText("");

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveADisclaimerWhenTextIsNull() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setText(null);

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveADisclaimerWhenVersionIsEmpty() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setVersion("");

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldNotSaveADisclaimerWhenVersionIsNull() throws Exception {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setVersion(null);

        doNothing().when(disclaimerService).save(any(DisclaimerRequestDto.class));

        // when & then
        mockMvc.perform(post("/api/v1/disclaimers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldReturnDisclaimerResponseDtoWhenFound() throws Exception {
        // given
        final String DISCLAIMER_ID = "id1";
        DisclaimerResponseDto expectedDto = DisclaimerUtils.disclaimerResponseDtoOf();

        when(disclaimerService.getById(DISCLAIMER_ID)).thenReturn(expectedDto);

        // when & then
        mockMvc.perform(get("/api/v1/disclaimers/" + DISCLAIMER_ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value(expectedDto.getName()))
                .andExpect(jsonPath("$.text").value(expectedDto.getText()))
                .andExpect(jsonPath("$.version").value(expectedDto.getVersion()))
                .andExpect(jsonPath("$.createAt").value(expectedDto.getCreateAt()));
    }

    @Test
    void itShouldReturn404ForGetByIdWhenDisclaimerIdNotFound() throws Exception {
        // given
        String NON_EXISTENT_ID = "nonExistentId";
        doThrow(ResourceNotFoundException.class).when(disclaimerService)
                .getById(NON_EXISTENT_ID);

        // when & then
        mockMvc.perform(get("/api/v1/disclaimers/" + NON_EXISTENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void itShouldDeleteDisclaimerWhenFound() throws Exception {
        // given
        String DISCLAIMER_ID = "id1";

        // when & then
        mockMvc.perform(delete("/api/v1/disclaimers/" + DISCLAIMER_ID))
                .andExpect(status().isOk());

        verify(disclaimerService, times(1)).delete(DISCLAIMER_ID);
    }

    @Test
    void itShouldReturn404ForDeleteWhenDisclaimerIdNotFound() throws Exception {
        // given
        String NON_EXISTENT_ID = "nonExistentId";
        doThrow(ResourceNotFoundException.class)
                .when(disclaimerService).delete(NON_EXISTENT_ID);

        // when & then
        mockMvc.perform(delete("/api/v1/disclaimers/" + NON_EXISTENT_ID))
                .andExpect(status().isNotFound());
    }

    @Test
    void itShouldUpdateDisclaimerWhenFound() throws Exception {
        // given
        final String DISCLAIMER_ID = "id1";
        DisclaimerRequestDto requestDto = DisclaimerUtils.disclaimerRequestDtoOf();

        // when & then
        mockMvc.perform(put("/api/v1/disclaimers/" + DISCLAIMER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(requestDto)))
                .andExpect(status().isOk());
    }

    @Test
    void itShouldReturn400WhenInvalidDataProvided() throws Exception {
        // given
        String disclaimerId = "someValidId";
        DisclaimerRequestDto requestDto = new DisclaimerRequestDto();

        // when & then
        mockMvc.perform(put("/api/v1/disclaimers/" + disclaimerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(ControllerUtils.objectToJson(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void itShouldReturnAllDisclaimersWithoutFiltering() throws Exception {
        // given
        DisclaimerResponseDto disclaimerResponseDto1 = DisclaimerUtils.disclaimerResponseDtoOf();
        DisclaimerResponseDto disclaimerResponseDto2 = DisclaimerUtils.disclaimerResponseDtoOf();
        List<DisclaimerResponseDto> allDisclaimers = List.of(disclaimerResponseDto1, disclaimerResponseDto2);

        when(disclaimerService.findAll(null)).thenReturn(allDisclaimers);

        // when & then
        mockMvc.perform(get("/api/v1/disclaimers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void itShouldReturnDisclaimersWithTextFiltering() throws Exception {
        // given
        String TEXT_FROM_REQUEST = "some_text";

        DisclaimerResponseDto disclaimerResponseDto1 = DisclaimerUtils.disclaimerResponseDtoOf();
        DisclaimerResponseDto disclaimerResponseDto2 = DisclaimerUtils.disclaimerResponseDtoOf();
        List<DisclaimerResponseDto> filteredDisclaimers = List.of(disclaimerResponseDto1, disclaimerResponseDto2);

        when(disclaimerService.findAll(TEXT_FROM_REQUEST)).thenReturn(filteredDisclaimers);

        // when & then
        mockMvc.perform(get("/api/v1/disclaimers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void itShouldReturnEmptyListWhenNoDisclaimersFound() throws Exception {
        // given
        String TEXT_FROM_REQUEST = "some_text";

        List<DisclaimerResponseDto> emptyList = List.of();

        when(disclaimerService.findAll(TEXT_FROM_REQUEST)).thenReturn(emptyList);

        // when & then
        mockMvc.perform(get("/api/v1/disclaimers").param("text", TEXT_FROM_REQUEST))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isEmpty());
    }
}
