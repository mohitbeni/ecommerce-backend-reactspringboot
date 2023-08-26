package com.ecomm.model;


import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PaymentInformation {
    @Column(name = "cardHolder_name")
    private String cardholderName;

    @Column(name = "card_number")
    private String cardNumber;

    @Column(name = "expiration_date")
    private LocalDate expirationTime;

    @Column(name = "cvv")
    private String cvv;
}
