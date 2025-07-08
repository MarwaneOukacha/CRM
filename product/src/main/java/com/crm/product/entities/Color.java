package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "color")
@Data
@AttributeOverride(name = "id", column = @Column(name = "color_id"))
public class Color extends AbstractEntity {
    private String color;
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "color", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductColor> productColors = new ArrayList<>();

    // Getters and setters
}

