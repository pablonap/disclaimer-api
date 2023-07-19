package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;
import com.fivvy.disclaimerapi.exception.RequestValidationException;
import com.fivvy.disclaimerapi.exception.ResourceNotFoundException;
import com.fivvy.disclaimerapi.model.Disclaimer;
import com.fivvy.disclaimerapi.repository.DisclaimerRepository;
import com.fivvy.disclaimerapi.service.utils.DisclaimerUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DisclaimerServiceTests {
    @Mock
    private DisclaimerRepository disclaimerRepository;
    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private DisclaimerService underTest;

    @Test
    void itShouldReturnDisclaimerById() {
        // given
        final String DISCLAIMER_ID = "id1";
        Disclaimer disclaimer = DisclaimerUtils.disclaimerOf(DISCLAIMER_ID);

        DisclaimerResponseDto dto = DisclaimerUtils.disclaimerResponseDtoOf();

        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.of(disclaimer));
        when(mapper.map(disclaimer, DisclaimerResponseDto.class)).thenReturn(dto);

        // when
        DisclaimerResponseDto actual = underTest.getById(DISCLAIMER_ID);

        // then
        assertNotNull(actual);
        assertEquals(DISCLAIMER_ID, actual.getDisclaimerId());
    }

    @Test
    void itShouldThrowResourceNotFoundExceptionForFindByIdWithNonExistentId() {
        // given
        final String DISCLAIMER_ID = "nonExistentId";
        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.empty());

        // when and then
        assertThrows(ResourceNotFoundException.class, () -> underTest.getById(DISCLAIMER_ID));
    }

    @Test
    void itShouldDeleteDisclaimer() {
        // given
        final String DISCLAIMER_ID = "id1";

        Disclaimer disclaimer = DisclaimerUtils.disclaimerOf();

        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.of(disclaimer));

        // when
        underTest.delete(DISCLAIMER_ID);

        // then
        verify(disclaimerRepository, times(1)).delete(DISCLAIMER_ID);
    }

    @Test
    void itShouldThrowResourceNotFoundExceptionForDeleteWithNonExistentId() {
        // given
        final String DISCLAIMER_ID = "nonExistentId";
        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.empty());

        // when and then
        assertThrows(ResourceNotFoundException.class, () -> underTest.delete(DISCLAIMER_ID));
    }

    @Test
    void itShouldSaveADisclaimer() {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        final String CURRENT_DATE_TIME = "2023-07-19 12:00:00";

        Disclaimer mappedDisclaimer = DisclaimerUtils.mappedDisclaimerFromRequestOf();
        mappedDisclaimer.setCreateAt(CURRENT_DATE_TIME);

        when(mapper.map(dto, Disclaimer.class)).thenReturn(mappedDisclaimer);

        // when
        underTest.save(dto);

        // then
        verify(mapper, times(1)).map(dto, Disclaimer.class);
        verify(disclaimerRepository, times(1)).save(mappedDisclaimer);

        ArgumentCaptor<Disclaimer> disclaimerArgumentCaptor = ArgumentCaptor.forClass(Disclaimer.class);
        verify(disclaimerRepository).save(disclaimerArgumentCaptor.capture());
        Disclaimer capturedDisclaimer = disclaimerArgumentCaptor.getValue();

        assertThat(capturedDisclaimer.getDisclaimerId()).isNull();
        assertThat(capturedDisclaimer.getName()).isEqualTo(dto.getName());
        assertThat(capturedDisclaimer.getText()).isEqualTo(dto.getText());
        assertThat(capturedDisclaimer.getVersion()).isEqualTo(dto.getVersion());
    }

    @Test
    void itShouldUpdateDisclaimer() {
        // given
        final String UPDATED_VERSION = "2.0";
        final String DISCLAIMER_ID = "id1";

        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        dto.setVersion(UPDATED_VERSION);

        Disclaimer disclaimerFromDb = DisclaimerUtils.disclaimerFromDbOf();

        Disclaimer mappedDisclaimer = DisclaimerUtils.mappedDisclaimerFromRequestOf();
        mappedDisclaimer.setVersion(UPDATED_VERSION);

        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.of(disclaimerFromDb));
        when(mapper.map(dto, Disclaimer.class)).thenReturn(mappedDisclaimer);

        // when & then
        assertDoesNotThrow(() -> underTest.update(DISCLAIMER_ID, dto));
        verify(disclaimerRepository, times(1)).update(DISCLAIMER_ID, mappedDisclaimer);

        ArgumentCaptor<Disclaimer> disclaimerArgumentCaptor = ArgumentCaptor.forClass(Disclaimer.class);
        ArgumentCaptor<String> idArgumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(disclaimerRepository).update(idArgumentCaptor.capture(), disclaimerArgumentCaptor.capture());

        String capturedId = idArgumentCaptor.getValue();
        Disclaimer capturedDisclaimer = disclaimerArgumentCaptor.getValue();

        assertThat(capturedDisclaimer.getCreateAt()).isNotNull();
        assertThat(capturedDisclaimer.getUpdateAt()).isNotNull();
        assertThat(capturedId).isEqualTo(disclaimerFromDb.getDisclaimerId());
        assertThat(capturedDisclaimer.getVersion()).isNotEqualTo(disclaimerFromDb.getVersion());
    }

    @Test
    void itShouldThrowRequestValidationExceptionForUpdateWithSameData() {
        // given
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();
        Disclaimer disclaimerFromDb = DisclaimerUtils.disclaimerFromDbOf();
        Disclaimer mappedDisclaimer = DisclaimerUtils.mappedDisclaimerFromRequestOf();
        final String DISCLAIMER_ID = "id1";

        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.of(disclaimerFromDb));
        when(mapper.map(dto, Disclaimer.class)).thenReturn(mappedDisclaimer);

        // when & then
        assertThrows(RequestValidationException.class, () -> underTest.update(DISCLAIMER_ID, dto));
        verify(disclaimerRepository, never()).update(any(), any());
    }

    @Test
    void itShouldThrowResourceNotFoundExceptionForUpdateWithNonExistentDisclaimer() {
        // given
        final String DISCLAIMER_ID = "nonExistentId";
        DisclaimerRequestDto dto = DisclaimerUtils.disclaimerRequestDtoOf();

        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> underTest.update(DISCLAIMER_ID, dto));
        verify(disclaimerRepository, never()).update(any(), any());
    }

    @Test
    void itShouldReturnAListWithElementsForFindAllWithTextNotEmpty() {
        // given
        final String TEXT_FROM_REQUEST = "text1";
        final String TEXT_1 = "text0 text1 text2";
        final String TEXT_2 = "text0 text1";

        Disclaimer disclaimerFromDb1 = DisclaimerUtils.disclaimerFromDbOf();
        Disclaimer disclaimerFromDb2 = DisclaimerUtils.disclaimerFromDbOf();
        disclaimerFromDb1.setText(TEXT_1);
        disclaimerFromDb2.setText(TEXT_2);

        List<Disclaimer> disclaimersFromDb = List.of(disclaimerFromDb1, disclaimerFromDb2);

        when(disclaimerRepository.findAllByText(TEXT_FROM_REQUEST)).thenReturn(disclaimersFromDb);

        when(mapper.map(any(Disclaimer.class), eq(DisclaimerResponseDto.class)))
                .thenAnswer(invocation -> {
                    Disclaimer disclaimer = invocation.getArgument(0);

                    DisclaimerResponseDto responseDto = new DisclaimerResponseDto();
                    responseDto.setDisclaimerId(disclaimer.getDisclaimerId());
                    responseDto.setText(disclaimer.getText());
                    responseDto.setCreateAt(disclaimer.getCreateAt());
                    return responseDto;
                });

        // when
        List<DisclaimerResponseDto> result = underTest.findAll(TEXT_FROM_REQUEST);

        // then
        assertEquals(disclaimersFromDb.size(), result.size());
        result.forEach(d -> assertTrue(d.getText().contains(TEXT_FROM_REQUEST)));
    }

    @Test
    void itShouldReturnAListWithElementsForFindAllWithEmptyText() {
        // given
        final String TEXT_FROM_REQUEST = "";
        Disclaimer disclaimerFromDb1 = DisclaimerUtils.disclaimerFromDbOf();
        Disclaimer disclaimerFromDb2 = DisclaimerUtils.disclaimerFromDbOf();

        List<Disclaimer> disclaimersFromDb = List.of(disclaimerFromDb1, disclaimerFromDb2);

        when(disclaimerRepository.findAll()).thenReturn(disclaimersFromDb);

        when(mapper.map(any(Disclaimer.class), eq(DisclaimerResponseDto.class)))
                .thenAnswer(invocation -> {
                    Disclaimer disclaimer = invocation.getArgument(0);
                    DisclaimerResponseDto responseDto = new DisclaimerResponseDto();
                    responseDto.setDisclaimerId(disclaimer.getDisclaimerId());
                    responseDto.setText(disclaimer.getText());
                    responseDto.setCreateAt(disclaimer.getCreateAt());
                    return responseDto;
                });

        // when
        List<DisclaimerResponseDto> result = underTest.findAll(null);

        // then
        assertEquals(disclaimersFromDb.size(), result.size());
    }

    @Test
    void itShouldReturnAnEmptyListForFindAll() {
        // given
        final String TEXT_FROM_REQUEST = "";

        // when
        List<DisclaimerResponseDto> result = underTest.findAll(TEXT_FROM_REQUEST);

        // then
        assertTrue(result.isEmpty());
    }

}
