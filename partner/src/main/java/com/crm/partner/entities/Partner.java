package com.crm.partner.entities;

import com.crm.partner.entities.model.AbstractEntity;
import com.crm.partner.enums.PartnerStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "partners", uniqueConstraints = @UniqueConstraint(columnNames = "contactEmail"))
public class Partner extends AbstractEntity {

    private String companyName;

    private String contactName;

    @Column(nullable = false, unique = true)
    private String contactEmail;

    @Enumerated(EnumType.STRING)
    private PartnerStatus status;

    private float commissionRate;

    private String contractTerms;


}

