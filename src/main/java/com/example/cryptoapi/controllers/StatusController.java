package com.example.cryptoapi.controllers;

import com.example.cryptoapi.dtos.status.GeneralStatusDto;
import com.example.cryptoapi.services.StatusService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/status")
public class StatusController {

    private final StatusService statusService;

    public StatusController(StatusService statusService) {
        this.statusService = statusService;
    }

    @GetMapping("")
    ResponseEntity<GeneralStatusDto> getGeneralStatus(){
        GeneralStatusDto generalStatusDto= statusService.getGeneralStatus();
        return ResponseEntity.ok(generalStatusDto);
    }
}
