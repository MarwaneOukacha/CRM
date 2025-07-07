package com.crm.product.entities;

import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.ProductStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
    private String code;
    private int clicks;
    private int favorite;
    private int cart;
    private double size;

    @ManyToOne
    @JoinColumn(name = "carat_id")
    private Carat carat;

    @Enumerated(EnumType.STRING)
    private ProductStatus status; // FOR_SALE, FOR_RENT, IN_STOCK, FROM_PARTNER

    @ManyToOne
    @JoinColumn(name = "occasion_id", nullable = false)
    private Occasion occasion;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Material> materials = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Color> colors = new ArrayList<>();

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Designer> designers = new ArrayList<>();

    private String agencyId;
    private String type;
    private Double weight;
    private UUID partnerId;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
    private List<Media> media = new ArrayList<>();

    // Getters and setters
}

