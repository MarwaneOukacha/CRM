package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Category;
import com.crm.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
@Data
@ToString
public class Product extends AbstractEntity {

    private String name;
    private String description;
    private Double price;

    @Enumerated(EnumType.STRING)
    private Category category;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // FOR_SALE, FOR_RENT, IN_STOCK, FROM_PARTNER

    private String material;
    private String type;

    private UUID partnerId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Media> media;

}
