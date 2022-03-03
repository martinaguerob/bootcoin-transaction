package com.nttdata.bootcointransaction.repository;

import com.nttdata.bootcointransaction.entity.BootCoinTransaction;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface BootCoinTransactionRepository extends ReactiveMongoRepository<BootCoinTransaction, String> {
}
