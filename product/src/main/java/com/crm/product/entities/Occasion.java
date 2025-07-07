package com.crm.product.entities;


import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "occasion")
public class Occasion extends AbstractEntity {
    private String name;
    private String status;
    @OneToMany(mappedBy = "occasion", cascade = CascadeType.ALL)
    private List<Product> products = new ArrayList<>();
}
