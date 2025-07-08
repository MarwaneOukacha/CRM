package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "product_material")
@AttributeOverride(name = "id", column = @Column(name = "product_material_id"))
@Data
@ToString
public class ProductMaterial extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "material_id", nullable = false)
    private Material material;
}
