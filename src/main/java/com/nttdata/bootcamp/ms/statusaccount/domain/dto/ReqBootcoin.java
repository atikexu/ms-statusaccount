package com.nttdata.bootcamp.ms.statusaccount.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReqBootcoin {

	private String id;
    private Integer bootcoinId;
    private String phone;
    private Float amount;
    private String payType;
    private String user;
    private Integer numBootcoin;
}
