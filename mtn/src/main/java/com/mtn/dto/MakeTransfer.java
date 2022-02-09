package com.mtn.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MakeTransfer {

    private String amount;
    private String currency;
    private String externalId;
    private Payer payee;
    private String payerMessage;
    private String payeeNote;

}
