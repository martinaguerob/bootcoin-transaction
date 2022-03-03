package com.nttdata.bootcointransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootCoinPurse {
    private String id;
    private Integer numberPurse; //Número de cuenta del monedero
    private Double amount; //Monto
    private Date createdAt;
    private Boolean status;
}
