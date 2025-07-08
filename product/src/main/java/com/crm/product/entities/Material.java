package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "material")
@Data
@AttributeOverride(name = "id", column = @Column(name = "material_id"))
public class Material extends AbstractEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "material", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductMaterial> productMaterials = new ArrayList<>();


    // Getters and setters
}
