package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product_occasion")
@AttributeOverride(name = "id", column = @Column(name = "product_occasion_id"))
@Getter
@Setter
public class ProductOccasion extends AbstractEntity {


    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne(optional = false)
    @JoinColumn(name = "occasion_id", nullable = false)
    private Occasion occasion;
}
