package com.nttdata.bootcointransaction.service;

@FunctionalInterface
public interface Calculate {

    //Calcular conversión
    Double calcular(Double montoA, Double montoB);

}
