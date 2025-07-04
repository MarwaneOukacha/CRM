package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "media_id"))
@Data
@ToString
public class Media extends AbstractEntity {

    private String url;
    private String type; // IMAGE or VIDEO

    @ManyToOne
    private Product product;
}
