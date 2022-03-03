package com.nttdata.bootcointransaction.controller;

import com.nttdata.bootcointransaction.entity.BootCoinTransaction;
import com.nttdata.bootcointransaction.service.BootCoinTransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bootcoin-transaction")
public class BootCoinTransactionController {

    @Autowired
    BootCoinTransactionService bootCoinTransactionService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Flux<BootCoinTransaction> getTransfers(){
        System.out.println("Listar transferencias");
        return bootCoinTransactionService.findAll();
    }

    @PostMapping("/buys-bank")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BootCoinTransaction> saveBuysBank(@RequestBody BootCoinTransaction bootCoinTransaction){
        return bootCoinTransactionService.saveBuysBank(bootCoinTransaction);
    }
    @PostMapping("/buys")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BootCoinTransaction> saveBuys(@RequestBody BootCoinTransaction bootCoinTransaction){
        return bootCoinTransactionService.saveBuys(bootCoinTransaction);
    }
    @PostMapping("/sale")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BootCoinTransaction> saveSale(@RequestBody BootCoinTransaction bootCoinTransaction){
        return bootCoinTransactionService.saveSale(bootCoinTransaction);
    }
}
