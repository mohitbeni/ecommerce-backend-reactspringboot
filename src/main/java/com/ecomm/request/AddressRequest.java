package com.ecomm.request;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {

    private String firstName;

    private String lastName;

    private String streetAddress;

    private String city;

    private String state;

    private String zipCode;

    private String mobile;



}
