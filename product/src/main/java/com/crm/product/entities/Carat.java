package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "carat")
public class Carat extends AbstractEntity {

    private String name;
    private String status;

    // Getters and setters
}

