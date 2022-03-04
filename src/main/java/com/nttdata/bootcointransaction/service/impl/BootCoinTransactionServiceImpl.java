package com.nttdata.bootcointransaction.service.impl;

import com.nttdata.bootcointransaction.config.WebClientConfig;
import com.nttdata.bootcointransaction.entity.BootCoinTransaction;
import com.nttdata.bootcointransaction.model.*;
import com.nttdata.bootcointransaction.repository.BootCoinTransactionRepository;
import com.nttdata.bootcointransaction.service.BootCoinTransactionService;
import com.nttdata.bootcointransaction.service.Calculate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.Random;

@Service
public class BootCoinTransactionServiceImpl implements BootCoinTransactionService {

    @Autowired
    BootCoinTransactionRepository bootCoinTransactionRepository;
    private final WebClientConfig webClientConfig = new WebClientConfig();

    @Override
    public Flux<BootCoinTransaction> findAll() {
        return bootCoinTransactionRepository.findAll();
    }

    @Override
    public Mono<BootCoinTransaction> save(BootCoinTransaction entity) {
        entity.setCreatedAt(new Date());
        entity.setUpdateAt(new Date());
        entity.setStatus("Waiting");
        entity.setTransactionOk(false);
        return bootCoinTransactionRepository.save(entity);
    }

    @Override
    public Mono<BootCoinTransaction> update(BootCoinTransaction entity, String id) {
        return null;
    }

    @Override
    public Mono<BootCoinTransaction> deleteById(String s) {
        return null;
    }

    @Override
    public Mono<BootCoinTransaction> getById(String id) {
        return bootCoinTransactionRepository.findById(id);
    }

    @Override
    public Mono<BootCoinTransaction> saveBuysBank(BootCoinTransaction entity) {
        System.out.println("Guardar:" +entity.getTypeTransaction());
        Mono<BootCoin>getBootcoin = webClientConfig.getBootCoinByServiceType("BuysBank");
        return getBootcoin
                .flatMap(b -> {
                    Calculate conversion = (Double amount, Double rate) -> (amount + ((rate+100)/100))+6;
                    Double amountSoles = conversion.calcular(entity.getAmountBootCoin(), b.getRate());
                    entity.setIdSeller("banco");
                    entity.setTypeTransaction("BuysBank");
                    entity.setAmountSoles(amountSoles);
                    return save(entity);
                });
    }

    @Override
    public Mono<BootCoinTransaction> saveBuys(BootCoinTransaction entity) {
        System.out.println("Guardar:" +entity.getTypeTransaction());
        Mono<BootCoin>getBootcoin = webClientConfig.getBootCoinByServiceType("Buys");
        return getBootcoin
                .flatMap(b -> {
                    Calculate conversion = (Double amount, Double rate) -> (amount + ((rate+100)/100))+4;
                    Double amountSoles = conversion.calcular(entity.getAmountBootCoin(), b.getRate());
                    entity.setTypeTransaction("Buys");
                    entity.setAmountSoles(amountSoles);
                    return save(entity);
                });
    }


    @Override
    public Mono<BootCoinTransaction> saveSale(BootCoinTransaction entity) {
        System.out.println("Guardar:" +entity.getTypeTransaction());
        Mono<BootCoin>getBootcoin = webClientConfig.getBootCoinByServiceType("Sale");
        return getBootcoin
                .flatMap(b -> {
                    Calculate conversion = (Double amount, Double rate) -> (amount + ((rate+100)/100))+5;
                    Double amountSoles = conversion.calcular(entity.getAmountBootCoin(), b.getRate());
                    entity.setTypeTransaction("Sale");
                    entity.setAmountSoles(amountSoles);
                    return save(entity);
                });
    }

    //El usuario aprueba la transacción
    @Override
    public Mono<BootCoinTransaction> approveTransaction(String idTransaction, BootCoinTransaction entity) {
        Mono<BootCoinTransaction> transaction = bootCoinTransactionRepository.findById(idTransaction);
        return transaction
                .flatMap(t -> {
                    t.setStatus("Accepted");
                    int valRandom = this.rand();
                    t.setNumberTransaction(valRandom);
                    this.approveTransactionSystem(idTransaction, t.getIdBuyer()).subscribe();
                    return bootCoinTransactionRepository.save(t);
                });
    }

    //Aprobaciones automáticas por parte del sistema
    @Override
    public Mono<BootCoinTransaction> approveTransactionSystem(String idTransaction, String idBuyer) {
        Mono<BootCoinAccount> bootCoinAccount = webClientConfig.getBootCoinAccountById(idBuyer);
        return bootCoinAccount
                .flatMap(b->
                                b.getLinkedAccount().equals("yanki purse")
                                        ? validYankiAccount(b.getNumberPurse(), b.getCelphone(), idTransaction)
                                        : validBankAccount(b.getNumberPurse(), b.getNumberBankAccount(), idTransaction)
                        );
    }

    //Validación en cuenta yanki respecto al saldo
    //Se realiza la actualización de saldo en el monedero de yanki, el saldo en coins y actualiza las transacciones
    public Mono<BootCoinTransaction> validYankiAccount(Integer numberPurse, String celphone, String idTransaction){
        Mono<YankiAccount> yankiAccount = webClientConfig.getYankiAccountByNumberCelphone(celphone);
        return yankiAccount
                .flatMap(y -> {
                    Mono<BootCoinTransaction> transaction = bootCoinTransactionRepository.findById(idTransaction);
                    return transaction
                            .flatMap(t -> {
                                Mono<YankiPurse> yankiPurse = webClientConfig.getYankiPurseByNumberAccount(y.getNumberAccount());
                                return yankiPurse
                                        .filter(yp -> yp.getBalance() >= t.getAmountSoles())
                                        .flatMap(yp -> {
                                            //Descuento del monedero de yanki
                                            Calculate descAccount = (Double amount, Double balance) -> balance-amount;
                                            Double newBalanceBank = descAccount.calcular(t.getAmountSoles(), yp.getBalance());
                                            yp.setBalance(newBalanceBank);
                                            webClientConfig.updateYankiPurse(yp, yp.getId()).subscribe();

                                            //Actualizar coins
                                            this.addCoin(numberPurse, t.getAmountBootCoin()).subscribe();

                                            //Actualizar transacción
                                            t.setUpdateAt(new Date());
                                            t.setTransactionOk(true);
                                            return bootCoinTransactionRepository.save(t);
                                        });
                            });

                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));
    }

    //Validación en cuenta bancaria respecto al saldo
    //Se realiza la actualización de saldo en la cuenta bancaria, el saldo en coins y actualiza las transacciones
    public Mono<BootCoinTransaction> validBankAccount(Integer numberPurse, String numberAccount, String idTransaction){
        Mono<BankAccount> bankAccount =  webClientConfig.getBankAccountByNumberAccount(numberAccount);
        return bankAccount
                .flatMap(b -> {
                    Mono<BootCoinTransaction> transaction = bootCoinTransactionRepository.findById(idTransaction);
                    return transaction
                            .filter(t -> b.getBalance() >= t.getAmountSoles())
                            .flatMap(t -> {
                                //Descuento del banco
                                Calculate descAccount = (Double amount, Double balance) -> balance-amount;
                                Double newBalanceBank = descAccount.calcular(t.getAmountSoles(), b.getBalance());
                                b.setBalance(newBalanceBank);
                                webClientConfig.updateBankAccount(b, b.getId()).subscribe();

                                //Actualizar coins
                                this.addCoin(numberPurse, t.getAmountBootCoin()).subscribe();

                                //Actualizar transacción
                                t.setUpdateAt(new Date());
                                t.setTransactionOk(true);
                                return bootCoinTransactionRepository.save(t);
                            });
                }).switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NO_CONTENT)));

    }

    //Actualizar el saldo el monedero BootCoin
    public Mono<BootCoinPurse> addCoin(Integer numberPurse, Double amount){
        Mono<BootCoinPurse>bootCoinPurse = webClientConfig.getBootCoinPurseByNumberPurse(numberPurse);
        return bootCoinPurse
                .flatMap(b -> {
                    Calculate sumBootCoin = (Double coinNew, Double coinPresent) -> coinNew+coinPresent;
                    Double newCoin = sumBootCoin.calcular(b.getAmount(), amount);
                    b.setAmount(newCoin);
                    return webClientConfig.updateBootCoinPurse(b, b.getId());
                });
    }

    public int rand(){
        int random = new Random().nextInt(2147483647);
        return random;
    }
}
