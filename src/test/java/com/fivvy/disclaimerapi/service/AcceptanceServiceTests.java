package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.AcceptanceRequestDto;
import com.fivvy.disclaimerapi.dto.AcceptanceResponseDto;
import com.fivvy.disclaimerapi.exception.ResourceNotFoundException;
import com.fivvy.disclaimerapi.model.Acceptance;
import com.fivvy.disclaimerapi.model.Disclaimer;
import com.fivvy.disclaimerapi.repository.AcceptanceRepository;
import com.fivvy.disclaimerapi.repository.DisclaimerRepository;
import com.fivvy.disclaimerapi.utils.AcceptanceUtils;
import com.fivvy.disclaimerapi.utils.DisclaimerUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AcceptanceServiceTests {
    @Mock
    private DisclaimerRepository disclaimerRepository;

    @Mock
    private AcceptanceRepository acceptanceRepository;
    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private AcceptanceService underTest;

    @Test
    void itShouldSaveAnAcceptance() {
        // given
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();
        Disclaimer disclaimerFromDb = DisclaimerUtils.disclaimerFromDbOf();
        final String CURRENT_DATE_TIME = "2023-07-19 12:00:00";
        final String DISCLAIMER_ID = "id1";

        Acceptance mappedAcceptance = AcceptanceUtils.mappedAcceptanceFromRequestOf();
        mappedAcceptance.setCreateAt(CURRENT_DATE_TIME);

        when(mapper.map(dto, Acceptance.class)).thenReturn(mappedAcceptance);
        when(disclaimerRepository.findById(DISCLAIMER_ID)).thenReturn(Optional.of(disclaimerFromDb));

        // when
        underTest.save(dto);

        // then
        verify(mapper, times(1)).map(dto, Acceptance.class);
        verify(acceptanceRepository, times(1)).save(mappedAcceptance);

        ArgumentCaptor<Acceptance> acceptanceArgumentCaptor = ArgumentCaptor.forClass(Acceptance.class);
        verify(acceptanceRepository).save(acceptanceArgumentCaptor.capture());
        Acceptance capturedAcceptance = acceptanceArgumentCaptor.getValue();

        assertThat(capturedAcceptance.getUserId()).isEqualTo(dto.getUserId());
        assertThat(capturedAcceptance.getCreateAt()).isNotNull();
    }

    @Test
    void itShouldThrowResourceNotFoundExceptionForSaveWithNonExistentDisclaimer() {
        // given
        final String DISCLAIMER_ID = "nonExistentId";
        AcceptanceRequestDto dto = AcceptanceUtils.acceptanceRequestDtoOf();

        when(disclaimerRepository.findById(dto.getDisclaimerId())).thenReturn(Optional.empty());

        // when & then
        assertThrows(ResourceNotFoundException.class, () -> underTest.save(dto));
        verify(acceptanceRepository, never()).save(any());
    }

    @Test
    void itShouldReturnAListWithElementsForFindAllWithUserIdNotEmpty() {
        // given
        final String USER_ID_FROM_REQUEST = "user1";

        Acceptance acceptanceFromDb = AcceptanceUtils.acceptanceFromDbOf();
        List<Acceptance> acceptancesFromDb = List.of(acceptanceFromDb);

        when(acceptanceRepository.findAllByUserId(USER_ID_FROM_REQUEST)).thenReturn(acceptancesFromDb);

        when(mapper.map(any(Acceptance.class), eq(AcceptanceResponseDto.class)))
                .thenAnswer(invocation -> {
                    Acceptance acceptance = invocation.getArgument(0);

                    AcceptanceResponseDto responseDto = new AcceptanceResponseDto();
                    responseDto.setDisclaimerId(acceptance.getDisclaimerId());
                    responseDto.setUserId(acceptance.getUserId());
                    responseDto.setCreateAt(acceptance.getCreateAt());
                    return responseDto;
                });

        // when
        List<AcceptanceResponseDto> result = underTest.findAll(USER_ID_FROM_REQUEST);

        // then
        assertEquals(acceptancesFromDb.size(), result.size());
    }

    @Test
    void itShouldReturnAListWithElementsForFindAllWithEmptyUserId() {
        // given
        Acceptance acceptanceFromDb1 = AcceptanceUtils.acceptanceFromDbOf();
        Acceptance acceptanceFromDb2 = AcceptanceUtils.acceptanceFromDbOf();
        List<Acceptance> acceptancesFromDb = List.of(acceptanceFromDb1, acceptanceFromDb2);

        when(acceptanceRepository.findAll()).thenReturn(acceptancesFromDb);

        when(mapper.map(any(Acceptance.class), eq(AcceptanceResponseDto.class)))
                .thenAnswer(invocation -> {
                    Acceptance acceptance = invocation.getArgument(0);

                    AcceptanceResponseDto responseDto = new AcceptanceResponseDto();
                    responseDto.setDisclaimerId(acceptance.getDisclaimerId());
                    responseDto.setUserId(acceptance.getUserId());
                    responseDto.setCreateAt(acceptance.getCreateAt());
                    return responseDto;
                });

        // when
        List<AcceptanceResponseDto> result = underTest.findAll(null);

        // then
        assertEquals(acceptancesFromDb.size(), result.size());
    }

    @Test
    void itShouldReturnAnEmptyListForFindAll() {
        // given
        final String USER_ID_FROM_REQUEST = "";

        // when
        List<AcceptanceResponseDto> result = underTest.findAll(USER_ID_FROM_REQUEST);

        // then
        assertTrue(result.isEmpty());
    }
}
