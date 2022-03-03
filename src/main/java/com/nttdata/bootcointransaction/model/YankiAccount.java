package com.nttdata.bootcointransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YankiAccount {

    private String id;
    private String typeDoc;
    private String numberDoc;
    private String numberCelphone;
    private String imei;
    private String email;
    private String typeAccount;
    private String numberAccount;
    private Date date;
    private Boolean status;
}
