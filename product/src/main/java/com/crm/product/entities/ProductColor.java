package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@Table(name = "product_color")
@AttributeOverride(name = "id", column = @Column(name = "product_color_id"))
@Data
@ToString
public class ProductColor extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "color_id", nullable = false)
    private Color color;
}


