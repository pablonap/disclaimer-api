package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.AcceptanceRequestDto;
import com.fivvy.disclaimerapi.dto.AcceptanceResponseDto;
import com.fivvy.disclaimerapi.exception.ExceptionMessages;
import com.fivvy.disclaimerapi.exception.ResourceNotFoundException;
import com.fivvy.disclaimerapi.model.Acceptance;
import com.fivvy.disclaimerapi.repository.AcceptanceRepository;
import com.fivvy.disclaimerapi.repository.DisclaimerRepository;
import com.fivvy.disclaimerapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AcceptanceService implements IAcceptanceService {
    private final AcceptanceRepository acceptanceRepository;
    private final DisclaimerRepository disclaimerRepository;
    private final ModelMapper mapper;

    public AcceptanceService(AcceptanceRepository acceptanceRepository,
                             DisclaimerRepository disclaimerRepository,
                             ModelMapper mapper) {
        this.acceptanceRepository = acceptanceRepository;
        this.disclaimerRepository = disclaimerRepository;
        this.mapper = mapper;
    }

    @Override
    public void save(AcceptanceRequestDto dto) {
        disclaimerRepository.findById(dto.getDisclaimerId())
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        Acceptance acceptance = mapper.map(dto, Acceptance.class);
        String creationDateTime = Utils.getCurrentDateTime();
        acceptance.setCreateAt(creationDateTime);
        acceptanceRepository.save(acceptance);
    }

    @Override
    public List<AcceptanceResponseDto> findAll(String userId) {
        List<Acceptance> acceptances = null;

        if (userId != null && !userId.isBlank()) {
            acceptances = acceptanceRepository.findAllByUserId(userId);
        } else {
            acceptances = acceptanceRepository.findAll();
        }
        return acceptances
                .stream()
                .map(d -> mapper.map(d, AcceptanceResponseDto.class))
                .collect(Collectors.toList());
    }
}
