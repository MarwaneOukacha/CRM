package com.crm.partner.entities;

import com.crm.partner.entities.model.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "companies")
public class Company extends AbstractEntity {

    private String name;

    private String taxId;

    private String address;

    private String contactEmail;

    private String contactPhone;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PartnerContract> contracts = new ArrayList<>();
}
