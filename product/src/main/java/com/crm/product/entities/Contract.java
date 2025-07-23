package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.ContractStatus;
import com.crm.product.enums.ReturnFeePayer;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class Contract extends AbstractEntity {
    private LocalDate validFrom;
    private LocalDate validTo;

    @Enumerated(EnumType.STRING)
    private ContractStatus status;

    private String contractFilePath;
    // Percentages
    private Float saleCompanyPercent;
    private Float salePartnerPercent;
    private Float damageCompanyCompensation;
    private Float lossCompanyCompensation;
    private Float partnerTakebackFeePercent;
    private Float rentCompanyPercent;
    private Float rentPartnerPercent;
    private Float returnFeePercent;
    private String url;

    @Enumerated(EnumType.STRING)
    private ReturnFeePayer returnFeePayer;

    private String notes;
}
