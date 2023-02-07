package com.nttdata.bootcamp.ms.statusaccount.domain.service;

import java.util.Date;

import com.nttdata.bootcamp.ms.statusaccount.domain.dto.Bootcoin;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.BootcoinResponse;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.Movement;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoin;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.ReqBootcoinResponse;
import com.nttdata.bootcamp.ms.statusaccount.domain.dto.WalletDto;
import com.nttdata.bootcamp.ms.statusaccount.enums.TypeCurrency;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.BankAccountRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.BootcoinRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.CreditRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.CustomerRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.DebitRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.ReqBootcoinRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.TransactionsRestClient;
import com.nttdata.bootcamp.ms.statusaccount.infrastructure.clients.WalletRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class StatusBootcoinServiceImpl implements  StatusBootcoinService {

    @Autowired
    BankAccountRestClient bankAccountRestClient;

    @Autowired
    CreditRestClient creditRestClient;

    @Autowired
    CustomerRestClient customerRestClient;
    
    @Autowired
    TransactionsRestClient transactionsRestClient;
    
    @Autowired
    DebitRestClient debitRestClient;
    
    @Autowired
    WalletRestClient walletRestClient;
    
    @Autowired
    BootcoinRestClient bootcampRestClient;
    
    @Autowired
    ReqBootcoinRestClient reqBootcampRestClient;

    @Override
    public Mono<BootcoinResponse> createBootcoin(Bootcoin bootcoin) {
        BootcoinResponse response = new BootcoinResponse();
        response.setMessage("Error creating Bootcoin");
        response.setBootcoin(bootcoin);
        bootcoin.setAmount(100f);
        return bootcampRestClient.createBootcoin(bootcoin).flatMap(w -> {
            response.setMessage("Bootcoin created");
            return Mono.just(response);
        });

    }

    @Override
    public Mono<ReqBootcoinResponse> createReqBootcoin(ReqBootcoin reqBootcoin) {
        ReqBootcoinResponse response = new ReqBootcoinResponse();
        response.setMessage("Error creating request");
        response.setReqBootcoin(reqBootcoin);
        return reqBootcampRestClient.createReqBootcoin(reqBootcoin).flatMap(r -> { 
            response.setMessage("Request created");
            response.setReqBootcoin(r);
            return Mono.just(response);
        });
    }
    
    @Override
    public Mono<ReqBootcoinResponse> acceptReqBootcoin(ReqBootcoin reqBootcoin) {
        ReqBootcoinResponse response = new ReqBootcoinResponse();
        response.setMessage("Error accept request");
        response.setReqBootcoin(reqBootcoin);
        return walletRestClient.getFindPhone(reqBootcoin.getPhone()).flatMap(r -> { 
            response.setMessage("Request created");
            return Mono.just(response);
        });
    }
    
    /*VALIDA EL REQUERIMIENTO LANZADO PARA LA COMPRA DE BOOTCOIN Y UNA VEZ VALIDADO ACTUALIZA LAS CUENTAS Y GUARDA LAS TRANSACCIONES*/
    
    @Override
    public Mono<BootcoinResponse> getValidateBootcoin(String phone, String idReq) {
        BootcoinResponse response = new BootcoinResponse();
        response.setMessage("User does not exist");
        return bootcampRestClient.getFindPhone(phone).flatMap(b -> {
            response.setMessage("User does not have Yanki");
            response.setBootcoin(b);
            return walletRestClient.getFindPhone(b.getPhone()).flatMap(y -> {
                return reqBootcampRestClient.getFindId(idReq).flatMap(req -> {
                    if (b.getNumBootcoin() > req.getNumBootcoin()) {
                        b.setNumBootcoin(b.getNumBootcoin() - req.getNumBootcoin());
                        response.setBootcoin(b);
                        return bootcampRestClient.getUpdate(b).flatMap(dat1 -> {
                            return bootcampRestClient.getFindId(req.getBootcoinId()).flatMap(q -> {
                            q.setNumBootcoin(q.getNumBootcoin() + req.getNumBootcoin());
                                return bootcampRestClient.getUpdate(q).flatMap(dat2 -> {
                                    req.setUser(b.getPhone());
                                    response.setMessage("Sucessful sale");
                                    return reqBootcampRestClient.getUpdate(req).flatMap(dat3 -> {
                                        Movement movement = new Movement();
                                        movement.setAccountId(b.getId());
                                        movement.setAmount(req.getAmount());
                                        movement.setCurrency(TypeCurrency.SOLES);
                                        movement.setCustomerId(b.getPhone());
                                        movement.setProductType("BOOTCOIN");
                                        movement.setTransactionDate(new Date());
                                        movement.setTransactionType("SALE");
                                        return transactionsRestClient.saveTransaction(movement).flatMap(t1 -> {
                                            movement.setAccountId(req.getBootcoinId());
                                            movement.setTransactionType("BUY");
                                            movement.setCustomerId(q.getPhone());
                                            return transactionsRestClient.saveTransaction(movement).flatMap(t2 -> {
                                            WalletDto wallet = new WalletDto();
                                            wallet.setAmount(req.getAmount());
                                            wallet.setWalletId(y.getId());
                                            wallet.setPhoneNumber(req.getPhone());
                                            wallet.setAvailableBalance(req.getAmount());
                                                return walletRestClient.walletDeposit(wallet).flatMap(w1 -> {
                                                    movement.setAccountId(y.getId());
                                                    movement.setTransactionType("DEPOSITO");
                                                    movement.setCustomerId(q.getPhone());
                                                    return transactionsRestClient.saveTransaction(movement).flatMap(t3 -> {
                                                        return Mono.just(response);
                                                    });                                                
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    }
                    response.setMessage("You don't have enough bootcoin");
                    return Mono.just(response);
                }).defaultIfEmpty(response);
            }).defaultIfEmpty(response);
        }).defaultIfEmpty(response);
    }

}
