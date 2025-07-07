package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "carat")
@Data
public class Carat extends AbstractEntity {

    private String name;
    private String status;

}

