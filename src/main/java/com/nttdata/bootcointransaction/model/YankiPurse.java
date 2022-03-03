package com.nttdata.bootcointransaction.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class YankiPurse {

    private String id;
    private Double balance;
    private String numberAccount;
    private Date createdAt;
    private Boolean status;

}
