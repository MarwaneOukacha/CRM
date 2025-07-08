package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "product_occasion")
@AttributeOverride(name = "id", column = @Column(name = "product_occasion_id"))
@Data
@ToString
public class ProductOccasion extends AbstractEntity {


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "occasion_id", nullable = false)
    private Occasion occasion;
}
