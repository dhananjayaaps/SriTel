package com.sritel.billingservice.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StripePaymentRequest {

    private String token;
    private String description;
    private int amount;

}
