package com.nttdata.bootcamp.ms.statusaccount.application.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.bootcamp.ms.statusaccount.domain.dto.Bootcoin;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.BootcoinResponse;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoin;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoinResponse;
import com.nttdata.bootcamp.ms.statusaccount.domain.service.StatusBootcoinService;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(value = "/status")
@RequiredArgsConstructor
public class StatusBootcoinController {

    private final StatusBootcoinService statusBootcoinService;

    @PostMapping("/bootcointcreate")
    public Mono<BootcoinResponse> createBootcoin(@RequestBody Bootcoin bootcoin) {
        return statusBootcoinService.createBootcoin(bootcoin);
    }
    
    @PostMapping("/reqbootcointcreate")
    public Mono<ReqBootcoinResponse> createReqBootcoin(@RequestBody ReqBootcoin reqBootcoin) {
        return statusBootcoinService.createReqBootcoin(reqBootcoin);
    }

    @PostMapping("/validatereq")
    public Mono<BootcoinResponse> getValidateBootcoin(@RequestBody ReqBootcoin reqBootcoin) {
        return statusBootcoinService.getValidateBootcoin(reqBootcoin.getPhone(), reqBootcoin.getId());
    }
    
}
