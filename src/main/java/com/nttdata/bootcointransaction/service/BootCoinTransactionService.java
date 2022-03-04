package com.nttdata.bootcointransaction.service;

import com.nttdata.bootcointransaction.entity.BootCoinTransaction;
import reactor.core.publisher.Mono;

public interface BootCoinTransactionService extends CrudService<BootCoinTransaction, String> {

    //Buscar por Id
    public Mono<BootCoinTransaction> getById(String id);

    //Grabar Compra al banco
    public Mono<BootCoinTransaction> saveBuysBank(BootCoinTransaction entity);

    //Grabar Compra
    public Mono<BootCoinTransaction> saveBuys(BootCoinTransaction entity);

    //Grabar Venta
    public Mono<BootCoinTransaction> saveSale(BootCoinTransaction entity);

    //Aprobar compra - venta
    public Mono<BootCoinTransaction> approveTransaction(String idTransaction, BootCoinTransaction entity);

    //Aprobar transacción - sistema
    public Mono<BootCoinTransaction> approveTransactionSystem(String idTransaction, String idBuyer);
}