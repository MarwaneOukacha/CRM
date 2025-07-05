package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id", column = @Column(name = "media_id"))
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class Media extends AbstractEntity {
    private String name;
    private String url;
    private String type; // IMAGE or VIDEO

    @ManyToOne()
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;
}
