package com.example.cryptoapi.dtos.status;

import com.example.cryptoapi.models.Status;
import org.springframework.stereotype.Service;

@Service
public class StatusDtoMapper {
    public StatusDto map(Status status){
        StatusDto dto = new StatusDto();
        dto.setId(status.getId());
        dto.setCurrentAmount(status.getCurrentAmount());
        dto.setCurrentProfit(status.getCurrentProfit());
        dto.setTotalCurrencyValue(status.getTotalCurrencyValue());
        return dto;
    }

}
