package com.crm.partner.entities;

import com.crm.partner.entities.model.AbstractEntity;
import com.crm.partner.enums.ContractStatus;
import com.crm.partner.enums.ReturnFeePayer;
import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
@Table(name = "partner_contracts")
public class PartnerContract extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "partner_id")
    private Partner partner;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;
    private String productId;

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

    @Enumerated(EnumType.STRING)
    private ReturnFeePayer returnFeePayer;

    private String notes;
}
