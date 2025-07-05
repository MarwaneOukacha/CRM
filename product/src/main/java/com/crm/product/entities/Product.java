package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Category;
import com.crm.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AttributeOverride(name = "id", column = @Column(name = "product_id"))
@Data
@ToString(callSuper = true, exclude = "media") @EqualsAndHashCode(callSuper = true)
@Table(name = "product")
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

    private Double stockQuantity;
    @ElementCollection
    @CollectionTable(
            name = "product_characteristics",
            joinColumns = @JoinColumn(name = "product_id")
    )
    @MapKeyColumn(name = "characteristic_key")
    @Column(name = "characteristic_value")
    private Map<String, String> characteristics = new HashMap<>();


}
