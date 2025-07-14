package com.crm.partner.entities;

import com.crm.partner.entities.model.AbstractEntity;
import com.crm.partner.enums.PartnerStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Entity
@Data
@Table(name = "partners", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class Partner extends AbstractEntity {

    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    private String phone;

    private String address;

    @Enumerated(EnumType.STRING)
    private PartnerStatus status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "company_id")
    private Company company;

    @OneToMany(mappedBy = "partner", cascade = CascadeType.ALL)
    private List<PartnerContract> contracts = new ArrayList<>();
    // Identity Information
    private String passportSeries;
    private String passportNumber;
    private String finCode;

    // Bank Details
    private String receivingBankName;
    private String receivingBankCurrency;
    private String bankTIN;
    private String bankSwiftCode;
    private String bankAccountNumber;
}

