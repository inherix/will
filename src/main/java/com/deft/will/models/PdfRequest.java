package com.deft.will.models;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


import java.util.List;

@Data
public class PdfRequest {

    @NotBlank
    private String providerName;
    private String providerAddress;
    private String providerContact;
    private List<Beneficiaries> beneficiaries;
    private String specialInstructions;
}
