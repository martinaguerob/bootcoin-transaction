package com.nttdata.bootcointransaction.config;

import com.nttdata.bootcointransaction.model.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

    private final WebClient.Builder webClientBuilder = WebClient.builder();

    public Mono<BootCoin> getBootCoinByServiceType(@PathVariable String serviceType){
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/bootcoin/type/"+serviceType)
                .retrieve()
                .bodyToMono(BootCoin.class);
    }

    public Mono<BankAccount> getBankAccountByNumberAccount(@PathVariable String numberAccount){
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/accounts/bank-account/number/"+numberAccount)
                .retrieve()
                .bodyToMono(BankAccount.class);
    }

    public Mono<BankAccount> updateBankAccount(BankAccount bankAccount, String id){
        System.out.println("Se llegó a updateBankAccount");
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8080/accounts/bank-account/update/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bankAccount))
                .retrieve()
                .bodyToMono(BankAccount.class);
    }

    public Mono<BootCoinPurse> updateBootCoinPurse(BootCoinPurse bootCoinPurse, String id){
        System.out.println("Se llegó a updateBankAccount");
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8080/bootcoin-purse/update/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(bootCoinPurse))
                .retrieve()
                .bodyToMono(BootCoinPurse.class);
    }

    public Mono<BootCoinAccount> getBootCoinAccountById(@PathVariable String id){
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/bootcoin-account/"+id)
                .retrieve()
                .bodyToMono(BootCoinAccount.class);
    }

    public Mono<BootCoinPurse> getBootCoinPurseByNumberPurse(@PathVariable Integer numberPurse){
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/bootcoin-purse/purse/"+numberPurse)
                .retrieve()
                .bodyToMono(BootCoinPurse.class);
    }

    public Mono<YankiAccount> getYankiAccountByNumberCelphone(@PathVariable String numberCelphone){
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/yanki-account/number/"+numberCelphone)
                .retrieve()
                .bodyToMono(YankiAccount.class);
    }

    public Mono<YankiPurse> getYankiPurseByNumberAccount(@PathVariable String numberAccount){
        return webClientBuilder.build()
                .get()
                .uri("http://localhost:8080/yanki-purse/number/"+numberAccount)
                .retrieve()
                .bodyToMono(YankiPurse.class);
    }

    public Mono<YankiPurse> updateYankiPurse(YankiPurse yankiPurse, String id){
        return webClientBuilder.build()
                .put()
                .uri("http://localhost:8080/yanki-purse/update/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(yankiPurse))
                .retrieve()
                .bodyToMono(YankiPurse.class);
    }
}
