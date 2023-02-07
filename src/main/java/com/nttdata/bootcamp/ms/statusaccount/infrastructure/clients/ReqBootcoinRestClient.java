package com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients;

import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoin;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class ReqBootcoinRestClient {
   
    public Mono<ReqBootcoin> getFindId(String id) {
        WebClient webClient = WebClient.create("http://localhost:8097");
        return  webClient.get()
                .uri("/requestbootcoin/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ReqBootcoin.class);
    }
    
    public Mono<ReqBootcoin> createReqBootcoin(ReqBootcoin bootcoin) {
        WebClient webClient = WebClient.create("http://localhost:8097");
        return  webClient.post()
                .uri("/requestbootcoin")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bootcoin), ReqBootcoin.class)
                .retrieve()
                .bodyToMono(ReqBootcoin.class);
    }
    
    public Mono<ReqBootcoin> getFindPhone(String id) {
        WebClient webClient = WebClient.create("http://localhost:8097");
        return  webClient.get()
                .uri("/requestbootcoin/findphone/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(ReqBootcoin.class);
    }
    
    public Mono<ReqBootcoin> getUpdate(ReqBootcoin bootcoin) {
        WebClient webClient = WebClient.create("http://localhost:8097");
        return  webClient.put()
                .uri("/requestbootcoin")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bootcoin), ReqBootcoin.class)
                .retrieve()
                .bodyToMono(ReqBootcoin.class);
    }
    
//    public Mono<ReqBootcoin> getFindPhone(String id) {
//        WebClient webClient = WebClient.create("http://localhost:8097");
//        return  webClient.get()
//                .uri("/requestbootcoin/findphone/" + id)
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .retrieve()
//                .bodyToMono(ReqBootcoin.class);
//    }
    
//    public Mono<Bootcoin> walletDeposit(Bootcoin bootcoin){
//        WebClient webClient = WebClient.create("http://localhost:8096");
//        return  webClient.post()
//                .uri("/monedero/deposit")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(bootcoin), Bootcoin.class)
//                .retrieve()
//                .bodyToMono(Bootcoin.class);
//    }
//    
//    public Mono<Wallet> walletPay(WalletDto wallet){
//    	WebClient webClient = WebClient.create("http://localhost:8094");
//        return  webClient.post()
//                .uri("/monedero/payment")
//                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
//                .body(Mono.just(wallet), WalletDto.class)
//                .retrieve()
//                .bodyToMono(Wallet.class);
//    }
}
