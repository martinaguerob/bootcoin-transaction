package com.nttdata.bootcointransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BootCoinAccount {

    private String id;
    private String typeDoc;
    private String numberDoc;
    private String celphone;
    private String email;
    private Integer numberPurse; //Número de cuenta del monedero
    private String linkedAccount; //cuenta vinculada
    private String numberBankAccount; // número de cuenta bancaria si tiene
    private Boolean status;
    private Date createdAt;

}
