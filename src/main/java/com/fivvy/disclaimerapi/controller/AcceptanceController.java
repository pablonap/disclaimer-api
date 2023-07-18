package com.fivvy.disclaimerapi.controller;

import com.fivvy.disclaimerapi.dto.AcceptanceRequestDto;
import com.fivvy.disclaimerapi.dto.AcceptanceResponseDto;
import com.fivvy.disclaimerapi.service.AcceptanceService;
import com.fivvy.disclaimerapi.service.IAcceptanceService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/acceptances")
public class AcceptanceController {
    private final IAcceptanceService acceptanceService;

    public AcceptanceController(AcceptanceService acceptanceService) {
        this.acceptanceService = acceptanceService;
    }

    @PostMapping
    public void save(@Valid @RequestBody AcceptanceRequestDto request) {
        acceptanceService.save(request);
    }

    @GetMapping
    public List<AcceptanceResponseDto> findAll(@RequestParam(value="userId", required = false) String userId) {
        return acceptanceService.findAll(userId);
    }
}
