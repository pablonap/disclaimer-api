package com.fivvy.disclaimerapi.service;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;
import com.fivvy.disclaimerapi.exception.ExceptionMessages;
import com.fivvy.disclaimerapi.exception.RequestValidationException;
import com.fivvy.disclaimerapi.exception.ResourceNotFoundException;
import com.fivvy.disclaimerapi.model.Disclaimer;
import com.fivvy.disclaimerapi.repository.DisclaimerRepository;
import com.fivvy.disclaimerapi.utils.Utils;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DisclaimerService implements IDisclaimerService {
    private final DisclaimerRepository disclaimerRepository;
    private final ModelMapper mapper;

    public DisclaimerService(DisclaimerRepository disclaimerRepository, ModelMapper modelMapper) {
        this.disclaimerRepository = disclaimerRepository;
        this.mapper = modelMapper;
    }

    @Override
    public void save(DisclaimerRequestDto dto) {
        Disclaimer disclaimer = mapper.map(dto, Disclaimer.class);
        String creationDateTime = Utils.getCurrentDateTime();
        disclaimer.setCreateAt(creationDateTime);
        disclaimerRepository.save(disclaimer);
    }

    @Override
    public DisclaimerResponseDto getById(String disclaimerId) {
        Disclaimer disclaimer = disclaimerRepository.findById(disclaimerId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        return mapper.map(disclaimer, DisclaimerResponseDto.class);
    }

    @Override
    public void delete(String disclaimerId) {
        disclaimerRepository.findById(disclaimerId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        disclaimerRepository.delete(disclaimerId);
    }

    @Override
    public void update(String disclaimerId, DisclaimerRequestDto dto) {
        Disclaimer disclaimer = disclaimerRepository.findById(disclaimerId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.RESOURCE_NOT_FOUND.getMessage()));

        Disclaimer mappedDisclaimer = mapper.map(dto, Disclaimer.class);
        boolean areEquals = disclaimer.isEqualExcludingDates(mappedDisclaimer);
        if (areEquals) {
            throw new RequestValidationException(ExceptionMessages.NO_DATA_CHANGES_FOUND.getMessage());
        }

        mappedDisclaimer.setDisclaimerId(disclaimer.getDisclaimerId());
        mappedDisclaimer.setCreateAt(disclaimer.getCreateAt());
        String updateDateTime = Utils.getCurrentDateTime();
        mappedDisclaimer.setUpdateAt(updateDateTime);

        disclaimerRepository.update(disclaimerId, mappedDisclaimer);
    }

    @Override
    public List<DisclaimerResponseDto> findAll(String text) {
        List<Disclaimer> disclaimers = null;

        if (text != null && !text.isBlank()) {
            disclaimers = disclaimerRepository.findAllByText(text);
        } else {
            disclaimers = disclaimerRepository.findAll();
        }
        return disclaimers
                .stream()
                .map(d -> mapper.map(d, DisclaimerResponseDto.class))
                .collect(Collectors.toList());
    }
}
