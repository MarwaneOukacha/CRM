package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "designer")
@Data
public class Designer extends AbstractEntity {

    private String name;
    private String status;

    @OneToMany(mappedBy = "designer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductDesigner> productDesigner = new ArrayList<>();

    // Getters and setters
}
