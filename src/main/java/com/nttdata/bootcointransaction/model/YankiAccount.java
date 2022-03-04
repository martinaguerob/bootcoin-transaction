package com.nttdata.bootcointransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YankiAccount {

    private String id;
    private String typeDoc;//dni, ce
    private String numberDoc;
    private String numberCelphone;
    private String imei;
    private String email;
    private String typeAccount; //yanki purse, cuenta bancaria
    private String numberAccountPurse;
    private String numberAccount; //Sea de bano o yanki purse
    private Date createdAt;
    private Date updateAt;
    private Boolean status;
}
