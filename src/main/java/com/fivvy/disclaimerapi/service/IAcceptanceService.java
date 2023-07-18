package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.AcceptanceRequestDto;
import com.fivvy.disclaimerapi.dto.AcceptanceResponseDto;

import java.util.List;

public interface IAcceptanceService {
    void save(AcceptanceRequestDto dto);
    List<AcceptanceResponseDto> findAll(String userId);
}
