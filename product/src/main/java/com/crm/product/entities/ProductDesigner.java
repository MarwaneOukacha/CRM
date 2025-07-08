package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "product_designer")
@AttributeOverride(name = "id", column = @Column(name = "product_designer_id"))
@Data
public class ProductDesigner extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "designer_id", nullable = false)
    private Designer designer;
}
