package com.fivvy.disclaimerapi.controller;

import com.fivvy.disclaimerapi.dto.DisclaimerRequestDto;
import com.fivvy.disclaimerapi.dto.DisclaimerResponseDto;
import com.fivvy.disclaimerapi.service.DisclaimerService;
import com.fivvy.disclaimerapi.service.IDisclaimerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/disclaimers")
public class DisclaimerController {
    private final IDisclaimerService disclaimerService;

    public DisclaimerController(DisclaimerService disclaimerService) {
        this.disclaimerService = disclaimerService;
    }

    @PostMapping
    public void save(@RequestBody DisclaimerRequestDto request) {
        disclaimerService.save(request);
    }

    @GetMapping("{disclaimerId}")
    public DisclaimerResponseDto getById(@PathVariable("disclaimerId") String disclaimerId) {
        return disclaimerService.getById(disclaimerId);
    }

    @DeleteMapping("{disclaimerId}")
    public void delete(@PathVariable("disclaimerId") String disclaimerId) {
        disclaimerService.delete(disclaimerId);
    }

    @PutMapping("{disclaimerId}")
    public void update(
            @PathVariable("disclaimerId") String disclaimerId,
            @RequestBody DisclaimerRequestDto request) {
        disclaimerService.update(disclaimerId, request);
    }
}
