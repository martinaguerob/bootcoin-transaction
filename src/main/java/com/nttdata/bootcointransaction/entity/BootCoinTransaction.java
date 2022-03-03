package com.nttdata.bootcointransaction.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@Document(collection = "bootcoin-transaction")
public class BootCoinTransaction {

    @Id
    private String id;
    private String typeTransaction; //Buys, Sale
    private String idBuyer;
    private String idSeller;
    private Double amountBootCoin;
    private Double amountSoles;
    private String status; //Accepted, Waiting, Denied
    private Integer numberTransaction;
    private Boolean transactionOk;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createdAt;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date updateAt;
}