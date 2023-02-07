package com.nttdata.bootcamp.ms.statusaccount.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bootcoin {

    private Integer id;
    private String documentType;
    private String documentNumber;
    private String phone;
    private String email;
    private Float amount;
    private String idCustomer;
    private Integer numBootcoin;
}
