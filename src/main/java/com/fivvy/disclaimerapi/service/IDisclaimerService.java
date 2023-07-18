package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;
import com.fivvy.disclaimerapi.model.Disclaimer;

public interface IDisclaimerService {
    void save(DisclaimerRequestDto dto);
    DisclaimerResponseDto getById(String disclaimerId);
    void delete(String disclaimerId);
    void update(String disclaimerId, DisclaimerRequestDto disclaimer);
}
