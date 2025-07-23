package com.crm.product.entities.dto;

import com.crm.product.enums.ReturnFeePayer;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ContractDto {

    private Float saleCompanyPercent;
    private Float salePartnerPercent;
    private Float damageCompanyCompensation;
    private Float lossCompanyCompensation;
    private Float partnerTakebackFeePercent;
    private Float rentCompanyPercent;
    private Float rentPartnerPercent;
    private Float returnFeePercent;
    private ReturnFeePayer returnFeePayer;
    private LocalDate validFrom;
    private LocalDate validTo;
    private String notes;
}
