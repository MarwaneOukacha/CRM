package com.crm.product.entities;


import com.crm.product.entities.model.AbstractEntity;
import com.crm.product.enums.Status;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "occasion")
@Data
@ToString
@AttributeOverride(name = "id", column = @Column(name = "occasion_id"))
public class Occasion extends AbstractEntity {
    private String name;
    @Enumerated(EnumType.STRING)
    private Status status;
    @OneToMany(mappedBy = "occasion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductOccasion> productOccasion = new ArrayList<>();
}
