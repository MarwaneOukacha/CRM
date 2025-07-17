package com.crm.order.entities;

import com.crm.order.entities.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Table(name = "campaigns_discounts")
@Data
public class CampaignDiscount extends AbstractEntity {

    private String title;
    private String description;
    private Double discountPercent;
    private LocalDate startDate;
    private LocalDate endDate;

}
