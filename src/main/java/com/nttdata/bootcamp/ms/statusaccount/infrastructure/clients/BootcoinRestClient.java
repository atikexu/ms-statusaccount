package com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients;

import com.nttdata.bootcamp.ms.statusaccount.domain.dto.Bootcoin;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BootcoinRestClient {
   
    public Mono<Bootcoin> getFindId(Integer id) {
        WebClient webClient = WebClient.create("http://localhost:8096");
        return  webClient.get()
                .uri("/bootcoin/" + id)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Bootcoin.class);
    }
    
    public Mono<Bootcoin> createBootcoin(Bootcoin bootcoin) {
        WebClient webClient = WebClient.create("http://localhost:8096");
        return  webClient.post()
                .uri("/bootcoin")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bootcoin), Bootcoin.class)
                .retrieve()
                .bodyToMono(Bootcoin.class);
    }
    
    public Mono<Bootcoin> getFindPhone(String phone) {
        WebClient webClient = WebClient.create("http://localhost:8096");
        return  webClient.get()
                .uri("/bootcoin/findphone/" + phone)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .bodyToMono(Bootcoin.class);
    }
    
    public Mono<Bootcoin> getUpdate(Bootcoin bootcoin) {
        WebClient webClient = WebClient.create("http://localhost:8096");
        return  webClient.put()
                .uri("/bootcoin")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(Mono.just(bootcoin), Bootcoin.class)
                .retrieve()
                .bodyToMono(Bootcoin.class);
    }
    
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
