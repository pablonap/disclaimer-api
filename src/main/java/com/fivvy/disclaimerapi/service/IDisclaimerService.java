package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;

import java.util.List;

public interface IDisclaimerService {
    void save(DisclaimerRequestDto dto);
    DisclaimerResponseDto getById(String disclaimerId);
    void delete(String disclaimerId);
    void update(String disclaimerId, DisclaimerRequestDto disclaimer);
    List<DisclaimerResponseDto> findAll(String text);
}
