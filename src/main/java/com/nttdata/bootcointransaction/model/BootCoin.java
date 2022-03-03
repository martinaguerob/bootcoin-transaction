package com.nttdata.bootcointransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootCoin {

    private String id;
    private String serviceType; // Buys, Sale
    private Double rate; // tasa de compra o venta
    private Date createdAt;
    private Boolean status;

}
