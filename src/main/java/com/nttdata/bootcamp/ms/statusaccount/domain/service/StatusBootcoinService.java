package com.nttdata.bootcamp.ms.statusaccount.domain.service;

import com.nttdata.bootcamp.ms.statusaccount.domain.dto.Bootcoin;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.BootcoinResponse;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoin;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoinResponse;
import reactor.core.publisher.Mono;

public interface StatusBootcoinService {

    Mono<BootcoinResponse> createBootcoin(Bootcoin bootcoin);

    Mono<ReqBootcoinResponse> createReqBootcoin(ReqBootcoin reqBootcoin);

	Mono<ReqBootcoinResponse> acceptReqBootcoin(ReqBootcoin reqBootcoin);

	Mono<BootcoinResponse> getValidateBootcoin(String phone, String idReq);


}
